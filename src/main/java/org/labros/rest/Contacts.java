package org.labros.rest;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
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
			// From which DB to retrieve the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();

			//Retrieve the contacts from the given connection
			contacts = new ContactsController().getAllContacts(connection);
		} catch (SQLException e) {
			return "[]";
		}
		return new GsonBuilder().create().toJson(contacts);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String insertSomething(String contact)
	{
		try{
			// In which DB to insert the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();
			Contact c = new Contact();
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			c.setDoB(date);
			if(new ContactsController().insertContact(connection, c) == 0)
				return "[]";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GsonBuilder().create().toJson(new ResponseWrapper());
	}
}