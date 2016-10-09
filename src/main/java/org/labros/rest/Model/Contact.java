package org.labros.rest.Model;

import java.util.Date;

public class Contact {

	private int Id;
	private String Name;
	private String Surname;
	private Date DoB;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public Date getDoB() {
		return DoB;
	}

	public void setDoB(Date doB) {
		DoB = doB;
	}
	
	@Override
	public String toString() {
		return "Contact [Id=" + Id + ", Name=" +Name +", Surname=" + Surname + ", DoB=" + DoB.toString()+"]";
	}

	public Boolean validate()
	{
		if(Name==null || Surname==null || DoB==null)
			return false;
		if(DoB.after(new Date()))
			return false;
		return true;
	}
}
