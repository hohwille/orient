/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;

/**
 * TODO: this class ...
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

    // TODO Auto-generated method stub
    // TransactionSynchronizationManager.getResource();
    return null;
  }

  @Override
  protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {

    // TODO Auto-generated method stub

  }

  @Override
  protected void doCommit(DefaultTransactionStatus status) throws TransactionException {

    // TODO Auto-generated method stub

  }

  @Override
  protected void doRollback(DefaultTransactionStatus status) throws TransactionException {

    // TODO Auto-generated method stub

  }

}
