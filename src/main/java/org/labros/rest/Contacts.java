package org.labros.rest;

import javax.validation.Valid;
import org.labros.rest.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/Phonebook")
public class Contacts {
	@Autowired
	private ContactDao contactDao;

	@RequestMapping(value ="/contacts", method = RequestMethod.GET)
	public ResponseEntity<ContactsWrapper> getContacts() {
		ContactsWrapper contacts = new ContactsWrapper();
		HttpStatus status = HttpStatus.OK;

		try{
			contacts.setFoo(
					contactDao.findAll()
					);
		} catch (Exception e) {
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
		contactDao.save(c);
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
