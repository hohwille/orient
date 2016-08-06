/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.db;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The {@link ConfigurationProperties} for {@link OrientDatabaseImpl} (datasource for OrientDB).
 *
 * @author hohwille
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "orient.datasource")
public class OrientDatasourceConfigProperties {

  private String url;

  private String username;

  private String password;

  private int maxPoolSize = 20;

  /**
   * The constructor.
   */
  public OrientDatasourceConfigProperties() {
    super();
  }

  /**
   * @return the OrientDB database url such as
   */
  public String getUrl() {

    return this.url;
  }

  /**
   * @param url the new value of {@link #getUrl()}.
   */
  public void setUrl(String url) {

    this.url = url;
  }

  /**
   * @return the username to log into OrientDB.
   */
  public String getUsername() {

    return this.username;
  }

  /**
   * @param username the new value of {@link #getUsername()}.
   */
  public void setUsername(String username) {

    this.username = username;
  }

  /**
   * @return the password to log into OrientDB.
   */
  public String getPassword() {

    return this.password;
  }

  /**
   * @param password the new value of {@link #getPassword()}.
   */
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * @see com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory#getMaxPoolSize()
   *
   * @return the maximum size of open connections in the database pool.
   */
  public int getMaxPoolSize() {

    return this.maxPoolSize;
  }

  /**
   * @param maxPoolSize the new value of {@link #getMaxPoolSize()}.
   */
  public void setMaxPoolSize(int maxPoolSize) {

    this.maxPoolSize = maxPoolSize;
  }

}
