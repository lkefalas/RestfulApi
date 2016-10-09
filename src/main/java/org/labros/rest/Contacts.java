package org.labros.rest;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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
	public String insertContact(String req)
	{
		Contact c = new Contact();

		try{
			c = new GsonBuilder().create().fromJson(req, Contact.class);
		} catch (JsonSyntaxException e) {
			return new GsonBuilder().create().toJson(new ResponseWrapper("Error", "Validation error"));
		}

		if(!c.validate())
			return new GsonBuilder().create().toJson(new ResponseWrapper("Error", "Validation error"));

		try{
			// In which DB to insert the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();

			if(new ContactsController().insertContact(connection, c) == 0)
				return new GsonBuilder().create().toJson(new ResponseWrapper("Error", "DB error"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send the response
		return new GsonBuilder().create().toJson(new ResponseWrapper("Success"));
	}
}