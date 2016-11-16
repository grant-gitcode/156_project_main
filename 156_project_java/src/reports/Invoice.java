package reports;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import dataContainers.*;
import containerClasses.*;

/**@author Grant Harrison
 * @version 1.0 <br><br>
 * The invoice class is designed to accept a set of strings, parse them into the appropriate classes,
 * and then serve as a container class for these other classes. The invoice class also provides functionality
 * for generating invoice reports; it has getter methods which are specifically designed to return strings
 * specially formatted for use in generating reports.
 */
public class Invoice {
	
	private String invoiceCode;
	private Customer customer;
	private Date invoiceDate;
	private Person salesperson;
	private Products productList;
	
	public Invoice() {
		
	}
	
	/**
	 * 
	 * @param invoiceCode
	 * @param customer
	 * @param salesperson
	 * @param date
	 * @param product
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *<br><br>
	 * The non-empty constructor for the Invoice class. Accepts a series of 5 strings which are then used
	 * in specially designed constructors of the Customer, Person, and Product classes to generate
	 * the appropriate objects to be contained within the Invoice object.
	 */
	public Invoice(String invoiceCode, String customer, String salesperson, String date, String product) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.invoiceCode = invoiceCode;
		this.customer = DataConverter.accessCustomerArrayList().searchContained(customer);
		this.invoiceDate = UtilityParser.stringToDate(date);
		this.salesperson = DataConverter.accessPersonArrayList().searchContained(salesperson);
		this.productList = new Products(product, this);
		this.productList.setDiscounted();
		
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}

	public String getInvoiceDate() {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY");
		return format.format(this.invoiceDate);
	}
	
	public Date getInvoiceDateDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = UtilityParser.stringToDate(invoiceDate);
	}

	public String getSalesperson() {
		return this.salesperson.getLastName() + ", " + this.salesperson.getFirstName();
	}
	
	public Person getSalespersonPerson() {
		return this.salesperson;
	}

	public void setSalesperson(Person salesperson) {
		this.salesperson = salesperson;
	}

	public Products getProductList() {
		return this.productList;
	}

	public void setProductList(Products productList) {
		this.productList = productList;
	}
	
	public String getProductCode(int position) {
		return this.productList.getProducts().get(position).getProductCode();
	}
	
	public String getProductDescription(int position) {
		return this.productList.getProducts().get(position).toString();
	}
	
	/**
	 * Gets the given subtotal for some object at position i in the Invoice's Products ArrayList.
	 * @param i
	 * @return String
	 */
	public String getProductSubtotal(int i) {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		BigDecimal u = BigDecimal.valueOf(this.productList.getProducts().get(i).computeSubTotal(this));
		
		return format.format(u);
		
	}
	
	/**
	 * Gets the tax for some object at position i of the Invoice's Products ArrayList.
	 * @param i
	 * @return String
	 */
	public String getProductTax(int i) {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		BigDecimal u = BigDecimal.valueOf(this.getProductList().getProducts().get(i).getTax(this));

		return format.format(u);
	}
	
	/**
	 * Calls the getProductTax() and getProductSubtotal() methods to compute the Total.
	 * @param i
	 * @return String
	 */
	public String getProductTotal(int i) {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		
		double x = Double.parseDouble(this.getProductSubtotal(i));
		double y = Double.parseDouble(this.getProductTax(i));
		y += x;
		
		BigDecimal u = BigDecimal.valueOf(y);
		return format.format(u);
	}
	
	/**Calls the getProductSubtotal() method for all products and returns their sum.
	 * @return String
	 */
	public String sumSubTotals() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i =0; i < this.productList.getProducts().size(); i++){
			x += this.productList.getProducts().get(i).computeSubTotal(this);
		}
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
	
	/** Calls all products' getProductTax() methods and returns their sum.
	 * 
	 * @return String
	 */
	public String sumTax() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i =0; i < this.productList.getProducts().size(); i++){
			x += this.productList.getProducts().get(i).getTax(this);
		}
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
	
	/** Calls each product's getProductTotal() method and returns their sum.s
	 * 
	 * @return String
	 */
	public String sumTotals() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = Double.parseDouble(this.sumSubTotals());
		x += Double.parseDouble(this.sumTax());
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
	
	/**Applies a student discount to the subtotal. 
	 * 
	 * @return String
	 */
	public String getStudentDiscount() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		
		double x = Double.parseDouble(this.sumSubTotals());
		double y = -x*0.08 - Double.parseDouble(this.sumTax());
		BigDecimal u = BigDecimal.valueOf(y);
		return format.format(u);
	}
	
	/**
	 * Gets the standard student fee of $6.75.
	 * @return
	 */
	public String getStudentFee() {
		return "6.75";
	}
	
	/**
	 * The "total total" is really just the sum of the sumTotals(), getStudentDiscount() and
	 * getStudentFee() methods. The latter two methods are only called if the Customer in
	 * question is of Student.class type.
	 * @return
	 */
	public String getTotalTotal() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		
		double x = Double.parseDouble(this.sumTotals());
		
		if(this.customer.getClass().equals(Student.class)) {
			x += Double.parseDouble(this.getStudentDiscount());
			x += Double.parseDouble(this.getStudentFee());
		}
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
		
	}
	
	/**
	 * Returns all MovieTicket objects within this Invoice's Products ArrayList in an 
	 * ArrayList.
	 * @return ArrayList<MovieTicket>
	 */
	public ArrayList<MovieTicket> getMovieTickets() {
		
		ArrayList<MovieTicket> tickets = new ArrayList<MovieTicket>();
		
		for(int i = 0; i < this.productList.getProducts().size(); i++) {
			if(this.productList.getProducts().get(i).getClass().equals(MovieTicket.class)) {
				tickets.add((MovieTicket) this.productList.getProducts().get(i));
			}
		}
		
		return tickets;
	}
	
	/**Returns an ArrayList of all the SeasonPass objects in this Invoice's Products ArrayList.
	 * 
	 * @return ArrayList<SeasonPass>
	 */
	public ArrayList<SeasonPass> getSeasonPasses() {
		
		ArrayList<SeasonPass> passes = new ArrayList<SeasonPass>();
		
		for(int i = 0; i < this.productList.getProducts().size(); i++) {
			if(this.productList.getProducts().get(i).getClass().equals(SeasonPass.class)) {
				passes.add((SeasonPass) this.productList.getProducts().get(i));
			}
		}
		
		return passes;
	}
	
	/**The equals() method for the Invoice class checks if two Invoice objects are equal along the
	 * following criteria: <br><br>
	 * <b> InvoiceCode </b> Compares two Strings, is case-sensitive. <br>
	 * <b> Customer </b> Uses custom defined equals() method for two Customer objects. <br>
	 * <b> InvoiceDate </b> Compares two Date objects. <br>
	 * <b> Salesperson </b> Compares two person objects using the overridden equals() method. <br>
	 * <b> ProductList </b> Compares the two ProductList objects within the Invoice. Uses the
	 * custom ProductList equals() method. <br><br>
	 * Both Invoice objects must be non-empty, where non-empty means all fields in both objects are
	 * non-null, else the method will return false.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		Invoice inv = (Invoice) obj;
		
		if(!this.isEmpty() && !inv.isEmpty()) {
			if(this.invoiceCode.equals(inv.getInvoiceCode())) {
				if(this.customer.equals(inv.getCustomer())) {
					if(this.invoiceDate.equals(inv.getInvoiceDateDate())) {
						if(this.salesperson.equals(inv.getSalespersonPerson())) {
							if(this.productList.equals(inv.getProductList())) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * A method to determine if a given Invoice object is empty, where
	 * empty is defined as an Invoice object containing at least one
	 * null field.
	 * @return
	 */
	public boolean isEmpty() {
		
		if(this.invoiceCode != null) {
			if(this.customer != null) {
				if(this.salesperson != null) {
					if(this.productList != null) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Overridden hashCode() method for the Invoice class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime*result + ((this.invoiceCode == null) ? 0 : this.invoiceCode.hashCode());
		result = prime*result + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = prime*result + ((this.invoiceDate == null) ? 0 : this.invoiceDate.hashCode());
		result = prime*result + ((this.salesperson == null) ? 0 : this.salesperson.hashCode());
		result = prime*result + ((this.productList == null) ? 0 : this.productList.hashCode());
		
		return result;
	}
	
}
