/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.world;

import net.sf.mmm.orient.data.bean.api.AbstractCode;
import net.sf.mmm.orient.data.bean.api.AbstractLocalizedName;
import net.sf.mmm.orient.data.bean.api.AbstractLocation;
import net.sf.mmm.util.property.api.math.LongProperty;

/**
 * This is the abstract base class for an area like a country, state, city, etc.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AbstractArea extends AbstractLocalizedName, AbstractCode, AbstractLocation {

  /**
   * @return the optional area in square kilometers (km²).
   */
  LongProperty Area();

  /**
   * @return the optional number of inhabitants of this area. Please note that this value frequently changes and will
   *         typically be out-dated. So consider it as an estimate.
   */
  LongProperty Inhabitants();

}
