/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient;

import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.OMetadata;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.orient.data.bean.api.AbstractCode;
import net.sf.mmm.orient.data.bean.api.world.Country;
import net.sf.mmm.orient.impl.mapping.OrientBeanMapperImpl;
import net.sf.mmm.orient.impl.mapping.OrientMappingConfigProperties;

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
    OrientDBConfig configuration = OrientDBConfig.defaultConfig();
    OrientDB db = new OrientDB("remote:localhost", configuration);
    ODatabaseDocument session = db.open("mmm", "admin", "admin");
    // ODatabaseDocumentTx connection = pool.acquire();
    OMetadata metadata = session.getMetadata();
    beanMapper.syncSchema(metadata.getSchema());
    ODocument doc = session.load(new ORecordId("#17:501"));
    Country bean = beanMapper.toBean(doc);
    System.out.println(bean);
    bean.Inhabitants().set(9876543210L);
    doc = beanMapper.fromBean(bean);
    Object field = doc.field("Inhabitants");
    System.out.println(field);
    doc.save();
    session.close();
  }

  private static OrientBeanMapper createBeanMapper() {

    OrientBeanMapperImpl beanMapperImpl = new OrientBeanMapperImpl();
    OrientMappingConfigProperties config = new OrientMappingConfigProperties();
    config.addPackagesToScan(AbstractCode.class.getPackage().getName());
    beanMapperImpl.setConfig(config);
    beanMapperImpl.initialize();
    return beanMapperImpl;
  }

}
