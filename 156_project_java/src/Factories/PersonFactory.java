package Factories;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataContainers.Person;
import database.Database;

/**
 * This class shall provide cheap, easy and clean access to renewable drinking -- wait,
 * no, nevermind. This class shall provide access to Person objects, creating them
 * from the database.
 * @author Grant
 *
 */
public class PersonFactory {
	
	/**
	 * This method shall create a person object given some position in the database,
	 * given by an integer, based off the personID column.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Person createPerson(int id) throws SQLException, ClassNotFoundException {
		
		Person pers = new Person();
		Database db = new Database();
		AddressFactory factoryA = new AddressFactory();
		EmailFactory factoryE = new EmailFactory();
		ResultSet rt = db.getRowsFromTable("Person", "personID", id);
		rt.next();
		pers.setPersonCode(rt.getString("personCode"));
		pers.setFirstName(rt.getString("firstName"));
		pers.setLastName(rt.getString("lastName"));
		pers.setEmails(factoryE.createEmail(id));
		
		int addressID = rt.getInt("addressID");
		pers.setAddress(factoryA.createAddress(addressID));	
		
		rt.close();
		db.close();
		return pers;
		
	}
	
	/**
	 * This method is designed to create
	 */

}
