/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.contact;

import net.sf.mmm.orient.bean.api.Vertex;
import net.sf.mmm.orient.data.bean.api.world.City;
import net.sf.mmm.orient.data.bean.api.world.Country;
import net.sf.mmm.orient.data.bean.api.world.PostalCode;
import net.sf.mmm.orient.data.bean.api.world.Region;
import net.sf.mmm.orient.property.api.LinkProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface Address extends Vertex {

  /**
   * @return the name of the {@link City} this address is located in.
   */
  @Mandatory
  StringProperty CityName();

  /**
   * @return the {@link City} this address is located in. Optional but if set then {@link #CityName()} should be
   *         {@link City#Name()}.
   */
  LinkProperty<City> City();

  /**
   * @return the {@link Country} this {@link Address} is located in.
   */
  @Mandatory
  LinkProperty<Country> Country();

  /**
   * @return the {@link Region} this {@link Address} is located in.
   */
  LinkProperty<Region> Region();

  /**
   * @return the {@link PostalCode} as {@link String}. Validation depends on the {@link Country#PostalCodeMode()}.
   */
  StringProperty Zip();

  /**
   * @return the {@link PostalCode}. Optional but if set then {@link #Zip()} has to be {@link PostalCode#Code()}.
   */
  LinkProperty<PostalCode> PostalCode();

  /**
   * @return the Post Office (PO) box. It is a unique identifier for a locked box typically located on the premises of
   *         the post office. In most countries a PO box has a numberic code. However {@link String} is used as this is
   *         not ensured for all countries. is Has to be present if {@link #Street()} is empty.
   */
  StringProperty PoBox();

  /**
   * @return the street name. Has to be present if {@link #PoBox()} is empty.
   */
  StringProperty Street();

  /**
   * @return the optional street number or house number. Please note that this is not strictly numeric (e.g. "7b" is a
   *         valid value) and is therefore represented as {@link String}.
   */
  StringProperty StreetNumber();

}
