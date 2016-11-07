package com.ceg.ext;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import database.Database;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Do not change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database. This method also
	 * removes all email addresses (since these are only associated with Person table)
	 * and also removes any rows in the address table which match with the addresses
	 * of the Person table. This is to prevent the storing of data which is not associated
	 * with anything.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void removeAllPersons() throws ClassNotFoundException, SQLException {
		
		Database db = new Database();
		Connection conn = db.connectToDB();
		
		PreparedStatement ps = null;
		
		String[] queries = new String[6];
		
		queries[0] = "SET foreign_key_checks = 0";
		queries[1] = "DELETE FROM Address USING Address, Person" 
				+" WHERE Address.addressID = Person.AddressID";
		queries[2] = "DELETE FROM Person"; 
		queries[3] = "ALTER TABLE Person AUTO_INCREMENT = 1";
		queries[4] = "DELETE FROM Email";
		queries[5] = queries[3];
		
		for(int i = 0; i < 6; i++) {
			ps = (PreparedStatement) conn.prepareStatement(queries[i]);
			ps.executeUpdate();
		}
		
		ps.close();
		conn.close();
		db.close();
		
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) throws ClassNotFoundException, SQLException {
		
		Database db = new Database();
		Connection conn = db.connectToDB();
		
		PreparedStatement ps = null;
		int addressID = 0;
		
		
		String query1 = "INSERT INTO Address (street,city,state,zip,country) "
				+"VALUES (?,?,?,?,?)";
		String query2 =  "SELECT LAST_INSERT_ID() FROM Address";
		String query3 = "INSERT INTO Person (personCode, firstName, lastName, addressID)"
				+ " VALUES (?,?,?,?)";
		
		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, street);
		ps.setString(2, city);
		ps.setString(3, state);
		ps.setString(4, zip);
		ps.setString(5, country);
		ps.executeUpdate();
		
		ps = (PreparedStatement) conn.prepareStatement(query2);
		ResultSet rt = (ResultSet) ps.executeQuery();
		rt.next();
		addressID = rt.getInt("LAST_INSERT_ID()");
		
		ps = (PreparedStatement) conn.prepareStatement(query3);
		ps.setString(1, personCode);
		ps.setString(2, firstName);
		ps.setString(3, lastName);
		ps.setInt(4, addressID);
		ps.executeUpdate();
		
		rt.close();
		ps.close();
		conn.close();
		db.close();
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {}
	
	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {}

	/**
	 * 10. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: ticketCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {}
	
    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {}

}