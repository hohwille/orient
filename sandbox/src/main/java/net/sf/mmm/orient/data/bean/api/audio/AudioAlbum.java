/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.audio;

import net.sf.mmm.orient.data.bean.api.AbstractName;
import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.link.LinkListProperty;

/**
 * This is the {@link net.sf.mmm.orient.api.bean.OrientBean} for an album such as a compact disc (CD) or a
 * vinyl record (e.g. LP).
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AudioAlbum extends AbstractName {

  /**
   * @return flag that is {@code true} if the album was recorded live (on concert).
   */
  BooleanProperty Live();

  /**
   * @return flag that is {@code true} in case the album is a sampler that contains {@link #Tracks() tracks}
   *         from different {@link AudioTrack#Artist() artists}.
   */
  BooleanProperty Sampler();

  /**
   * @return the {@link AudioTrack tracks} contained in this album in the order of recording (index on the
   *         media).
   */
  LinkListProperty<AudioTrack> Tracks();

}
