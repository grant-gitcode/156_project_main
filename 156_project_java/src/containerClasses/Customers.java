package containerClasses;

import dataContainers.Customer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Extends the Container class. This class is designed to hold an ArrayList of type Customer,
 * and to provide methods to access that ArrayList and its objects.
 * @author Grant
 *
 */
@XmlRootElement ( name = "customers")
@XmlSeeAlso({dataContainers.Customer.class, dataContainers.General.class, dataContainers.Student.class})
public class Customers extends Container{

	@SuppressWarnings("rawtypes")
	private ArrayList customers = new ArrayList<Customer>();
	
	@SuppressWarnings("rawtypes")
	public Customers(ArrayList customerList) {
		this.customers = customerList;
	}
	
	public Customers() {
	
	}
	
	/**
	 * Method for setting Customers object's ArrayList. Accepts another ArrayList as method
	 * parameter. Replaces current ArrayList with parameter ArrayList.
	 * @param customers
	 */
	@SuppressWarnings("rawtypes")
	public void setCustomers(ArrayList customers) {
		this.customers = customers;
	}
	
	/**
	 * Returns a given Customer object's stored ArrayList of Customer objects.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@XmlAnyElement(lax=true)
	public ArrayList getCustomers() {
		return this.customers;
	}
	
	
	/**
	 * Unlike setCustomers, addContained doesn't override the current ArrayList. Instead, it
	 * simply adds the objects from the parameter ArrayList to the current ArrayList.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addContained(ArrayList customers) {
			for(int r = 0; r < customers.size(); r++) {
				this.customers.add(customers.get(r));
		}
		
	}
	
	/**
	 * A more specific implementation of the searchContained method, this method searches through
	 * the Customers object's ArrayList to find a given Customer object based upon its customerCode.
	 * The method accepts a string which is a customer code to be searched for. If no code is found,
	 * the method returns null.
	 * 
	 */
	@Override
	public Customer searchContained(String customerCode) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for(int i = 0; i < this.customers.size(); i++) {
			if(this.customers.get(i).getClass().getMethod("getCustomerCode").invoke(this.customers.get(i),new Object[0]).toString().compareTo(customerCode) == 0) {
				return (Customer) this.customers.get(i);
			}
		}
		return null;
	}
	
}
