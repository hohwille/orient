/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.media;

import net.sf.mmm.orient.data.bean.api.contact.Person;
import net.sf.mmm.util.property.api.link.LinkProperty;

/**
 * This is the {@link net.sf.mmm.orient.api.bean.Vertex} for an {@link AbstractArtist artist} that is a single
 * person ("one man show") unlike an {@link ArtistGroup}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface ArtistPerson extends AbstractArtist {

  /**
   * @return the actual {@link Person} data of this artist.
   */
  LinkProperty<Person> Person();

}
