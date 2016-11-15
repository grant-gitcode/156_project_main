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
	 * <b> AttachedProduct </b> First checks, to see if each Product has an attached product. If they both
	 * do not, no more methods are called. If one does and the other does not, they're clearly not the same
	 * Product object, and false is returned. If both have an attached Product, this method is called again
	 * on the attached product.
	 */
	@Override
	public boolean equals(Object obj) {
		Product prod = (Product) obj;
		
		if(!this.isEmpty() && !prod.isEmpty()) {
			if(this.productCode.equals(prod.getProductCode())) {
				if(this.units == prod.getUnits()) {
					
					//Case 1: Both products have an attached product -- check to see if products match.
					if(this.attachedProduct != null && prod.getAttachedProduct() != null) {
						if(this.attachedProduct.equals(prod.getAttachedProduct())) return true;
						else return false;
					}
					
					//Case 2: One product has an attached product and the other does not.
					if(this.attachedProduct == null || prod.attachedProduct == null) {
						if(!(this.attachedProduct == null && prod.attachedProduct == null)) {
							return false;
						}
					}
					
					//Case 3: Both products have no attached product.
					if(this.attachedProduct == null && prod.attachedProduct == null) {
						return true;
					}
					
				}
			}
		}
		return false;
		
	}
	
	/**
	 * A method to determine if a given Product object is empty, where empty
	 * is defined as a single field within the Product object being null, with
	 * the exception of the units field.
	 * @return
	 */
	public boolean isEmpty() {
		
		if(this.productCode != null) {
				if(this.attachedProduct != null) {
					return false;
				}
		}
		
		return true;
	}
	
	/**
	 * Overridden hashCode() method for the Product class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime*result + ((this.productCode == null) ? 0 : this.productCode.hashCode());
		result = prime*result + ((this.units == 0) ? 0 : this.units);
		result = prime*result + ((this.attachedProduct == null) ? 0 : this.attachedProduct.hashCode());
		
		return result;
	}
} 