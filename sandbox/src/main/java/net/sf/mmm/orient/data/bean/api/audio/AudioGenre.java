/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.audio;

import net.sf.mmm.orient.data.bean.api.AbstractGenre;
import net.sf.mmm.orient.property.api.LinkProperty;
import net.sf.mmm.util.property.api.lang.IntegerProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AudioGenre extends AbstractGenre {

  /**
   * @return the IDv3 genre code if available, or <code>null</code>.
   */
  IntegerProperty Id3();

  /**
   * @return the parent genre or {@code null} if this is the root genre node.
   */
  LinkProperty<AudioGenre> Parent();

}
