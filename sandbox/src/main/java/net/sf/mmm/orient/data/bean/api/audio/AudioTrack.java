/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.audio;

import net.sf.mmm.orient.data.bean.api.media.AbstractTrack;
import net.sf.mmm.orient.data.bean.api.world.Language;
import net.sf.mmm.orient.property.api.LinkListProperty;
import net.sf.mmm.orient.property.api.LinkProperty;
import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;

/**
 * TODO: this class ...
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