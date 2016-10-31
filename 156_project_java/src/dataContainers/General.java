package dataContainers;

import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class which creates a subclass for the Customer class, the General class receives
 * no tax break or discount, but is charged no fee.
 * @author Grant
 *
 */
@XmlRootElement
public class General extends Customer{
	
	public final double DISCOUNT = 0.00;
	public final double FEE = 0.00;
	public final double TAX = 1.00;
	
	public General(String customerCode, String primaryContact, String customerName, String customerAddress) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(customerCode, primaryContact, customerName, customerAddress);
	}
	
	public General() {
		
	}

	@Override
	public double getTax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFee() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return "General";
	}
	

}
