/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.world;

import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface Country extends AbstractArea {

  StringProperty AreaCode();

  StringProperty CodeIso3();

  IntegerProperty CodeIsoNo();

  StringProperty Currency();

  StringProperty PostalCodeMode();

  StringProperty PostalCodePattern();

  StringProperty Tld();

}
