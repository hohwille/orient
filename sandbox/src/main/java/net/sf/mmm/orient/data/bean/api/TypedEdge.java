/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api;

import net.sf.mmm.orient.api.bean.Edge;
import net.sf.mmm.orient.data.bean.api.contact.Person;
import net.sf.mmm.property.api.lang.StringProperty;

/**
 * An {@link Edge} that has a {@link #Type() type}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface TypedEdge extends Edge {

  /**
   * @return the type of this {@link Edge}. It acts as a classifier for the link. E.g. if you link a
   *         {@link Person} to an address
   */
  StringProperty Type();

}
