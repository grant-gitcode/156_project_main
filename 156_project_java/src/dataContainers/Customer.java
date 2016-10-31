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
	
	public abstract double getTax();
	
	public abstract double getFee();
	
	public abstract double getDiscount();
	
}
