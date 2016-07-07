/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api;

import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.math.LongProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface File extends AbstractName {

  LongProperty getSize();

  StringProperty getMimetype();

  StringProperty getChecksum();

  StringProperty getServerLocation();

}
