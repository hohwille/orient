/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.db;

import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.orientechnologies.orient.core.db.ODatabaseInternal;

/**
 * This is the internal transaction object for OrientDB.
 *
 * @see com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
 * @see javax.transaction.UserTransaction
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientTx {

  private OrientDatabaseImpl database;

  private ODatabaseInternal<?> resource;

  private boolean rollbackOnly;

  /**
   * The constructor.
   *
   * @param database the {@link OrientDatabaseImpl} instance.
   */
  public OrientTx(OrientDatabaseImpl database) {
    super();
    this.database = database;
    this.resource = (ODatabaseInternal<?>) TransactionSynchronizationManager.getResource(this.database);
  }

  /**
   * @return the resource
   */
  public ODatabaseInternal<?> getResource() {

    return this.resource;
  }

  /**
   * @see ODatabaseInternal#begin()
   */
  public void begin() {

    if (this.resource == null) {
      this.resource = this.database.getOrCreateConnection();
      TransactionSynchronizationManager.bindResource(this.database, this.resource);
    }
    this.resource.begin();
  }

  /**
   * @see ODatabaseInternal#commit()
   */
  public void commit() {

    if (this.rollbackOnly) {
      throw new IllegalStateException("Can not commit after rollback-only has been set!");
    }
    this.resource.commit();
  }

  /**
   * @see ODatabaseInternal#rollback()
   */
  public void rollback() {

    this.resource.rollback();
  }

  /**
   * Mark this transaction so it can only be completed by {@link #rollback()} but NOT by {@link #commit()}.
   */
  public void setRollbackOnly() {

    this.rollbackOnly = true;
  }

  /**
   * Disposes this transaction and performs a cleanup to free resources.
   */
  public void dispose() {

    if ((this.resource != null) && (!this.resource.isClosed())) {
      this.resource.close();
    }
    TransactionSynchronizationManager.unbindResource(this.database);
    this.database = null;
    this.resource = null;
  }

  /**
   * @param transaction the untyped {@link OrientTx} from
   *        {@link org.springframework.transaction.support.AbstractPlatformTransactionManager} (that was designed before
   *        generic where supported).
   * @return the casted {@link OrientTx}.
   */
  public static OrientTx of(Object transaction) {

    return (OrientTx) transaction;
  }

  /**
   * @param txStatus the {@link DefaultTransactionStatus}.
   * @return the casted {@link DefaultTransactionStatus#getTransaction() transaction} object.
   */
  public static OrientTx of(DefaultTransactionStatus txStatus) {

    return (OrientTx) txStatus.getTransaction();
  }

}
