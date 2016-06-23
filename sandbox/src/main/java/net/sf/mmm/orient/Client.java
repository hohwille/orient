/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient;

import java.util.Arrays;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.db.record.ridbag.ORidBag;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.metadata.OMetadataDefault;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.record.ORecord;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class Client {

  public static void main(String[] args) {

    OPartitionedDatabasePoolFactory factory = new OPartitionedDatabasePoolFactory();
    OPartitionedDatabasePool pool = factory.get("remote:localhost/mmm", "admin", "admin");
    ODatabaseDocumentTx db = pool.acquire();
    try {
      // Object result = db
      // .command(
      // new OCommandSQL("INSERT INTO Person (FirstName, LastName, Gender) VALUES ('Heinz','Kunze','M')"))
      // .execute();
      // System.out.println(result);
      ORecordIteratorClass<ODocument> iterator = db.browseClass("DEBundesland");
      if (iterator.hasNext()) {
        ODocument document = iterator.next();
        System.out.println(document);
        ORID identity = document.getIdentity();
        System.out.println(identity);
        System.out.println(Arrays.toString(document.fieldNames()));
        ODocument field = document.field("Country");
        System.out.println(field);
        identity = new ORecordId(17, 498);
        ORecord record = db.load(identity);
        System.out.println(record);
        System.out.println(record.getClass());
        System.out.println(record == document);
      }
      OMetadataDefault metadata = db.getMetadata();
      OSchemaProxy schema = metadata.getSchema();
      OClass country = schema.getClass("Country");
      for (OProperty property : country.properties()) {
        System.out
            .println(property.getName() + ", type:" + property.getType() + ", linkedType: " + property.getLinkedType()
                + ", linkedClass:" + property.getLinkedClass() + ", regexp:" + property.getRegexp() + ", min:"
                + property.getMin() + ", max:" + property.getMax() + ", defaultValue:" + property.getDefaultValue()
                + ", fullName:" + property.getFullName() + ", collate:" + property.getCollate());
      }

      ODocument doc = db.load(new ORecordId("#17:501"));
      System.out.println(Arrays.toString(doc.fieldNames()));
      ORidBag out = doc.field("out_");
      if (out != null) {
        System.out.println(out);
        for (OIdentifiable id : out) {
          System.out.println(id);
        }
        System.out.println(out.getClass());
        ORidBag outTE = doc.field("out_TypedEdge");
        System.out.println(outTE);
        for (OIdentifiable id : outTE) {
          System.out.println(id.getIdentity());
        }
        System.out.println(outTE.getClass());
      }
    } finally {
      db.close();
      factory.close();
    }
  }

}
