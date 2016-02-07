/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.impl;

import net.sf.mmm.orient.bean.api.OrientBean;

/**
 * Implementation of {@link OrientLinkDocument} only containing an {@link #getId()}.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class OrientLinkIdLazy<T extends OrientBean> extends OrientLinkId<T> {

  private T target;

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   */
  public OrientLinkIdLazy(String id) {
    super(id);
    this.target = null;
  }

  @Override
  public T getTarget() {

    if (this.target == null) {
      this.target = resolveLink();
    }
    return this.target;
  }

  protected T resolveLink() {

    // return (T) OrientDatabase.getInstance().load(this.id);
    return null;
  }

  /**
   *
   * @param <T>
   * @param id
   * @return
   */
  public static <T extends OrientBean> OrientLinkIdLazy<T> valueOf(String id) {

    if (id == null) {
      return null;
    }
    return new OrientLinkIdLazy<>(id);
  }

}
