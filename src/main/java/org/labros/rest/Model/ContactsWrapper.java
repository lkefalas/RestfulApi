package org.labros.rest.Model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ContactsWrapper {

	String respStatus = "success";
	
	@SerializedName("contacts")
	private List<Contact> foo = null;

	public List<Contact> getFoo() {
		return foo;
	}

	public void setFoo(List<Contact> foo) {
		this.foo= foo;
	}
}