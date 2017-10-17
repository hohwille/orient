/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.media;

import net.sf.mmm.orient.data.bean.api.AbstractName;
import net.sf.mmm.property.api.lang.StringListProperty;

/**
 * This is the abstract {@link net.sf.mmm.orient.api.bean.OrientBean} for any multi-media object.
 *
 * @author hohwille
 * @since 1.0.0
 */
public abstract interface AbstractMedia extends AbstractName {

  /**
   * @return an optional list of tags.
   */
  StringListProperty Tags();

}
