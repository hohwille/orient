/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.audio;

import javafx.beans.property.BooleanProperty;
import net.sf.mmm.orient.data.bean.api.AbstractName;
import net.sf.mmm.orient.property.api.LinkListProperty;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AudioAlbum extends AbstractName {

  BooleanProperty Live();

  BooleanProperty Sampler();

  LinkListProperty<AudioTrack> Tracks();

}