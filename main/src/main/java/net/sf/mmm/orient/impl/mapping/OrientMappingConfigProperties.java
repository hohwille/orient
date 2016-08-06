/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.mapping;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.sf.mmm.orient.api.bean.OrientBean;

/**
 * The {@link ConfigurationProperties} for {@link OrientBeanMapperImpl}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "orient.mapping")
public class OrientMappingConfigProperties {

  private Set<String> packagesToScan;

  /**
   * The constructor.
   */
  public OrientMappingConfigProperties() {
    super();
  }

  /**
   * @return the {@link List} of {@link Package}s to scan for {@link OrientBean}s to map and sync with OrientDB.
   */
  public Set<String> getPackagesToScan() {

    if (this.packagesToScan == null) {
      this.packagesToScan = new HashSet<>();
    }
    return this.packagesToScan;
  }

  /**
   * @param packagesToScan the new value of {@link #getPackagesToScan()}.
   */
  public void setPackagesToScan(Set<String> packagesToScan) {

    this.packagesToScan = packagesToScan;
  }

  /**
   * @param packages the packages to add to {@link #getPackagesToScan()}.
   */
  public void addPackagesToScan(Collection<String> packages) {

    getPackagesToScan().addAll(packages);
  }

  /**
   * @param packages the packages to add to {@link #getPackagesToScan()}.
   */
  public void addPackagesToScan(String... packages) {

    addPackagesToScan(Arrays.asList(packages));
  }

}
