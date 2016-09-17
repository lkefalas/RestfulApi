package org.labros.rest.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.labros.rest.DAO.ConnectionFactory;
import org.labros.rest.Model.Contact;

public class ContactsController {
	//Retrieve all contacts from the given connection
	public List<Contact> getAllContacts(Connection connection) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {
			stmt = connection.prepareStatement("SELECT Id, Name, Surname, DoB FROM Contact");
	
			rs = stmt.executeQuery();

			//Extract data from result set
			while(rs != null && rs.next()){
				Contact c = new Contact();
				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setSurname(rs.getString("Surname"));
				c.setDoB(rs.getDate("DoB"));
				contacts.add(c);
			}
		} catch (SQLException e) {
			return null;
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
}