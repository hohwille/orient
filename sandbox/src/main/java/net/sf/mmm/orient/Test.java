/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.OMetadataDefault;

import net.sf.mmm.orient.data.bean.api.AbstractCode;
import net.sf.mmm.orient.db.impl.OrientBeanMapper;
import net.sf.mmm.orient.db.impl.OrientBeanMapperImpl;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public class Test {

  private OrientBeanMapper beanMapper;

  public static void main(String[] args) {

    OrientBeanMapper beanMapper = createBeanMapper();
    OPartitionedDatabasePoolFactory factory = new OPartitionedDatabasePoolFactory();
    OPartitionedDatabasePool pool = factory.get("remote:localhost/mmm", "admin", "admin");
    ODatabaseDocumentTx connection = pool.acquire();
    OMetadataDefault metadata = connection.getMetadata();
    beanMapper.syncSchema(metadata.getSchema());
    connection.close();
  }

  private static OrientBeanMapper createBeanMapper() {

    OrientBeanMapperImpl beanMapperImpl = new OrientBeanMapperImpl();
    beanMapperImpl.addPackagesToScan(AbstractCode.class.getPackage().getName());
    beanMapperImpl.initialize();
    return beanMapperImpl;
  }

}
