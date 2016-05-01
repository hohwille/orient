/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.orientechnologies.orient.core.db.ODatabaseInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.orient.db.api.OrientDatabase;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.Id;

/**
 * Implementation to wrap OrientDB for spring.
 *
 * @see OrientPlatformTransactionManager
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class OrientDatabaseImpl extends AbstractLoggableComponent implements OrientDatabase {

  private OrientConfigProperties config;

  private OPartitionedDatabasePoolFactory poolFactory;

  private OPartitionedDatabasePool pool;

  private boolean autoConnect;

  private OrientBeanMapper beanMapper;

  /**
   * The constructor.
   */
  public OrientDatabaseImpl() {
    super();
    this.autoConnect = true;
  }

  /**
   * @param config is the {@link OrientConfigProperties} to {@link Inject}.
   */
  @Inject
  public void setConfig(OrientConfigProperties config) {

    getInitializationState().requireNotInitilized();
    this.config = config;
  }

  /**
   * @param beanMapper is the {@link OrientBeanMapper} to {@link Inject}.
   */
  @Inject
  public void setBeanMapper(OrientBeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  /**
   * @param poolFactory is the poolFactory to set
   */
  public void setPoolFactory(OPartitionedDatabasePoolFactory poolFactory) {

    this.poolFactory = poolFactory;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.poolFactory == null) {
      this.poolFactory = new OPartitionedDatabasePoolFactory();
    }
    if (this.config == null) {
      throw new ResourceMissingException(OrientConfigProperties.class.getSimpleName());
    }
    // new ODatabaseDocumentTx(this.config.getUrl());
    this.poolFactory.setMaxPoolSize(this.config.getMaxPoolSize());
    this.pool = this.poolFactory.get(this.config.getUrl(), this.config.getUsername(), this.config.getPassword());
  }

  @Override
  public ODatabaseDocumentTx getConnection() {

    ODatabaseDocumentTx connection = getCurrentConnection();
    if (connection == null) {
      if (this.autoConnect) {
        connection = newConnection();
      } else {
        throw new ResourceMissingException("No open connection for current Thread!");
      }
    }
    return connection;
  }

  protected ODatabaseDocumentTx getCurrentConnection() {

    ODatabaseRecordThreadLocal singleton = ODatabaseRecordThreadLocal.INSTANCE;
    if (singleton.isDefined()) {
      return (ODatabaseDocumentTx) singleton.get().getDatabaseOwner();
    }
    return null;
  }

  /**
   * @return the current running transaction or a {@link #newConnection() new transaction}.
   */
  protected ODatabaseInternal<?> getOrCreateConnection() {

    ODatabaseInternal<?> connection = getCurrentConnection();
    if (connection == null) {
      connection = newConnection();
    }
    return connection;
  }

  /**
   * @return a new {@link ODatabaseDocumentTx} as transaction for OrientDB document API.
   */
  protected ODatabaseDocumentTx newConnection() {

    return this.pool.acquire();
  }

  /**
   * @param id the {@link ODocument#getIdentity() ID} as {@link String}.
   * @return the given {@code id} parsed as {@link ORID}.
   */
  @SuppressWarnings("unchecked")
  protected ORID convertId(Id<?> id) {

    return this.beanMapper.convertId((Id<? extends OrientBean>) id);
  }

  @Override
  public ODocument find(Id<?> id) {

    return getConnection().load(convertId(id));
  }

  @Override
  public void delete(Id<?> id) {

    getConnection().delete(convertId(id));
  }
}
