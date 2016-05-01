/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.world.de;

import net.sf.mmm.orient.data.bean.api.world.AbstractArea;
import net.sf.mmm.util.property.api.link.LinkProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface DeRegierungsbezirk extends AbstractArea {

  LinkProperty<DeBundesland> Bundesland();

}
