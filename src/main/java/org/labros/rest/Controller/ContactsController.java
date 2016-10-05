package org.labros.rest.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.labros.rest.DAO.ConnectionFactory;
import org.labros.rest.Model.Contact;

public class ContactsController {

	private String selectContacts = "SELECT Id, Name, Surname, DoB FROM Contact";
	private String insertContact = "INSERT INTO Contact (Name,Surname,Dob) VALUES(?,?,?)";

	//Retrieves all contacts from the given connection
	public List<Contact> getAllContacts(Connection connection) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {
			stmt = connection.prepareStatement(selectContacts);
			rs = stmt.executeQuery();

			//Extract data from result set
			while(rs != null && rs.next()){
				Contact c = new Contact();
				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setSurname(rs.getString("Surname"));
				c.setDoB(rs.getDate("DoB"));
				contacts.add(c);
				System.out.println(c.getName() + " " + c.getSurname());
			}
		} catch (SQLException e) {
			return Collections.emptyList();
		} finally {
			ConnectionFactory.closeConnection(connection);
			if(stmt != null) {
				stmt.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
		return contacts;
	}

	//Retrieves all contacts from the given connection
	public int insertContact(Connection connection, Contact c) throws SQLException {
		PreparedStatement stmt = null;

		try {
			stmt = connection.prepareStatement(insertContact);
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getSurname());
			stmt.setString(3, "2016-01-01 00:00:00");
			return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			ConnectionFactory.closeConnection(connection);
			if(stmt != null) {
				stmt.close();
			}
		}
	}
}