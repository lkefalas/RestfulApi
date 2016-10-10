package org.labros.rest;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.Collections;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import org.labros.rest.Model.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.labros.rest.Controller.ContactsController;
import org.labros.rest.DAO.ConnectionFactory;

@RestController
public class Contacts {
	
	@RequestMapping(value ="/contacts", method = RequestMethod.GET)
	public List<Contact> getContacts() {
		List<Contact> contacts = null;

		try {
			// From which DB to retrieve the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();

			//Retrieve the contacts from the given connection
			contacts = new ContactsController().getAllContacts(connection);
		} catch (SQLException e) {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			return Collections.emptyList();
		}
		return contacts;
	}

//	@POST
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces({MediaType.APPLICATION_JSON})
	@RequestMapping(value ="/contacts", method = RequestMethod.POST)
	public String insertContact(@RequestBody String req)
	{
		Contact c = new Contact();
		
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(req);
		try{
			c = new GsonBuilder().create().fromJson(req, Contact.class);
		} catch (JsonSyntaxException e) {
			return new GsonBuilder().create().toJson(new ResponseWrapper("Error", "Validation error"));
		}

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