package dataContainers;

import java.lang.reflect.InvocationTargetException;
import javax.xml.bind.annotation.XmlRootElement;
import com.sun.xml.internal.txw2.annotation.XmlElement;

import containerClasses.Products;
import reports.DataConverter;
import reports.Invoice;

/**
 * Product is an abstract class used as a general parent to the variety of other
 * more specific product subclasses. Product stores information which is common to all
 * types of products like: productCode, units, attachedProduct, and the Invoice which a 
 * product is a part of. Although not all products utilize these attributes, they are 
 * included in this class because they make my code work.
 * @author Grant
 *
 */
@XmlRootElement
public abstract class Product extends Record {
 
	private String productCode;
	private int units;
	private Product attachedProduct;
	
	public Product(String productCode){
		this.productCode = productCode;
	}
	
	public Product(String productCode, String units) {
		this.productCode = productCode;
		this.units = Integer.parseInt(units);
	}
	
	public Product(String productCode, String units, String attachedProduct) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.productCode = productCode;
		this.units = Integer.parseInt(units);
		this.attachedProduct = ((Product) DataConverter.accessProductArrayList().searchContained(attachedProduct));
	}
	 
	public Product() {
		 
	}
	
	/**
	 * A copy constructor designed for a general Product.
	 * @param toClone
	 */
	public Product(Product toClone) {
		this.attachedProduct = toClone.attachedProduct;
		this.productCode = toClone.productCode;
		this.units = toClone.units;
	}
	
	public String getProductCode() {
		return this.productCode;
	}
	
	@XmlElement
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getUnits() {
		return this.units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public Product getAttachedProduct() {
		return this.attachedProduct;
		
	}

	/**
	 * Some objects, like parking passes, come with a product that they're attached to, like
	 * a movie ticket. This method takes a given product code, searches a Products object,
	 * and then finds a Product object to attach to this Product.
	 * @param attachedProduct
	 * @param products
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void setAttachedProduct(String attachedProduct, Products products) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Product x = (Product) products.searchContained(attachedProduct);
		this.attachedProduct = x;
	}
	
	public void setAttachedProduct(Product x) {
		this.attachedProduct = x;
	}
	
	public String toString(Invoice inv) {
		return this.getClass().toString();
	}
	
	public abstract Product copyProduct(Product x);
	
	public abstract double computeSubTotal(Invoice inv);
	
	public abstract double getTax(Invoice inv);
	
	public abstract void setDiscount();
	
	public abstract double getDiscount();
	
	/**
	 * The overridden equals() method for Product tests to see if objects are the same along the
	 * following criteria: <br><br>
	 * <b> ProductCode </b> Case-sensitive String comparison.<br>
	 * <b> Units </b> A test to see if integer values match. <br>
	 * <b> AttachedProduct </b> Calls this method to check if the attached products are the same. <br>
	 * <b> Invoice </b> Checks to see if both objects have the same Invoice object associated
	 * with them.
	 */
	@Override
	public boolean equals(Object obj) {
		
		
		return false;
		
	}
} 