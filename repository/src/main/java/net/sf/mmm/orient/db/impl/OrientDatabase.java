/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import com.orientechnologies.orient.core.db.ODatabaseInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientDatabase {

  /**
   * The constructor.
   */
  public OrientDatabase() {
    super();
    OPartitionedDatabasePoolFactory factory = new OPartitionedDatabasePoolFactory();
    OPartitionedDatabasePool pool = factory.get("remote:localhost/mmm", "admin", "admin");
    ODatabaseRecordThreadLocal orlt = ODatabaseRecordThreadLocal.INSTANCE;
    if (orlt.isDefined()) {
      ODatabaseInternal<?> database = orlt.get().getDatabaseOwner();
    }
  }

}
