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
	public static void removeAllPersons() {
		try{
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
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

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
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		try{
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
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		
	}
	//do these methods!!! sean
	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addEmail(String personCode, String email) {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;


		String query1 = "INSERT INTO Person (personCode) "
				+"VALUES (?)";
		String query2 = "INSERT INTO Email (emailAddress)"
				+ " VALUES (?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, personCode);
		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query2);
		ps.setString(1, email);
		ps.executeUpdate();

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}
	/**
	 * 4. Method that removes every customer record from the database
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void removeAllCustomers(){
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String[] queries = new String[6];

		queries[0] = "SET foreign_key_checks = 0";
		queries[1] = "DELETE FROM Customer"; 
		queries[2] = "ALTER TABLE Customer AUTO_INCREMENT = 1";
		queries[3] = queries[2];

		for(int i = 0; i < 4; i++) {
			ps = (PreparedStatement) conn.prepareStatement(queries[i]);
			ps.executeUpdate();
		}

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country){
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;
		int addressID = 0;


		String query1 = "INSERT INTO Address (street,city,state,zip,country) "
				+"VALUES (?,?,?,?,?)";
		String query2 =  "SELECT LAST_INSERT_ID() FROM Address";
		String query3 = "INSERT INTO Customer (customerCode, customerType, primaryContactPersonCode, customerName)" //need a Customer type column and primaryContact
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
		ps.setString(1, customerCode);
		ps.setString(2, customerType);
		ps.setString(3, primaryContactPersonCode);
		ps.setString(4, name);
		ps.executeUpdate();

		rt.close();
		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

	/**
	 * 5. Removes all product records from the database
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void removeAllProducts(){
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String[] queries = new String[6];

		queries[0] = "SET foreign_key_checks = 0";
		queries[1] = "DELETE FROM Products"; 
		queries[2] = "ALTER TABLE Products AUTO_INCREMENT = 1";
		queries[3] = queries[2];

		for(int i = 0; i < 4; i++) {
			ps = (PreparedStatement) conn.prepareStatement(queries[i]);
			ps.executeUpdate();
		}

		ps.close();
		conn.close();
		db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit){
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;
		int addressID = 0;


		String query1 = "INSERT INTO Address (street,city,state,zip,country) "
				+"VALUES (?,?,?,?,?)";
		String query2 =  "SELECT LAST_INSERT_ID() FROM Address";
		String query3 = "INSERT INTO Products (productCode, movieDateTime, productName, movieScreenNo, cost ) "
				+"VALUES (?,?,?,?,?)";

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
		ps.setString(1, productCode);
		ps.setString(2, dateTime);
		ps.setString(3, movieName );
		ps.setString(4, screenNo); 
		ps.setDouble(5, pricePerUnit); 		
		ps.executeUpdate();

		rt.close();
		ps.close();
		conn.close();
		db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}
	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost)  {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;
		
		String query1 = "INSERT INTO Products (productCode, productName, startDate, endDate, cost) "
				+"VALUES (?,?,?,?,?)";
		

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, productCode);
		ps.setString(2, name);
		ps.setString(2, seasonStartDate);
		ps.setString(2, seasonEndDate);
		ps.setDouble(3, cost);
		ps.executeUpdate();

	

		ps.close();
		conn.close();
		db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}
	
	
	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addParkingPass(String productCode, double parkingFee){
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;
		
		String query1 = "INSERT INTO Products (productCode, cost) "
				+"VALUES (?,?)";
		

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, productCode);
		ps.setDouble(3, parkingFee);
		ps.executeUpdate();

	

		ps.close();
		conn.close();
		db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		
	}
	
	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String query1 = "INSERT INTO Products (productCode, productName,cost ) "
				+"VALUES (?,?,?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, productCode);
		ps.setString(2, name);
		ps.setDouble(3, cost);
		
		ps.executeUpdate();

		ps.close();
		conn.close();
		db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}
	
	/**
	 * 10. Removes all invoice records from the database
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void removeAllInvoices() {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String[] queries = new String[6];

		queries[0] = "SET foreign_key_checks = 0";
		queries[1] = "DELETE FROM Invoice"; 
		queries[2] = "ALTER TABLE Invoice AUTO_INCREMENT = 1";
		queries[3] = queries[2];

		for(int i = 0; i < 4; i++) {
			ps = (PreparedStatement) conn.prepareStatement(queries[i]);
			ps.executeUpdate();
		}

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate){
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;
		int invoiceID = 0;


		String query1 = "INSERT INTO Invoice (invoiceCode, date) "
				+"VALUES (?, ?)";
		String query2 =  "SELECT LAST_INSERT_ID() FROM Invoice";
		String query3 = "INSERT INTO Customer (customerCode)" //need a Customer type column and primaryContact
				+ " VALUES (?)";
		String query4 = "Insert INTO Person (personCode)" 
				+ "Values (?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, invoiceCode);
		ps.setString(2, invoiceDate);

		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query2);
		ResultSet rt = (ResultSet) ps.executeQuery();
		rt.next();
		invoiceID = rt.getInt("LAST_INSERT_ID()");

		ps = (PreparedStatement) conn.prepareStatement(query3);
		ps.setString(1, customerCode);

		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query4);
		ps.setString(1, salesPersonCode);

		rt.close();
		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}
	
	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String query1 = "INSERT INTO Invoice (invoiceCode) "
				+"VALUES (?)";
		String query2 = "INSERT INTO Products (productCode)" 
				+ " VALUES (?)";
		String query3 = "Insert INTO ProductsInvoice (units)" 
				+ "Values (?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, invoiceCode);
		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query2);
		ps.setString(2, productCode);


		ps = (PreparedStatement) conn.prepareStatement(query3);
		ps.setInt(1, quantity);

		ps.executeUpdate();

		

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}
	
	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String query1 = "INSERT INTO Invoice (invoiceCode) "
				+"VALUES (?)";
		String query2 = "INSERT INTO Products (productCode)" //need a Customer type column and primaryContact
				+ " VALUES (?)";
		String query3 = "Insert INTO ProductsInvoice (units)" 
				+ "Values (?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, invoiceCode);
		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query2);
		ps.setString(2, productCode);


		ps = (PreparedStatement) conn.prepareStatement(query3);
		ps.setInt(1, quantity);

		ps.executeUpdate();

		

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

	/**
	 * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 * NOTE: ticketCode may be null
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String query1 = "INSERT INTO Invoice (invoiceCode) "
				+"VALUES (?)";
		String query2 = "INSERT INTO Products (productCode)" 
				+ " VALUES (?)";
		String query3 = "Insert INTO ProductsInvoice (units)" 
				+ "Values (?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, invoiceCode);
		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query2);
		ps.setString(2, productCode);


		ps = (PreparedStatement) conn.prepareStatement(query3);
		ps.setInt(1, quantity);

		ps.executeUpdate();

		

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}

	/**
	 * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
		try{
		Database db = new Database();
		Connection conn = db.connectToDB();

		PreparedStatement ps = null;

		String query1 = "INSERT INTO Invoice (invoiceCode) "
				+"VALUES (?)";
		String query2 = "INSERT INTO Products (productCode)" 
				+ " VALUES (?)";
		String query3 = "Insert INTO ProductsInvoice (units)" 
				+ "Values (?)";

		ps = (PreparedStatement) conn.prepareStatement(query1);
		ps.setString(1, invoiceCode);
		ps.executeUpdate();

		ps = (PreparedStatement) conn.prepareStatement(query2);
		ps.setString(2, productCode);


		ps = (PreparedStatement) conn.prepareStatement(query3);
		ps.setInt(1, quantity);

		ps.executeUpdate();

		

		ps.close();
		conn.close();
		//db.close();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}
	
}