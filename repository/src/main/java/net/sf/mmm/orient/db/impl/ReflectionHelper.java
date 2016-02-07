/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.OrientLink;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.GenericTypeBuilder;
import net.sf.mmm.util.reflect.base.GenericTypeVariable;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public final class ReflectionHelper {

  private ReflectionHelper() {
  }

  public static <T extends OrientBean> GenericType<OrientLink<T>> createLinkType(Class<T> beanClass) {

    return new GenericTypeBuilder<OrientLink<T>>() {
    }.with(new GenericTypeVariable<T>() {
    }, beanClass).build();
  }

}
