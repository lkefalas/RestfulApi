package org.labros.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.mysql.jdbc.Statement;

public class MyAppServletContextListener
               implements ServletContextListener{

	Connection connection;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
		Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();     

        java.sql.Driver driver = null;
        // clear drivers
        while(drivers.hasMoreElements()) {
            try {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);

            } catch (SQLException ex) {
                // deregistration failed, might want to do something, log at the very least
            }
        }

        // MySQL driver leaves around a thread. This static method cleans it up.
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            // again failure, not much you can do
        }
	}

	//Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started");
		
		Statement stmt;
		System.out.println("Creating table in given database...");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 connection = DriverManager.getConnection(
			    "jdbc:mysql://127.0.0.1:" + 3306 + "/testdb",
			    "root",
			    "abc123"
			);
			 
			 stmt = (Statement) connection.createStatement();
		      
		      String sql = "CREATE DATABASE IF NOT EXISTS STUDENTS";
		      stmt.executeUpdate(sql);
			stmt = (Statement) connection.createStatement();

			sql = "CREATE TABLE IF NOT EXISTS Registration " +
	                   "(id INTEGER not NULL, " +
	                   " first VARCHAR(255), " + 
	                   " last VARCHAR(255), " + 
	                   " age INTEGER, " + 
	                   " PRIMARY KEY ( id ))"; 

	      stmt.executeUpdate(sql);
	      
	      stmt = (Statement) connection.createStatement();
	      
	      sql = "INSERT IGNORE INTO Registration " +
	                   "VALUES (101, 'Zara', 'Ali', 18)";
	      
	      stmt.executeUpdate(sql);
	      connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
