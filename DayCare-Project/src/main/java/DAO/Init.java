/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author zhengbochao
 */
public class Init {

    public Init(){
        Connection c = null;
        Statement stmt = null;
      
      try {
         Class.forName("org.h2.Driver");
         c = DriverManager.getConnection("jdbc:h2:./daycare", "sa","");
//         org.h2.tools.Server.startWebServer(c);
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "CREATE TABLE VACCINE " +
                        "(ID INT  PRIMARY KEY    NOT NULL," +
                        "STUDENTID INT      NOT NULL," +
                        "TYPE TEXT      NOT NULL," +
                        "TIMEINTERVAL INT      NOT NULL," +
                        "LASTDOSEDATE DATE      NOT NULL," +
                        "CURRDOSE INT      NOT NULL," +
                        "TOTALDOSE INT      NOT NULL)"; 
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//         System.exit(0);
      }
      System.out.println("Table created successfully");
    }
    
//    public static void add(People people) {
//        Connection c = null;
//        Statement stmt = null;
//
//        try {
//           Class.forName("org.h2.Driver");
//           c = DriverManager.getConnection("jdbc:h2:~/testa", "sa","");
//           c.setAutoCommit(false);
//           System.out.println("Opened database successfully");
//
//           stmt = c.createStatement();
//           String sql = "INSERT INTO PEOPLE (ID,NAME) " +
//                          "VALUES (" + people.getId() +",'" +  people.getName() +"');"; 
//           stmt.executeUpdate(sql);
//           stmt.close();
//           c.commit();
//           c.close();
//        } catch ( Exception e ) {
//           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//           System.exit(0);
//        }
//        System.out.println("Records created successfully");
//    }
    
    public static void main(String[] args) {
        Init sqlLiteJDBC = new Init();
    }
}
