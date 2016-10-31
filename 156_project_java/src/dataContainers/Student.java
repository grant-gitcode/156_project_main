package dataContainers;

import java.lang.reflect.InvocationTargetException;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A type of Customer, the Student class is unique in that it pays no taxes, is charged a
 * constant fee, and is given a discount on orders. 
 * @author Grant
 *
 */
@XmlRootElement
public class Student extends Customer {
	
	public final double DISCOUNT = 0.08;
	public final double FEE = 6.75;
	public final double TAXES = 0.00;

	public Student(String customerCode, String primaryContact, String customerName, String customerAddress) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(customerCode,primaryContact,customerName,customerAddress);
	}
	
	public Student() {
		
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
		return "Student";
	}
}
