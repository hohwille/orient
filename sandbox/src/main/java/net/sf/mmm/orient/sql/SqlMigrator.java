/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.sql;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class SqlMigrator {

  public static final String DEFAULT_LOCATIONS = "classpath:db/migration";

  private String locations;

  /**
   * The constructor.
   */
  public SqlMigrator() {
    super();
    this.locations = DEFAULT_LOCATIONS;
  }

  /**
   * @return the locations
   */
  public String getLocations() {

    return this.locations;
  }

  /**
   * @param locations is the locations to set
   */
  public void setLocations(String locations) {

    this.locations = locations;
  }

  public void migrate() {

  }

}
