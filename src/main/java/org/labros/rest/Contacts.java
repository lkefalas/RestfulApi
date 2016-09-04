package org.labros.rest;

import com.mysql.jdbc.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.labros.rest.Model.Contact;
import org.labros.rest.Model.ResponseList;

@Path("/contacts")
public class Contacts {
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseList getStartingPage() throws SQLException
	{
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:" + 3306 + "/phonebookdb?autoReconnect=true&useSSL=false",
				"root",
				"root_password"
		);
		Statement stmt = null;
		String sql;
		ArrayList<Object> contacts = new ArrayList<Object>();
		try{	 
			stmt = (Statement) connection.createStatement();
			sql = "SELECT Id, Name, Surname, DoB FROM Contact";
			ResultSet rs = stmt.executeQuery(sql);

			//Extract data from result set
			while(rs.next()){
				Contact c = new Contact();
				//Retrieve by column name
				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setSurname(rs.getString("Surname"));
				c.setDoB(rs.getDate("DoB"));
				//Display values
				contacts.add(c);
				System.out.println("Sending response:" +c.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		ResponseList res = new ResponseList();
		res.setList(contacts);
		return res;
	}

	@GET
	@Path("/insert")
	@Produces("text/html")
	public Response insertSomething() throws SQLException
	{
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:" + 3306 + "/phonebookdb?autoReconnect=true&useSSL=false",
			    "root",
			    "root_password"
		);
		Statement stmt = null;
		String sql;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt = new Date();
			stmt = (Statement) connection.createStatement();
			sql = "INSERT INTO Contact (Name,Surname,DoB) VALUES('Some','One','"+formatter.format(dt)+"')";
			System.out.println("Inserted into the DB");
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return Response.status(200)
				.entity("Entry inserted on " + LocalDateTime.now())
				.build();
	}
}
