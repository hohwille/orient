/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api;

import net.sf.mmm.orient.api.bean.Vertex;
import net.sf.mmm.property.api.lang.StringProperty;

/**
 * This is the abstract base class for a {@link Vertex} that has a {@link #Name()}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AbstractLocalizedName extends AbstractName {

  /**
   * @return the optional native variant of {@link #Name()}. That is the localized {@link #Name() name} in the
   *         native language of this object (e.g. in case of a Country or Language).
   */
  StringProperty NativeName();

  /**
   * @return the optional localized variants of {@link #Name()}.
   */
  // EmbeddedProperty LocalizedNames();

}
