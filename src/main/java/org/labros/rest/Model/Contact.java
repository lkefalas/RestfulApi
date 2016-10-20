package org.labros.rest.Model;

import org.hibernate.validator.constraints.NotEmpty;

public class Contact {

	private int id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String surname;

	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dob;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}
