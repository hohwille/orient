/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.impl;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.OrientLink;

/**
 * Implementation of {@link OrientLink} only containing an {@link #getId()}.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientLinkId<T extends OrientBean> extends AbstractOrientLink<T> {

  private final String id;

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   */
  public OrientLinkId(String id) {
    super();
    this.id = id;
  }

  @Override
  public boolean isResolved() {

    return true;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  public T getTarget() {

    return null;
  }

  /**
   * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link OrientLinkId} instance.
   */
  public static <T extends OrientBean> OrientLinkId<T> valueOf(String id) {

    if (id == null) {
      return null;
    }
    return new OrientLinkId<>(id);
  }

}
