/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.id;

import java.util.UUID;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;

import net.sf.mmm.util.bean.api.id.AbstractId;
import net.sf.mmm.util.bean.api.id.Id;
import net.sf.mmm.util.exception.api.ObjectMismatchException;

/**
 * This is the implementation of {@link Id} for a native {@link ORID}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientId<E> extends AbstractId<E> {

  private final Class<E> type;

  private final ORID orid;

  private final UUID uuid;

  private final long version;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param uuid - see {@link #getUuid()}.
   * @param orid - see {@link #getOrid()}.
   * @param version - see {@link #getVersion()}.
   */
  protected OrientId(Class<E> type, UUID uuid, ORID orid, long version) {
    super();
    this.type = type;
    this.uuid = uuid;
    this.orid = orid;
    this.version = version;
  }

  @Override
  public long getId() {

    return this.orid.getClusterPosition();
  }

  @Override
  public UUID getUuid() {

    return this.uuid;
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @SuppressWarnings("unchecked")
  @Override
  public OrientId<E> withType(Class<?> newType) {

    if (this.type == null) {
      return new OrientId<>((Class<E>) newType, this.uuid, this.orid, this.version);
    } else if (this.type != newType) {
      throw new ObjectMismatchException(newType, this.type, this);
    }
    return this;
  }

  @Override
  public Id<E> withLatestVersion() {

    if (this.version == VERSION_LATEST) {
      return this;
    }
    return new OrientId<>(getType(), this.uuid, this.orid, VERSION_LATEST);
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
  public String toString() {

    return this.orid.toString() + VERSION_SEPARATOR + this.version;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param idAsString the {@link #toString() string representation of the ID}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> of(Class<E> type, String idAsString) {

    if (idAsString == null) {
      return null;
    }
    int versionSeparatorIndex = idAsString.indexOf(VERSION_SEPARATOR);
    String idString;
    long version;
    if (versionSeparatorIndex < 0) {
      idString = idAsString;
      version = VERSION_LATEST;
    } else {
      idString = idAsString.substring(0, versionSeparatorIndex);
      version = Long.parseLong(idAsString.substring(versionSeparatorIndex + 1));
    }
    UUID uuid = parseUuid(idString);
    if (uuid != null) {
      return of(type, uuid, version);
    } else {
      return of(type, new ORecordId(idString), version);
    }
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param uuid - see {@link #getUuid()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> of(Class<E> type, UUID uuid, long version) {

    if (uuid == null) {
      return null;
    } else {
      return new OrientId<>(type, uuid, null, version);
    }
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> of(Class<E> type, ORID orid, long version) {

    if (orid == null) {
      return null;
    } else {
      return new OrientId<>(type, null, orid, version);
    }
  }
}
