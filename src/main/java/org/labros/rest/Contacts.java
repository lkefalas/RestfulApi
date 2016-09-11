package org.labros.rest;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.labros.rest.Model.*;
import org.labros.rest.DAO.ConnectionFactory;

@Path("/contacts")
public class Contacts {
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public String getStartingPage() throws SQLException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try{
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("SELECT Id, Name, Surname, DoB FROM Contact");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
			if(stmt != null) {
				stmt.close();
			}
		}

		//Extract data from result set
		while(rs != null && rs.next()){
			Contact c = new Contact();
			c.setId(rs.getInt("Id"));
			c.setName(rs.getString("Name"));
			c.setSurname(rs.getString("Surname"));
			c.setDoB(rs.getDate("DoB"));
			contacts.add(c);
		}

		if(rs != null) {
			rs.close();
		}

		ContactsWrapper rw = new ContactsWrapper();
		rw.setFoo(contacts);
		return new GsonBuilder().create().toJson(rw);
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public String insertSomething() throws SQLException
	{
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt = new Date();
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("INSERT INTO Contact (Name,Surname,DoB) VALUES('Some','One','"+formatter.format(dt)+"')");
			System.out.println("Inserted into the DB");
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
		}
		return new GsonBuilder().create().toJson(new ResponseWrapper());
	}
}