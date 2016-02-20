/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.property.api;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.datatype.api.OrientLink;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.GenericTypeBuilder;
import net.sf.mmm.util.reflect.base.GenericTypeVariable;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class represents a {@link GenericProperty property} containing an {@link OrientLink} that
 * {@link OrientLink#getTarget() points to} an {@link OrientBean}.
 *
 * @param <T> the generic type of the {@link OrientLink#getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class LinkProperty<T extends OrientBean> extends GenericProperty<OrientLink<T>> {

  /** The fallback for {@link #getType()} if linked class is unknown. */
  @SuppressWarnings("rawtypes")
  private static final GenericType<OrientLink> TYPE = new SimpleGenericTypeImpl<>(OrientLink.class);

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public LinkProperty(String name, GenericType<OrientLink<T>> type, Bean bean) {
    super(name, type, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public LinkProperty(String name, GenericType<OrientLink<T>> type, Bean bean,
      AbstractValidator<? super OrientLink<T>> validator) {
    super(name, type, bean, validator);
  }

  /**
   * @param <T> the generic type of the linked {@link OrientBean}.
   * @param beanClass the class reflecting the linked {@link OrientBean}.
   * @return the {@link GenericType} for an {@link OrientLink} {@link OrientLink#getTarget() pointing to} an
   *         {@link OrientBean} of the given {@link Class}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T extends OrientBean> GenericType<OrientLink<T>> createLinkType(Class<T> beanClass) {

    if (beanClass == null) {
      return (GenericType) TYPE;
    }
    return new GenericTypeBuilder<OrientLink<T>>() {
    }.with(new GenericTypeVariable<T>() {
    }, beanClass).build();
  }

}
