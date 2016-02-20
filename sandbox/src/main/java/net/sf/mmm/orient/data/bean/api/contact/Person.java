/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.contact;

import net.sf.mmm.orient.bean.api.Vertex;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.time.LocalDateProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface Person extends Vertex {

  StringProperty NamePrefix();

  StringProperty FirstName();

  StringProperty MiddleNames();

  StringProperty LastName();

  StringProperty NameSuffix();

  StringProperty Gender();

  LocalDateProperty Birthday();

}
