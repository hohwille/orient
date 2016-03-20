/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import java.util.ArrayList;
import java.util.List;

import com.orientechnologies.orient.core.metadata.schema.OClass;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;

/**
 * This is a container for the data of an {@link OrientBean}-interface and its corresponding {@link OClass}.
 *
 * @author hohwille
 * @since 1.0.0
 */
class OrientClass {

  private final OrientBean prototype;

  private List<OrientClass> superClasses;

  private OClass oClass;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanFactory#createPrototype(Class, boolean) bean prototype}.
   */
  public OrientClass(OrientBean prototype) {
    super();
    this.prototype = prototype;
  }

  /**
   * @return the {@link OClass}.
   */
  public OClass getOClass() {

    return this.oClass;
  }

  /**
   * @param oClass the {@link OClass} to set.
   */
  public void setOClass(OClass oClass) {

    if (this.oClass != null) {
      throw new IllegalStateException("OClass can be set only once: " + this.prototype.access().getSimpleName());
    }
    this.oClass = oClass;
  }

  /**
   * @return the {@link BeanFactory#createPrototype(Class, boolean) bean prototype}.
   */
  public OrientBean getPrototype() {

    return this.prototype;
  }

  /**
   * @return {@code true} if {@code getSuperClasses()} has already been called, {@code false} otherwise.
   */
  public boolean isSuperClassesInitialized() {

    return (this.superClasses != null);
  }

  /**
   * @return the {@link List} of super classes.
   */
  public List<OrientClass> getSuperClasses() {

    if (this.superClasses == null) {
      this.superClasses = new ArrayList<>();
    }
    return this.superClasses;
  }

  @Override
  public String toString() {

    BeanAccess access = this.prototype.access();
    return access.getSimpleName() + "(" + access.getBeanClass().getName() + ")";
  }

}
