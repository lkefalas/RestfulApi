package org.labros.rest.Model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactsWrapper {

	@JsonProperty("contacts")
	private List<Contact> contacts = null;

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setFoo(List<Contact> contacts) {
		this.contacts= contacts;
	}
}