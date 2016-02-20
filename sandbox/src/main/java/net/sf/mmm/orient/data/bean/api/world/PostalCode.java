/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.world;

import net.sf.mmm.orient.data.bean.api.AbstractCode;
import net.sf.mmm.orient.property.api.LinkListProperty;
import net.sf.mmm.orient.property.api.LinkProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface PostalCode extends AbstractCode {

  LinkProperty<Country> Country();

  LinkListProperty<City> Cities();

}
