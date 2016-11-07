package Factories;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataContainers.Customer;
import dataContainers.General;
import dataContainers.Student;
import database.Database;

/**
 * A class to make Customer objects given information from a MySQL database.
 * @author Grant
 *
 */
public class CustomerFactory {

	/**
	 * Method which creates a Customer object given a specific customerID which 
	 * corresponds to the primary key in the Customer table.
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Customer createCustomer(int id) throws SQLException, ClassNotFoundException {
		
		Customer cust = null;
		Database db = new Database();
		PersonFactory factoryP = new PersonFactory();
		AddressFactory factoryA = new AddressFactory();
		ResultSet rt = db.getRowsFromTable("Customer", "customerID", id);
		
		rt.next();
		String type = rt.getString("subClass");
		switch(type) {
		case("G"):
			cust = new General();
			break;
		case("S"):
			cust = new Student();
			break;	
		}
		
		cust.setCustomerCode(rt.getString("customerCode"));
		cust.setCustomerName(rt.getString("customerName"));
		cust.setPrimaryContact(factoryP.createPerson(rt.getInt("personID")));
		cust.setCustomerAddress(factoryA.createAddress(rt.getInt("addressID")));
		
		rt.close();
		db.close();
		return cust;
	}
	
}
