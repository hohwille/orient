/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.media;

import net.sf.mmm.property.api.time.DurationInSecondsProperty;

/**
 * This is the abstract {@link net.sf.mmm.orient.api.bean.OrientBean} for a multi-media track such as an
 * {@link net.sf.mmm.orient.data.bean.api.audio.AudioTrack} or a
 * {@link net.sf.mmm.orient.data.bean.api.video.VideoTrack}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public abstract interface AbstractTrack extends AbstractMedia {

  /**
   * @return the duration of the multi-media track in seconds.
   */
  DurationInSecondsProperty Length();

}
