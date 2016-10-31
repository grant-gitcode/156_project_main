package dataContainers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import reports.Invoice;

/**
 * Refreshment is a class which models a typical refreshment found in a movie theatre. It 
 * can store information on the name, cost and discount of the particular refreshment object.
 * @author Grant
 *
 */
@XmlRootElement
@XmlType(propOrder = {"name","cost"})
public class Refreshment extends Service{

	private String name;
	private double cost;
	private double discount = 0.0;
	
	public Refreshment(String productCode, String name, String cost, Invoice inv) {
		super(productCode, inv);
		this.name = name;
		this.cost = Double.parseDouble(cost);
		}
	
	public Refreshment(String productCode, String name, String cost) {
		super(productCode);
		this.name = name;
		this.cost = Double.parseDouble(cost);
		}
	
	public Refreshment() {
		
	}
	
	/**
	 * A copy constructor used when creating objects specific to Invoice.
	 * @param toClone
	 */
	public Refreshment(Refreshment toClone) {
		super(toClone);
		this.cost = toClone.cost;
		this.name = toClone.name;
		this.discount = toClone.discount;
	}

	@XmlElement
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public double computeSubTotal() {
		double x = this.cost*super.getUnits();
		return x - x*this.discount;
	}

	@Override
	public double getTax() {
		double x = this.computeSubTotal();
		return x*super.getTax();
	}

	/**
	 * A fancy toString() method used to print out the Invoice Reports.
	 */
	@Override
	public String toString() {
		
		NumberFormat formatter = new DecimalFormat("#0.00");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		BigDecimal u = BigDecimal.valueOf(this.cost); //Need this because doubles are stupid.
		String str1 = this.name + " (" + this.getUnits() + " units @ $" + 
		formatter.format(u) + "/unit";
		if(this.discount > 0.0) str1 = str1.concat(" with " +  this.discount*100 + "% off)");
		else {
			str1 = str1.concat(")");
		}
		return str1;
	}

	/**
	 * A copy method which is used when creating objects specific to invoices.
	 */
	@Override
	public Product copyProduct(Product x) {
		Refreshment clone = new Refreshment((Refreshment) x);
		return clone;
	}

	@Override
	public void setDiscount() {
		this.discount = 0.05;
		
	}

	@Override
	public double getDiscount() {
		return this.discount;
	}

}
