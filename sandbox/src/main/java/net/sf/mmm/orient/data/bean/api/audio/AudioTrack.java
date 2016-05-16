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
 * This is the {@link net.sf.mmm.orient.api.bean.OrientBean} for an audio {@link AudioTrack track}. It
 * represents the metadata of a single audio record such as a song, a chapter of an audio-book, etc. Any
 * single audio file (ogg, mp3, flac, etc.) or any single track on an audio CD corresponds to an
 * {@link AudioTrack}.<br>
 * Typically an {@link AudioTrack} belongs to an {@link AudioAlbum} that groups the tracks belonging together
 * (e.g. to an audio CD).<br>
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

  /**
   * @return the {@link AudioGenre genre} of the track.
   */
  LinkProperty<AudioGenre> Genre();

  /**
   * @return the musical key of the track. Value will be empty if no music, not in a main key, or unknown.
   */
  StringProperty MusicalKey();

  /**
   * @return the primary {@link Language} of the track. Value will be empty if undefined, or if in no language
   *         (e.g. instrumantal).
   */
  LinkProperty<Language> PrimaryLanguage();

  /**
   * @return the list of optional additional {@link Language}s of the track. Sometimes a song has verses in
   *         different languages or the refrain is in a different language. In that case all {@link Language}s
   *         in addition to the {@link #PrimaryLanguage() primary language} should be listed here.
   */
  LinkListProperty<Language> AdditionalLanguages();

}
