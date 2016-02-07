/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class JdbcClient {

  public static void main(String[] args) throws Exception {

    Class.forName("com.orientechnologies.orient.jdbc.OrientJdbcDriver");

    Properties info = new Properties();
    info.put("user", "admin");
    info.put("password", "admin");

    Connection conn = DriverManager.getConnection("jdbc:orient:remote:localhost/mmm", info);
    Statement statement = conn.createStatement();
    ResultSet resultSet = statement
        .executeQuery("INSERT INTO Person (FirstName, LastName, Gender) VALUES ('Heinz','Kunze','M')");
    System.out.println(resultSet);
    conn.close();
  }

}
