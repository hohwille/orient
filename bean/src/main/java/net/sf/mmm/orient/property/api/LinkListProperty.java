/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.property.api;

import java.util.List;

import javafx.collections.ObservableList;
import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.datatype.api.OrientLink;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.api.ListProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class represents a {@link GenericProperty property} containing a {@link List} of {@link OrientLink}s that each
 * {@link OrientLink#getTarget() point to} an {@link OrientBean}.
 *
 * @param <T> the generic type of the {@link OrientLink#getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class LinkListProperty<T extends OrientBean> extends ListProperty<OrientLink<T>> {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public LinkListProperty(String name, GenericType<ObservableList<OrientLink<T>>> type, Bean bean) {
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
  public LinkListProperty(String name, GenericType<ObservableList<OrientLink<T>>> type, Bean bean,
      AbstractValidator<? super ObservableList<OrientLink<T>>> validator) {
    super(name, type, bean, validator);
  }

  /**
   * @param <T> the generic type of the linked {@link OrientBean}.
   * @param beanClass the class reflecting the linked {@link OrientBean}.
   * @return the {@link GenericType} for an {@link OrientLink} {@link OrientLink#getTarget() pointing to} an
   *         {@link OrientBean} of the given {@link Class}.
   */
  public static <T extends OrientBean> GenericType<ObservableList<OrientLink<T>>> createLinkType(
      Class<T> beanClass) {

    return createListType(LinkProperty.createLinkType(beanClass));
  }

}
