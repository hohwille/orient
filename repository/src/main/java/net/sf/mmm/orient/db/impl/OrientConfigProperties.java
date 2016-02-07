/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "orient.datasource")
public class OrientConfigProperties {

  private String url;

  private String username;

  private String password;

  private int minPoolSize = 1;

  private int maxPoolSize = 20;

  /**
   * The constructor.
   */
  public OrientConfigProperties() {
    super();
  }

  /**
   * @return the OrientDB database url such as
   */
  public String getUrl() {

    return this.url;
  }

  /**
   * @param url is the url to set
   */
  public void setUrl(String url) {

    this.url = url;
  }

  /**
   * @return the username
   */
  public String getUsername() {

    return this.username;
  }

  /**
   * @param username is the username to set
   */
  public void setUsername(String username) {

    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {

    return this.password;
  }

  /**
   * @param password is the password to set
   */
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * @return the minPoolSize
   */
  public int getMinPoolSize() {

    return this.minPoolSize;
  }

  /**
   * @param minPoolSize is the minPoolSize to set
   */
  public void setMinPoolSize(int minPoolSize) {

    this.minPoolSize = minPoolSize;
  }

  /**
   * @return the maxPoolSize
   */
  public int getMaxPoolSize() {

    return this.maxPoolSize;
  }

  /**
   * @param maxPoolSize is the maxPoolSize to set
   */
  public void setMaxPoolSize(int maxPoolSize) {

    this.maxPoolSize = maxPoolSize;
  }

}
