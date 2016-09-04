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
            	ex.printStackTrace();
            }
        }
        
        // MySQL driver leaves around a thread. This static method cleans it up.
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            // again failure, not much you can do
        	e.printStackTrace();
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
			e1.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(
				    "jdbc:mysql://127.0.0.1:" + 3306 + "/phonebookdb?autoReconnect=true&useSSL=false",
				    "root",
				    "root_password"
			);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			 stmt = (Statement) connection.createStatement();
		      
		      String sql = "CREATE DATABASE IF NOT EXISTS Phonebook";
		      stmt.executeUpdate(sql);
		      stmt = (Statement) connection.createStatement();

		      sql = "CREATE TABLE IF NOT EXISTS Contact " +
	                   "(Id INTEGER not NULL AUTO_INCREMENT, " +
	                   " Name VARCHAR(255), " + 
	                   " Surname VARCHAR(255), " + 
	                   " DoB DATETIME, " + 
	                   " PRIMARY KEY ( id ))"; 

		      stmt.executeUpdate(sql);

		      stmt.executeUpdate(sql);
		      connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
