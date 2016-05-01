/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.contact;

import net.sf.mmm.orient.api.bean.Vertex;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.time.LocalDateProperty;

/**
 * TODO: this class ...
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

  StringProperty Gender();

  LocalDateProperty Birthday();

}
