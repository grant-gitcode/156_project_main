package Factories;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataContainers.Address;
import database.Database;

/**
 * The AddressFactory class provides a way to quickly and easily create Address 
 * objects given information in a database.
 * @author Grant
 *
 */
public class AddressFactory {
	
	/**
	 * The purpose of this method is to be able to create Address objects quickly and
	 * easily.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Address createAddress(int id) throws SQLException, ClassNotFoundException {
		
		Address addr = new Address();
		Database db = new Database();
		ResultSet rt = db.getRowsFromTable("Address", "addressID", id);
		rt.next();
		addr.setStreet(rt.getString("street"));
		addr.setCity(rt.getString("city"));
		addr.setState(rt.getString("state"));
		addr.setZip(rt.getString("zip"));
		addr.setCountry(rt.getString("country"));
		
		rt.close();
		db.close();
		return addr;
	}
	
}
