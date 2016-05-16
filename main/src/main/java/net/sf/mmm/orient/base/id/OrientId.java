/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.id;

import java.util.Objects;

import com.orientechnologies.orient.core.id.ORID;

import net.sf.mmm.util.bean.api.id.AbstractId;
import net.sf.mmm.util.bean.api.id.Id;

/**
 * This is the implementation of {@link Id} for a native {@link ORID}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientId<E> implements Id<E> {

  private final Class<E> type;

  private final ORID orid;

  private final long version;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @param version - see {@link #getVersion()}.
   */
  protected OrientId(Class<E> type, ORID orid, long version) {
    super();
    this.type = type;
    this.orid = orid;
    this.version = version;
  }

  @Override
  public long getId() {

    return this.orid.getClusterPosition();
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public long getVersion() {

    return this.version;
  }

  /**
   * @return the {@link ORID}.
   */
  public ORID getOrid() {

    return this.orid;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(this.orid);
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj == null) || !(obj instanceof AbstractId)) {
      return false;
    }
    OrientId<?> other = (OrientId<?>) obj;
    if (this.orid != other.orid) {
      return false;
    }
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    if (this.version != other.version) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return this.orid.toString() + '@' + this.version;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> valueOf(Class<E> type, ORID orid) {

    return valueOf(type, orid, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> valueOf(Class<E> type, ORID orid, int version) {

    return valueOf(type, orid, (long) version);
  }

  private static <E> OrientId<E> valueOf(Class<E> type, ORID orid, long version) {

    if (orid == null) {
      return null;
    } else {
      return new OrientId<>(type, orid, version);
    }
  }
}
