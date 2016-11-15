package dataContainers;

import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import reports.DataConverter;

/**
 * A class to reflect the customers who do business with this theater. This class contains
 * several data members like: customerCode, primaryContact, customerName, customerAddress.
 * This is an abstract class.
 * @author Grant
 *
 */
@XmlRootElement( name = "customer")
@XmlType(propOrder = {"customerCode","primaryContact","customerName","customerAddress"})
@XmlSeeAlso({dataContainers.Address.class,dataContainers.Person.class,containerClasses.Persons.class})
public abstract class Customer extends Record {

	private String customerCode;
	private Person primaryContact;
	private String customerName;
	private Address customerAddress;
	
	public Customer(String customerCode, String primaryContact, String customerName, String customerAddress) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.customerCode = customerCode;
		this.primaryContact = DataConverter.accessPersonArrayList().searchContained(primaryContact);
		this.customerName = customerName;
		this.customerAddress = new Address(customerAddress);
	}
	
	public Customer() {
		
	}

	@XmlElement
	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@XmlElement
	public Person getPrimaryContact() {
		return this.primaryContact;
	}

	public void setPrimaryContact(String primaryContact) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.primaryContact = DataConverter.accessPersonArrayList().searchContained(primaryContact);
	}
	
	public void setPrimaryContact(Person x) {
		this.primaryContact = x;
	}

	@XmlElement
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@XmlElement
	public Address getCustomerAddress() {
		return this.customerAddress;
	}
	
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = new Address(customerAddress);
	}
	
	public void setCustomerAddress(Address x) {
		this.customerAddress = x;
	}
	
	public abstract double getTax();
	
	public abstract double getFee();
	
	public abstract double getDiscount();
	
	/**
	 * The equals() method for the Customer class tests for equality between two Customer objects
	 * along the following criteria: <br><br>
	 * <b> CustomerCode </b> This is a case sensitive String comparison.<br>
	 * <b> PrimaryContact </b> This is a comparison of two Person objects using the overridden
	 * equals() method for the Person class. <br>
	 * <b> CustomerName </b> This is a case-insensitive comparison of the names of two Customer
	 * objects. <br>
	 * <b> CustomerAddress </b> This is a comparison of the two Address objects within each Customer
	 * object using the overridden equals() method defined in the Address class. <br><br>
	 * One caveat of this method is that it does not take into account whether any given pair of
	 * Customer objects are of type Student or General. For that level of comparison, use the
	 * Student equals() method or the General equals() method.
	 */
	@Override
	public boolean equals(Object obj) {
		Customer cust = (Customer) obj;
		
		if(this.customerCode.equals(cust.getCustomerCode())) {
			if(this.primaryContact.equals(cust.getPrimaryContact())) {
				if(this.customerName.toUpperCase().equals(cust.getCustomerName().toUpperCase())) {
					if(this.customerAddress.equals(cust.getCustomerAddress())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
