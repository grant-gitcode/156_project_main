package containerClasses;

import dataContainers.ParkingPass;
import dataContainers.Product;
import dataContainers.Refreshment;
import dataContainers.Ticket;
import reports.DataConverter;
import reports.Invoice;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Subclass of Container class, is the specific class for holding an ArrayList of Product
 * objects and for searching that ArrayList.
 * @author Grant
 *
 */
@XmlRootElement ( name = "Products")
@XmlSeeAlso({dataContainers.MovieTicket.class,dataContainers.SeasonPass.class,dataContainers.Refreshment.class,dataContainers.ParkingPass.class})
public class Products extends Container{

	private ArrayList<Product> products = new ArrayList<Product>();
	
	public Products(ArrayList<Product> productList) {
		this.products = productList;
	}
	
	/**
	 * A very elaborate constructor designed to parse a given string from an invoice flat file
	 * and convert it into a series of Product objects held by a Products object, with each
	 * object attached to the specific Invoice they were created in.
	 * @param rawProductList
	 * @param inv
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Products(String rawProductList, Invoice inv) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!rawProductList.isEmpty()) {
			//Creation of a scanner to parse the string by comma, creating unique strings for the 
			//creation of each unique product.
			Scanner scan = new Scanner(rawProductList.substring(1));
			scan.useDelimiter(",");
			//delimitedProducts is an ArrayList of product strings used for constructors
			ArrayList<String> delimitedProducts = new ArrayList<String>();
	
			//Creates a new String in delimitedProducts ArrayList based off each product string
			//in a given line of the original Invoice flat file.
			while(scan.hasNext()) {
				delimitedProducts.add(scan.next());
			}
			
			//A for loop cycling through each of the product strings.
			for(int t = 0; t < delimitedProducts.size(); t++) {
				
				//Each product string has at least 2 subdivisions; at most 3. To parse these
				//subdivisions, we use the : as a delimiter.
				Scanner scan2 = new Scanner(delimitedProducts.get(t));
				scan2.useDelimiter(":");
				
				/**So the first part of each individual product string is the product code. We
				 * parse this and then create an object from it by searching our list of products.
				 * Next, we create a copy of this object which is the specific object for this 
				 * given Invoice (rather than the general objects we got from the non-Invoice flat
				 * files which serve as our templates.
				 */
				String product = scan2.next();
				Product newProduct = DataConverter.accessProductArrayList().searchContained(product);
				Product newInvoiceProduct = newProduct.copyProduct(newProduct);
				
				/**
				 * Looks at the second part of the individual product string (the part after the 
				 * colon) and uses it to set the units field of the newly created Product object.
				 * If there is a third sub-division, this will always be some attached product,
				 * so we call the setAttachedProduct() method using this last sub-string and 
				 * pass a reference to this object we're creating.
				 */
				newInvoiceProduct.setUnits(Integer.parseInt(scan2.next()));
				if(scan2.hasNext()) newInvoiceProduct.setAttachedProduct(scan2.next(), this);
				
				//Adds the newly created product to this Products object.
				this.products.add(newInvoiceProduct);
				scan2.close();
			}
			
			scan.close();
		}
	}
	
	public Products() {
	
	}
	
	/**
	 * Sets the ArrayList of Product objects; overwrites the field with whatever ArrayList
	 * parameter field is passed.
	 * @param products
	 */
	@XmlAnyElement(lax=true)
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	
	/**
	 * Returns the ArrayList contained within this Products object (which contains Product
	 * objects).
	 * @return
	 */
	public ArrayList<Product> getProducts() {
		return this.products;
	}
	
	/**
	 * Adds a passed ArrayList's objects to the current ArrayList held by the Products object.
	 */
	@SuppressWarnings({ "rawtypes" })
	public void addContained(ArrayList products) {
		for(int r = 0; r < products.size(); r++) {
			this.products.add((Product) products.get(r));
		}
	}
	
	/**
	 * Searches the Products object ArrayList for a specific Product object based upon its 
	 * productCode. If no match is found, null is returned.
	 */
	public Product searchContained(String productCode) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for(int i = 0; i < this.products.size(); i++) {
			if(this.products.get(i).getClass().getMethod("getProductCode").invoke(this.products.get(i),new Object[0]).toString().compareTo(productCode) == 0) {
				return (Product) this.products.get(i);
			}
		}
		return null;
	}
	
	/**
	 * A method which can be called on a given Products object. Checks to see if there is some
	 * object which has the superclass Ticket, and, if so, performs the setDiscount() method
	 * specific to each of the ParkingPass and Refreshment classes. 
	 */
	public void setDiscounted() {
		
		boolean isTicket = false;
		
		for (int j = 0; j < this.products.size(); j++) {
			if(this.products.get(j).getClass().getSuperclass().equals(Ticket.class)) {
				isTicket = true;
			}
		}
		
		
		for(int i = 0; i < this.products.size(); i++) {
			
			if(this.products.get(i).getClass().equals(ParkingPass.class) && isTicket) {
				this.products.get(i).setDiscount();
			}
			
			if(this.products.get(i).getClass().equals(Refreshment.class) && isTicket) {
				this.products.get(i).setDiscount();
			}
			
		}
	}
	
	/**
	 * Overriding the equals method for the Products class, the method checks the following criteria for 
	 * equality: <br><br>
	 * <b> Products </b> Checks an ArrayList of Product objects one by one. Objects must be in the same order,
	 * must be the same Product objects, and the ArrayList must be of the same length.
	 */
	@Override
	public boolean equals(Object obj) {
		
		Products list = (Products) obj;
		
		if(!this.isEmpty() && !list.isEmpty()) {
			if(this.products.size() == list.products.size()) {
				for(int i = 0; i < this.products.size(); i++) {
					if(!this.products.get(i).equals(list.products.get(i))) return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		
		if(this.products != null) return false;
		
		return true;
	}
	
	/**
	 * Overridden method for hashCode() for the Products class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime*result + ((this.products == null) ? 0 : this.products.hashCode());
		
		return result;
	}
}
