package org.labros.rest.DAO;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import org.labros.rest.Properties.Property;

public class ConnectionFactory{
	private static String port;
	private static String database;
	private static String root_user;
	private static String root_password;
	private static String db_url;
	
	//Get all the db properties
	static{
		try{
			port = Property.getMyProperty("db_port");
			database = Property.getMyProperty("db_database");
			root_user = Property.getMyProperty("db_root_user");
			root_password = Property.getMyProperty("db_root_pass");
			db_url = Property.getMyProperty("db_url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				db_url + ":" + port +
				"/" + database +"?autoReconnect=true&useSSL=false",
				root_user,
				root_password
		);
	}

	//Make sure you close the connection, statement and resultset
	//But it doesn't adhere to the single responsibility rule.......
	public static void closeConnection(Connection con, Statement st, ResultSet rs) throws SQLException {
		if(con!=null)
			con.close();
		if(st!=null)
			st.close();
		if(rs!=null)
			rs.close();
	}
}
