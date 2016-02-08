/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;

/**
 * Implementation of {@link AbstractPlatformTransactionManager} to integrate OrientDB with Spring-TX.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientPlatformTransactionManager extends AbstractPlatformTransactionManager
    implements ResourceTransactionManager {

  private OrientDatabase database;

  /**
   * The constructor.
   */
  public OrientPlatformTransactionManager() {
    super();
  }

  @Override
  public OrientDatabase getResourceFactory() {

    return this.database;
  }

  @Override
  protected Object doGetTransaction() throws TransactionException {

    return new OrientTx(this.database);
  }

  @Override
  protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {

    OrientTx.of(transaction).begin();
  }

  @Override
  protected void doCommit(DefaultTransactionStatus status) throws TransactionException {

    OrientTx.of(status).commit();
  }

  @Override
  protected void doRollback(DefaultTransactionStatus status) throws TransactionException {

    OrientTx.of(status).rollback();
  }

  @Override
  protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {

    status.setRollbackOnly();
    OrientTx.of(status).setRollbackOnly();
  }

  @Override
  protected void doCleanupAfterCompletion(Object transaction) {

    super.doCleanupAfterCompletion(transaction);
    OrientTx.of(transaction).dispose();
  }

}
