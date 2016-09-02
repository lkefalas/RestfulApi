package org.labros.rest;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RESTfulHelloWorld 
{
	@GET
	@Produces("text/html")
	public Response getStartingPage() throws SQLException
	{
		Connection connection = null;
		Statement stmt = null;
		String res = null;
		String sql;
		try{	 
			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:" + 3306 + "/testdb?autoReconnect=true&useSSL=false",
					"root",
					"abc123"
	    	);

			stmt = (Statement) connection.createStatement();	    	 
			sql = "SELECT id, first, last, age FROM Registration";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				int id  = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");	         
				//Display values
				res = "ID: " + id +", Age: " + age	 +", First: " + first +", Last: " + last;         
			}	      
		} catch (SQLException e) {
			e.printStackTrace();		
		} finally {
			connection.close();
		}
		System.out.println("Sending response:" +res);
		//Return the response
		return Response.status(200).entity(res).build();
	}
}