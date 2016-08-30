package org.labros.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mysql.jdbc.Statement;

@Path("/")
public class RESTfulHelloWorld 
{
	@GET
	@Produces("text/html")
	public Response getStartingPage() throws SQLException
	{
		Connection connection;
		Statement stmt;
		String res = null;
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
		      
		      String sql = "CREATE DATABASE STUDENTS";
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
	      
	      sql = "INSERT INTO Registration " +
	                   "VALUES (101, 'Zara', 'Ali', 18)";
	      
	      stmt.executeUpdate(sql);
	      
	      sql = "SELECT id, first, last, age FROM Registration";
	      ResultSet rs = stmt.executeQuery(sql);
	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("id");
	         int age = rs.getInt("age");
	         String first = rs.getString("first");
	         String last = rs.getString("last");
	         
	         //Display valuesd
	         res = "ID: " + id +", Age: " + age	 +", First: " + first +", Last: " + last;
	      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Creating table in given database...");
	      
		String output = "<h1>Hello World!<h1>" +
				"<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>"+"<br>\nFinalyyy!!!&amp";
		return Response.status(200).entity(res).build();
	}
}