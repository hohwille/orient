/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.db;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.repository.api.OrientBeanRepository;
import net.sf.mmm.util.data.api.id.Id;

/**
 * This is the interface for the low-level abstraction of the Orient-Database. Use {@link OrientBeanRepository} instead
 * for high-level database access.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface OrientDatabase {

  /**
   * @param id the unique ID of the requested {@link ODocument}.
   * @return the {@link ODocument} with the given {@link ODocument#getIdentity() ID} or {@code null} if no such
   *         {@link ODocument} exists.
   */
  ODocument find(Id<?> id);

  /**
   * @param id the {@link Id unique ID} of the {@link ODocument} to delete.
   */
  void delete(Id<?> id);

  /**
   * @return the existing current DB connection or a newly created connection.
   */
  ODatabaseDocumentTx getConnection();

}
