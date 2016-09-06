package org.labros.rest;

import com.google.gson.GsonBuilder;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.labros.rest.Model.Contact;
import org.labros.rest.Model.ContactsWrapper;
import org.labros.rest.Model.ResponseWrapper;
import org.labros.rest.Properties.Property;

@Path("/contacts")
public class Contacts {
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public String getStartingPage() throws SQLException {
		String port = Property.getMyProperty("db_port");
		String database = Property.getMyProperty("db_database");
		String root_user = Property.getMyProperty("db_root_user");
		String root_password = Property.getMyProperty("db_root_pass");
		String db_url = Property.getMyProperty("db_url");
		
		Connection connection = DriverManager.getConnection(
			    db_url + ":" + port +
			    "/" + database +"?autoReconnect=true&useSSL=false",
			    root_user,
			    root_password
		);
		Statement stmt = null;
		String sql;
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try{	 
			stmt = (Statement) connection.createStatement();
			sql = "SELECT Id, Name, Surname, DoB FROM Contact";
			ResultSet rs = stmt.executeQuery(sql);

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
			connection.close();
		}

		ContactsWrapper rw = new ContactsWrapper();
		rw.setFoo(contacts);
		return new GsonBuilder().create().toJson(rw);
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public String insertSomething() throws SQLException
	{
		String port = Property.getMyProperty("db_port");
		String database = Property.getMyProperty("db_database");
		String root_user = Property.getMyProperty("db_root_user");
		String root_password = Property.getMyProperty("db_root_pass");
		String db_url = Property.getMyProperty("db_url");
		
		Connection connection = DriverManager.getConnection(
			    db_url + ":" + port +
			    "/" + database +"?autoReconnect=true&useSSL=false",
			    root_user,
			    root_password
		);
		Statement stmt = null;
		String sql;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt = new Date();
			stmt = (Statement) connection.createStatement();
			sql = "INSERT INTO Contact (Name,Surname,DoB) VALUES('Some','One','"+formatter.format(dt)+"')";
			System.out.println("Inserted into the DB");
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return new GsonBuilder().create().toJson(new ResponseWrapper());
	}
}
