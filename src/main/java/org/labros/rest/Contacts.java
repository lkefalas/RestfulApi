package org.labros.rest;

import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.labros.rest.Model.*;
import org.labros.rest.Controller.ContactsController;
import org.labros.rest.DAO.ConnectionFactory;

@Path("/contacts")
public class Contacts {
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public String getContacts() {
		List<Contact> contacts = null;

		try {
			// From wich DB to retrieve the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();

			//Retrieve the contacts from the given connection
			contacts = new ContactsController().getAllContacts(connection);
		} catch (SQLException e) {
			return null;
		}

		return new GsonBuilder().create().toJson(contacts);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String insertSomething(String contact) throws SQLException
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