/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.api;

import javax.inject.Named;

import net.sf.mmm.orient.property.api.LinkProperty;

/**
 * This is the interface for an edge. An edge connects two instances of {@link Vertex}. It represents the predefined
 * {@link com.orientechnologies.orient.core.metadata.schema.OClass OrientDB Class} called {@code E}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named(Edge.NAME)
public interface Edge extends OrientBean {

  /**
   * The {@link net.sf.mmm.util.bean.api.BeanAccess#getName() name} corresponding to
   * {@link com.orientechnologies.orient.core.metadata.schema.OClass#getName()}.
   */
  String NAME = "E";

  /**
   * @return the {@link LinkProperty property} with the incoming link where this {@link Edge} is coming from.
   */
  LinkProperty<?> In();

  /**
   * @return the {@link LinkProperty property} with the outgoing link where this {@link Edge} is going to.
   */
  LinkProperty<?> Out();

}