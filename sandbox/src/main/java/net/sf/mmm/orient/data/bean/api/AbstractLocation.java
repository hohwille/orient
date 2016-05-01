/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api;

import net.sf.mmm.orient.api.bean.Vertex;
import net.sf.mmm.util.property.api.lang.DoubleProperty;

/**
 * This is the abstract base class for a {@link Vertex} that has an optional geo-location with {@link #Longitude()} and
 * {@link #Latitude()}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AbstractLocation extends Vertex {

  /**
   * @return the optional latitude part of the geo-location. If set then also {@link #Longitude()} should be set.
   */
  DoubleProperty Latitude();

  /**
   * @return the optional longitude part of the geo-location. If set then also {@link #Latitude()} should be set.
   */
  DoubleProperty Longitude();

}
