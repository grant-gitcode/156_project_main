package dataContainers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	
	private Database db;
	
	public ObjectFactory(Database db) {
		this.db = db;
	}
	
	/**
	 * The purpose of this method is to be able to create Address objects quickly and
	 * easily.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Address createAddress(int id) throws SQLException, ClassNotFoundException {
		
		ResultSet rt;
		
		Address addr = new Address();
		rt = db.getRowFromTable("Address", "addressID", id);
		rt.next();
		addr.setStreet(rt.getString("street"));
		addr.setCity(rt.getString("city"));
		addr.setState(rt.getString("state"));
		addr.setZip(rt.getString("zip"));
		addr.setCountry(rt.getString("country"));
		
		rt.close();
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
		ResultSet rt = db.getRowFromTable("Customer", "customerID", id);
		rt.next();
		String type = rt.getString("subclass");
		
		switch(type) {
		case("G"):
			cust = new General();
			break;
		case("S"):
			cust = new Student();
			break;	
		case("General"):
			cust = new General();
			break;
		case("Student"):
			cust = new Student();
			break;	
		}
		
		cust.setCustomerCode(rt.getString("customerCode"));
		cust.setCustomerName(rt.getString("customerName"));
		cust.setPrimaryContact(createPerson(rt.getInt("personID")));
		cust.setCustomerAddress(createAddress(rt.getInt("addressID")));
		
		rt.close();
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
		
		ResultSet rt;
		Email e = new Email();
		ArrayList<String> emailList = new ArrayList<String>();
		rt = db.getRowFromTable("Email","personID", i);
		
		while(rt.next()) {
			emailList.add(rt.getString("emailAddress"));
		}
		
		e.setEmail(emailList);
		
		rt.close();
		return e;
	}
	
	/**
	 * This method takes a row of the Invoice table and uses that data to create an
	 * Invoice object.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Invoice createInvoice(int i) throws ClassNotFoundException, SQLException {
		ResultSet rt;
		Invoice inv = new Invoice();
		rt = db.getRowFromTable("Invoice", "invoiceID", i);
		
		rt.next();
		inv.setInvoiceCode(rt.getString("invoiceCode"));
		inv.setCustomer(createCustomer(rt.getInt("customerID")));
		inv.setInvoiceDate(rt.getDate("date"));
		inv.setSalesperson(createPerson(rt.getInt("personID")));
		inv.setProductList(createProducts(i));
		inv.getProductList().setDiscounted();
		
		rt.close();
		return inv;
	}
	
	/**
	 * This method shall create a person object given some position in the database,
	 * given by an integer, based off the personID column.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Person createPerson(int id) throws SQLException, ClassNotFoundException {
		
		ResultSet rt;
		Person pers = new Person();
		rt = db.getRowFromTable("Person", "personID", id);
		
		rt.next();
		pers.setPersonCode(rt.getString("personCode"));
		pers.setFirstName(rt.getString("firstName"));
		pers.setLastName(rt.getString("lastName"));
		pers.setEmails(createEmail(id));
		
		int addressID = rt.getInt("addressID");
		pers.setAddress(createAddress(addressID));	
		
		rt.close();
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
	public Product createProduct(int prodInvID) throws SQLException, ClassNotFoundException {
		ResultSet rt = db.getInvoiceProduct(prodInvID);
		rt.next();
		ResultSet rt2 = db.getAddress(rt.getInt("addressID"));
		rt2.next();
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
				Date x = rt.getDate("movieDateTime");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				m.setDiscounted(UtilityParser.isDiscounted(format.format(x)));
				p = m;
				break;
			case "S":
				SeasonPass s = new SeasonPass();
				s.setName(rt.getString("productName"));
				s.setSeasonStartDate(rt.getDate("startDate"));
				s.setSeasonEndDate(rt.getDate("endDate"));
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
		if(rt.getInt("attachedProductID") != 0 && rt.getInt("invoiceID") != 0) {
			ResultSet v = db.getIPFromTwoIDs
					(rt.getInt("attachedProductID"),rt.getInt("invoiceID"));
			p.setAttachedProduct(this.createAttachedProduct(v));
		}
		
		rt.close();
		return p;
		
	}
	
	public Product createAttachedProduct(ResultSet rt) throws SQLException {
		rt.next();
		ResultSet rt2 = db.getAddress(rt.getInt("addressID"));
		String type;
		Product p = null;
		rt2.next();
		type = rt.getString("subType").toUpperCase().trim();
		
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
				m.setProductCode(rt.getString("productCode"));
				m.setUnits(rt.getInt("units"));
				p = m;
				break;
			case "S":
				SeasonPass s = new SeasonPass();
				s.setName(rt.getString("productName"));
				s.setCost(rt.getDouble("cost"));
				s.setProductCode(rt.getString("productCode"));
				s.setUnits(rt.getInt("units"));
				p = s;
				break;
		}
		
		rt.close();
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
		ResultSet rt = db.getRowFromTable("ProductsInvoice", "invoiceID", id);
		
		while(rt.next()) {
			int productsInvoiceID = rt.getInt("productsInvoiceID");
			productList.add(createProduct(productsInvoiceID));
		}
		
		products.setProducts(productList);
		
		rt.close();
		return products;
	}
	
	/**
	 * The .close() method automatically closes the Database object which is created when instantiating
	 * the class. This conserves system resources and minimizes security risks. This method should always be closed when
	 * and ObjectFactory object is no longer needed.
	 * @throws SQLException 
	 */
	public void close() throws SQLException {
		db.close();
	}

}
