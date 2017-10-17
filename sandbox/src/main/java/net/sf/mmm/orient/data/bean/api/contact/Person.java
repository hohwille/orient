/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.contact;

import net.sf.mmm.orient.api.bean.Vertex;
import net.sf.mmm.property.api.lang.StringProperty;
import net.sf.mmm.property.api.time.LocalDateProperty;

/**
 * This is the {@link net.sf.mmm.orient.api.bean.OrientBean} for a person.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface Person extends Vertex {

  /**
   * @return an optional prefix for the name such as an academic title.
   */
  StringProperty NamePrefix();

  /**
   * @return the first name (given name).
   */
  StringProperty FirstName();

  /**
   * @return optional middle name(s) (additional names).
   */
  StringProperty MiddleNames();

  /**
   * @return the last name (family name).
   */
  StringProperty LastName();

  /**
   * @return an optional suffix for the name.
   */
  StringProperty NameSuffix();

  /**
   * @return the gender of the person.
   */
  StringProperty Gender();

  /**
   * @return the date when the person was born.
   */
  LocalDateProperty Birthday();

}
