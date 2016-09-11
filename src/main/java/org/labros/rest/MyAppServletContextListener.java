package org.labros.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.labros.rest.DAO.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyAppServletContextListener
					implements ServletContextListener{

	Connection connection;
	PreparedStatement stmt;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	//Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started");

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS Phonebook");
			stmt.execute();

			stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Contact " +
					"(Id INTEGER not NULL AUTO_INCREMENT, " +
					" Name VARCHAR(255), " +
					" Surname VARCHAR(255), " +
					" DoB DATETIME, " +
					" PRIMARY KEY ( id ))");
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				ConnectionFactory.closeConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
