/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.id;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;

import net.sf.mmm.util.data.api.id.AbstractId;
import net.sf.mmm.util.data.api.id.Id;

/**
 * This is the implementation of {@link Id} for a native {@link ORID}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientId<E> extends AbstractId<E> {

  private final ORID orid;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @param version - see {@link #getVersion()}.
   */
  protected OrientId(Class<E> type, ORID orid, long version) {
    super(type, version);
    this.orid = orid;
  }

  @Override
  public ORID getId() {

    return this.orid;
  }

  @Override
  protected <T> AbstractId<T> newId(Class<T> newType, long newVersion) {

    return new OrientId<>(newType, this.orid, newVersion);
  }

  /**
   * @return the {@link ORID}.
   */
  public ORID getOrid() {

    return this.orid;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param orid the {@link #getOrid() ORID}.
   * @return the new {@link OrientId} or {@code null} if the given {@link ORID} was {@code null}.
   */
  public static <E> OrientId<E> of(Class<E> type, ORID orid) {

    return of(type, orid, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param orid the {@link #getOrid() ORID}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link OrientId} or {@code null} if the given {@link ORID} was {@code null}.
   */
  public static <E> OrientId<E> of(Class<E> type, ORID orid, long version) {

    if (orid == null) {
      return null;
    }
    return new OrientId<>(type, orid, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param orid the {@link #getOrid() ORID} given as {@link String}.
   * @return the new {@link OrientId} or {@code null} if the given {@link ORID} was {@code null}.
   */
  public static <E> OrientId<E> of(Class<E> type, String orid) {

    return of(type, new ORecordId(orid), VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param orid the {@link #getOrid() ORID} given as {@link String}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link OrientId} or {@code null} if the given {@link ORID} was {@code null}.
   */
  public static <E> OrientId<E> of(Class<E> type, String orid, long version) {

    if (orid == null) {
      return null;
    }
    return new OrientId<>(type, new ORecordId(orid), version);
  }

}
