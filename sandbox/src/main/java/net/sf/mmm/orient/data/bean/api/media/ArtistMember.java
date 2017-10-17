/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.media;

import net.sf.mmm.orient.api.bean.Edge;
import net.sf.mmm.property.api.lang.StringListProperty;

/**
 * An {@link Edge} that links from an {@link ArtistGroup} to the {@link ArtistPerson}s that its members.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface ArtistMember extends Edge {

  /**
   * @return the functions of the {@link ArtistPerson member} in the {@link ArtistGroup band}. Examples for a
   *         function are <em>vocals</em>, <em>guitar</em>, <em>bass</em>, <em>drums</em>, <em>keyboards</em>,
   *         <em>saxophone</em>.
   */
  StringListProperty Functions();

}
