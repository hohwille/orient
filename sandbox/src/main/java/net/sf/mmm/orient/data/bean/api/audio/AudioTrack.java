/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.audio;

import net.sf.mmm.orient.data.bean.api.media.AbstractTrack;
import net.sf.mmm.orient.data.bean.api.world.Language;
import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.link.LinkListProperty;
import net.sf.mmm.util.property.api.link.LinkProperty;

/**
 * This is the {@link net.sf.mmm.orient.api.bean.OrientBean} for an audio {@link AudioTrack track}. It represents the
 * metadata of a single audio record such as a song, a chapter of an audio-book, etc. Any single audio file (ogg, mp3,
 * flac, etc.) or any single track on an audio CD corresponds to an {@link AudioTrack}.<br>
 * Typically an {@link AudioTrack} belongs to an {@link AudioAlbum} that groups the tracks belonging together (e.g. to
 * an audio CD).<br>
 *
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface AudioTrack extends AbstractTrack {

  /**
   * @return the optional average number of beats per minute.
   */
  IntegerProperty Bpm();

  LinkProperty<AudioGenre> Genre();

  StringProperty MusicalKey();

  LinkProperty<Language> PrimaryLanguage();

  LinkListProperty<Language> AdditionalLanguages();

}
