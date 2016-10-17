package org.labros.rest;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.SQLException;
import org.labros.rest.Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.labros.rest.Controller.ContactsController;
import org.labros.rest.DAO.ConnectionFactory;

@RestController
public class Contacts {

	@RequestMapping(value ="/contacts", method = RequestMethod.GET)
	public ResponseEntity<ContactsWrapper> getContacts() {
		ContactsWrapper contacts = new ContactsWrapper();
		HttpStatus status = HttpStatus.OK;
		try {
			// From which DB to retrieve the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();

			//Retrieve the contacts from the given connection
			contacts.setFoo(new ContactsController()
								.getAllContacts(connection));
		} catch (SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<ContactsWrapper>(
					contacts,
					status
				);
	}

	@RequestMapping(value ="/contacts", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseWrapper> insertContact(@Valid @RequestBody Contact c)
	{
		HttpStatus status = HttpStatus.OK;
		try{
			// In which DB to insert the contacts
			Connection connection = (Connection)ConnectionFactory.getConnection();

			//Check if the record is inserted
			if(new ContactsController().insertContact(connection, c) == 0)
				status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		// Send the response
		return new ResponseEntity<ResponseWrapper>(
											new ResponseWrapper(status.toString()),
											status
										);
	}
}