package org.labros.rest.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contact")
public class Contact{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	private String dob;

	public Contact() {

	}

	public Contact(long id) { 
		this.id = id;
	}
	public Contact(String name, String surname, String dob)
	{
		this.name = name;
		this.surname = surname;
		this.dob = dob;
	}

	public long getId() {
		return id;
	}

	public void setId(long value) {
		this.id = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String value) {
		this.surname = value;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String value) {
		this.dob = value;
	}
}
