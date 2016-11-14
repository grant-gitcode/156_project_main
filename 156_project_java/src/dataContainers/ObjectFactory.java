package dataContainers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import containerClasses.Products;
import database.Database;
import reports.Invoice;

/**
 * The ObjectFactory class provides an easy way to instantiate one object while having the ability to create objects of
 * multiple classes. All classes which have superclass Record and which are non-abstract are able to be created from the
 * methods of this class. When an ObjectFactory class object is created, ResultSet and Database objects are also created
 * which must later be closed using the .close() method in the interest of security and efficiency.
 * @author Grant
 *
 */
public class ObjectFactory {
	
	private Database db = new Database();
	private ResultSet rt;
	
	/**
	 * The purpose of this method is to be able to create Address objects quickly and
	 * easily.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Address createAddress(int id) throws SQLException, ClassNotFoundException {
		
		Address addr = new Address();
		rt = db.getRowsFromTable("Address", "addressID", id);
		rt.next();
		addr.setStreet(rt.getString("street"));
		addr.setCity(rt.getString("city"));
		addr.setState(rt.getString("state"));
		addr.setZip(rt.getString("zip"));
		addr.setCountry(rt.getString("country"));
		
		return addr;
	}
	
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
		rt = db.getRowsFromTable("Customer", "customerID", id);
		
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
		cust.setPrimaryContact(createPerson(rt.getInt("personID")));
		cust.setCustomerAddress(createAddress(rt.getInt("addressID")));
		
		return cust;
	}
	
	/**
	 * The purpose of this method is to create an Email object based upon a given
	 * row or rows in the Email table in the database which corresponds to a given 
	 * Person object. Finds all emails associated with a PersonID and creates a sole
	 * Email object with multiple email strings in it.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Email createEmail(int i) throws SQLException, ClassNotFoundException {
		
		Email e = new Email();
		ArrayList<String> emailList = new ArrayList<String>();
		rt = db.getRowsFromTable("Email","personID", i);
		
		while(rt.next()) {
			emailList.add(rt.getString("emailAddress"));
		}
		
		e.setEmail(emailList);
		
		return e;
	}
	
	/**
	 * This method creates the Invoice located at a specific row in the Invoice table.
	 * Accepts a customerID to create potentially multiple Invoice objects, returned
	 * as an ArrayList.
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ArrayList<Invoice> createInvoiceList(int i) throws SQLException, ClassNotFoundException {
		
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		rt = db.getRowsFromTable("Invoice","customerID", i);
		
		while(rt.next()) {
			Invoice inv = new Invoice();
			inv.setInvoiceCode(rt.getString("invoiceCode"));
			inv.setInvoiceDate(rt.getDate("date"));	
			inv.setProductList(createProducts(i));
		}
		
		return invoices;
	}
	
	/**
	 * This method shall create a person object given some position in the database,
	 * given by an integer, based off the personID column.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Person createPerson(int id) throws SQLException, ClassNotFoundException {
		
		Person pers = new Person();
		rt = db.getRowsFromTable("Person", "personID", id);
		
		rt.next();
		pers.setPersonCode(rt.getString("personCode"));
		pers.setFirstName(rt.getString("firstName"));
		pers.setLastName(rt.getString("lastName"));
		pers.setEmails(createEmail(id));
		
		int addressID = rt.getInt("addressID");
		pers.setAddress(createAddress(addressID));	
		
		return pers;
		
	}
	
	/**
	 * Given a ResultSet object which contains the join of Products and ProductInvoice,
	 * and a row at which to set the database cursor, this method will create 
	 * the object in question. It also sets the attachedProduct and the Invoice of
	 * said object.
	 * @param rt
	 * @param i
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public Product createProduct(int i, int invoiceID) throws SQLException, ClassNotFoundException {
		
		rt = db.getInvoiceProduct(i);
		ResultSet rt2 = db.getAddress(rt.getInt("addressID"));
		String type;
		Product p = null;
		
		type = rt.getString("subType").toUpperCase();
		
		/*This provides a case for all four Product sub classes. The nature of the
		 * switch statement allows for this code to be easily extensible, as the 
		 * only adjustment needed to be made for new Product subclasses would be 
		 * the addition of a case.
		 */
		switch(type) {
			case "M": 
				MovieTicket m = new MovieTicket();
				m.setMovieName(rt.getString("productName"));
				m.setCost(rt.getDouble("cost"));
				m.setMovieDateTime(rt.getString("movieDateTime"));
				m.setScreenNo(rt.getString("movieScreenNo"));
				Address address = new Address(
						rt2.getString("street"),
						rt2.getString("city"),
						rt2.getString("state"),
						rt2.getString("zip"),
						rt2.getString("country")
						);
				m.setAddress(address);
				p = m;
				break;
			case "S":
				SeasonPass s = new SeasonPass();
				s.setName(rt.getString("productName"));
				s.setCost(rt.getDouble("cost"));
				p = s;
				break;
			case "P":
				ParkingPass pp = new ParkingPass();
				pp.setCost(rt.getDouble("cost"));
				p = pp;
				break;
			case "R":
				Refreshment r = new Refreshment();
				r.setName(rt.getString("productName"));
				r.setCost(rt.getDouble("cost"));
				p = r;
				break;
		}
		
		p.setProductCode(rt.getString("productCode"));
		p.setUnits(rt.getInt("units"));
		
		
		/*The logic of this is simple: If the Product has an attachedProductID,
		 *then a method is called which searches for the appropriate object in
		 *the Products table; and returning this, it sets the attachedProduct
		 *to this object. 
		 */
		if(rt.getInt("attachedProductID") != 0) {
			ResultSet pd = db.getRowsFromTable
					("Products", "ProductsID",rt.getInt("attachedProductID"));
			p.setAttachedProduct(this.createAttachedProduct(pd, 1,invoiceID));
		}
		
		return p;
		
	}
	
	public Product createAttachedProduct(ResultSet rt, int i, int id) throws SQLException {
		
		ResultSet rt2 = db.getAddress(rt.getInt("addressID"));
		String type;
		Product p = null;
		
		rt.next();
		type = rt.getString("subType").toUpperCase();
		
		/*This provides a case for all attached classes. The nature of the
		 * switch statement allows for this code to be easily extensible, as the 
		 * only adjustment needed to be made for new Product subclasses would be 
		 * the addition of a case.
		 */
		switch(type) {
			case "M": 
				MovieTicket m = new MovieTicket();
				m.setMovieName(rt.getString("productName"));
				m.setCost(rt.getDouble("cost"));
				m.setMovieDateTime(rt.getString("movieDateTime"));
				m.setScreenNo(rt.getString("movieScreenNo"));
				Address address = new Address(
						rt2.getString("street"),
						rt2.getString("city"),
						rt2.getString("state"),
						rt2.getString("zip"),
						rt2.getString("country")
						);
				m.setAddress(address);
				p = m;
				break;
			case "S":
				SeasonPass s = new SeasonPass();
				s.setName(rt.getString("productName"));
				s.setCost(rt.getDouble("cost"));
				p = s;
				break;
		}
		
		p.setProductCode(rt.getString("productCode"));
		p.setUnits(rt.getInt("units"));

		return p;
	}
	
	/**
	 * We pass the invoiceID to this method to get the appropriate list of Product 
	 * objects to create.
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Products createProducts(int id) throws SQLException, ClassNotFoundException {
		
		Products products = new Products();
		ArrayList<Product> productList = new ArrayList<Product>();
		rt = db.getRowsFromTable("ProductsInvoice", "invoiceID", id);
		
		while(rt.next()) {
			int productsInvoiceID = rt.getInt("productsInvoiceID");
			productList.add(createProduct(productsInvoiceID, id));
		}
		
		products.setProducts(productList);
		
		return products;
	}
	
	/**
	 * The .close() method automatically closes the ResultSet and Database objects which are created when instantiating
	 * the class. This conserves system resources and minimizes security risks. This method should always be closed when
	 * and ObjectFactory object is no longer needed.
	 * @throws SQLException 
	 */
	public void close() throws SQLException {
		rt.close();
		db.close();
	}

}
