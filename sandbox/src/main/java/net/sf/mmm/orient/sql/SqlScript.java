/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class SqlScript {

  public static final Pattern FILENAME_PATTERN = Pattern.compile("v([0-9]+).*\\.sql", Pattern.CASE_INSENSITIVE);

  private static final String DEFAULT_HASH_ALGORITHM = "SHA-256";

  private static final CharFilter FILTER_REGULAR = new ListCharFilter(false, '\\', '\'', '"', '-', '\n', '\r');

  private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
  'f' };

  private static final Charset UTF8 = Charset.forName("UTF-8");

  private final DataResource resource;

  private final int version;

  private final String algorithm;

  private String checksum;

  private List<String> statements;

  /**
   * The constructor.
   */
  private SqlScript(DataResource resource, int version, String algorithm) {
    super();
    this.resource = resource;
    this.version = version;
    this.algorithm = algorithm;
  }

  /**
   * @return the path to the directory containing the {@link #getSourceFilename() SQL file}.
   */
  public String getSourcePath() {

    return this.resource.getPath();
  }

  /**
   * @return the filename of the file containing the SQL.
   */
  public String getSourceFilename() {

    return this.resource.getName();
  }

  /**
   * Lazy initialization.
   */
  private void init() {

    if ((this.checksum != null) && (this.statements != null)) {
      return;
    }
    this.statements = new ArrayList<>();
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance(this.algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("Signature algorithm " + this.algorithm + " not found!", e);
    }
    try (InputStream in = this.resource.openStream()) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder commandBuffer = new StringBuilder(80);
      while (true) {
        String line = reader.readLine();
        if (line == null) {
          break;
        }
        CharSequenceScanner scanner = new CharSequenceScanner(line);
        int start = scanner.skipWhile(CharFilter.WHITESPACE_FILTER);
        int end = line.length() - 1;
        char quoteStart = '\0';
        while (true) {
          int skipped = scanner.skipWhile(FILTER_REGULAR);
          char stop = scanner.forceNext();
          if (stop == quoteStart) {
            quoteStart = '\0'; // quote end...
          } else if ((stop == '\'') || (stop == '"')) {
            quoteStart = stop;
          } else if (stop == '\\') {
            stop = scanner.forceNext();
          } else if ((stop == '-') && (quoteStart != '\0')) {
            stop = scanner.forceNext();
            if (stop == '-') {
              end = scanner.getCurrentIndex() - 1; // found comment start
              break;
            }
          } else {
            end = scanner.getCurrentIndex() - 1; // newline
            break;
          }
        }
        while (end > start) { // trim end
          char c = line.charAt(end);
          if (c == ' ') {
            end--;
          } else {
            break;
          }
        }
        if (end != start) {
          String command = line.substring(start, end);
          digest.update(command.getBytes(UTF8));
          boolean join = command.endsWith(",");
          boolean append = commandBuffer.length() > 0;
          if (append || join) {
            commandBuffer.append(command);
          }
          if (!join) {
            if (append) {
              command = commandBuffer.toString();
              commandBuffer.setLength(0);
            }
            this.statements.add(command);
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
    this.checksum = getHex(digest.digest());
    this.statements = Collections.unmodifiableList(this.statements);
  }

  /**
   * @return the checksum of the {@link #getSourceFilename() SQL file} after normalization (EOL-style normalized to LF,
   *         comments removed, lines trimmed)
   */
  public String getChecksum() {

    init();
    return this.checksum;
  }

  /**
   * The actual SQL statements.<br/>
   * <b>ATTENTION:</b><br/>
   * All continuous lines ending with a colon (after stripping comments and trimming) are grouped as one statement.
   *
   * @return an array with the actual SQL statements.
   */
  public List<String> getStatements() {

    init();
    return this.statements;
  }

  public static SqlScript create(DataResource resource) {

    return create(resource, DEFAULT_HASH_ALGORITHM);
  }

  public static SqlScript create(DataResource resource, String algorithm) {

    String filename = resource.getName();
    Matcher matcher = FILENAME_PATTERN.matcher(filename);
    if (matcher.matches() && resource.isAvailable()) {
      int version = Integer.parseInt(matcher.group(1));
      return new SqlScript(resource, version, algorithm);
    }
    return null;
  }

  private static String getHex(byte[] data) {

    char[] buffer = new char[data.length * 2];
    int i = 0;
    for (byte b : data) {
      buffer[i++] = HEX[(b & 0xF0) >> 4];
      buffer[i++] = HEX[b & 0x0F];
    }
    return new String(buffer);
  }

}
