/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.impl;

import java.util.Objects;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.OrientLink;

/**
 * Abstract base implementation of {@link OrientLink}.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public abstract class AbstractOrientLink<T extends OrientBean> implements OrientLink<T> {

  /**
   * The constructor.
   */
  public AbstractOrientLink() {
    super();
  }

  @Override
  public final int hashCode() {

    String id = getId();
    if (id == null) {
      return 0;
    }
    return ~id.hashCode();
  }

  @Override
  public final boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    String id = ((AbstractOrientLink<?>) obj).getId();
    if (!Objects.equals(getId(), id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return getId();
  }

}
