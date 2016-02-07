/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient;

import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;

import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class Server {

  public static void main(String[] args) throws Exception {

    OServer server = OServerMain.create();
    server.startup(new ClasspathResource(Server.class, "orientdb.xml", false).openStream());
    server.activate();
  }

}
