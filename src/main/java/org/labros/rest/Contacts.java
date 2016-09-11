package org.labros.rest;

import com.google.gson.GsonBuilder;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.labros.rest.Model.*;
import org.labros.rest.DAO.ConnectionFactory;

@Path("/contacts")
public class Contacts {
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public String getStartingPage() throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql;

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try{
			connection = ConnectionFactory.getConnection();
			stmt = (Statement) connection.createStatement();
			sql = "SELECT Id, Name, Surname, DoB FROM Contact";
			rs = stmt.executeQuery(sql);

			//Extract data from result set
			while(rs.next()){
				Contact c = new Contact();
				//Retrieve by column name
				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setSurname(rs.getString("Surname"));
				c.setDoB(rs.getDate("DoB"));
				//Display values
				contacts.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection,stmt,rs);
		}

		ContactsWrapper rw = new ContactsWrapper();
		rw.setFoo(contacts);
		return new GsonBuilder().create().toJson(rw);
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public String insertSomething() throws SQLException
	{
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql;

		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt = new Date();
			connection = ConnectionFactory.getConnection();
			stmt = (Statement) connection.createStatement();
			sql = "INSERT INTO Contact (Name,Surname,DoB) VALUES('Some','One','"+formatter.format(dt)+"')";
			System.out.println("Inserted into the DB");
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection,stmt,rs);
		}
		return new GsonBuilder().create().toJson(new ResponseWrapper());
	}
}