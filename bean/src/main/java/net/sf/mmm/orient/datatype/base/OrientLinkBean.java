/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.datatype.base;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.datatype.api.OrientLink;

/**
 * Implementation of {@link OrientLink} based on an already resolved {@link OrientBean}.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientLinkBean<T extends OrientBean> extends AbstractOrientLink<T> {

  private final T bean;

  /**
   * The constructor.
   *
   * @param bean - see {@link #getTarget()}.
   */
  protected OrientLinkBean(T bean) {
    super();
    this.bean = bean;
  }

  @Override
  public boolean isResolved() {

    return true;
  }

  @Override
  public String getId() {

    return this.bean.getId();
  }

  @Override
  public T getTarget() {

    return this.bean;
  }

  /**
   * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
   * @param bean the {@link #getTarget() linked} {@link OrientBean}.
   * @return the new {@link OrientLinkBean} instance.
   */
  public static <T extends OrientBean> OrientLinkBean<T> valueOf(T bean) {

    if (bean == null) {
      return null;
    }
    return new OrientLinkBean<>(bean);
  }

}
