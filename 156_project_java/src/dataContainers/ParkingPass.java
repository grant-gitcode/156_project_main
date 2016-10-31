package dataContainers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import reports.Invoice;

/**
 * ParkingPass is a subclass of Service, and contains the data members: cost, discount.
 * @author Grant
 *
 */
@XmlRootElement
public class ParkingPass extends Service {
	
	private double cost;
	private double discount = 0.0;
	
	public ParkingPass(String productCode, String parkingFee, Invoice inv) {
		super(productCode, inv);
		this.cost = Double.parseDouble(parkingFee);
	}
	
	public ParkingPass(String productCode, String parkingFee) {
		super(productCode);
		this.cost = Double.parseDouble(parkingFee);
	}
	
	public ParkingPass() {
		
	}
	
	public ParkingPass(ParkingPass toClone) {
		super(toClone);
		this.cost = toClone.cost;
		this.discount = toClone.discount;
		
	}

	@XmlElement
	public double getCost() {
		return cost;
	}

	public void setCost(double parkingFee) {
		this.cost = parkingFee;
	}
	
	@Override
	public double computeSubTotal() {
		double x = this.cost * super.getUnits();
		x -= this.discount;
		return x;
	}

	@Override
	public double getTax() {
		double x = this.computeSubTotal();
		return x*super.getTax();
	}

	/**
	 * An example of method overriding, this method replaces the normal output with output
	 * that is designed for the Individual Invoice Report generated in the InvoiceReport class.
	 */
	@Override
	public String toString() {
		NumberFormat formatter = new DecimalFormat("#0.00");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		BigDecimal u = BigDecimal.valueOf(this.cost);
		String productCodeString = "";
		if(this.getAttachedProduct() != null) productCodeString = this.getAttachedProduct().getProductCode() + " ";
		
		String str1 = "Parking Pass " + productCodeString + "(" + super.getUnits() + " units @ $"
				+ formatter.format(u);
		if(this.discount > 0.0) {
			if(super.getAttachedProduct().getUnits() <= this.getUnits()) {
				str1 = str1.concat(" with " + this.getAttachedProduct().getUnits() + " free)");
			}
			else {
				str1 = str1.concat(" with " + super.getUnits() + " free)");
			}
			
		}
		else {
			str1 = str1.concat(")");
		}
		return str1;
	}

	@Override
	public Product copyProduct(Product x) {
		ParkingPass clone = new ParkingPass((ParkingPass) x);
		return clone;
	}

	/**
	 * Sets the discount of this ParkingPass object based on how many attached products
	 * there are to this unit and on how many units this object has.
	 */
	@Override
	public void setDiscount() {
		
		if(super.getAttachedProduct() != null) {
			if(super.getAttachedProduct().getUnits() <= this.getUnits()) {
				this.discount = this.cost*super.getAttachedProduct().getUnits();
			}
			else {
				this.discount = this.cost*this.getUnits();
			}
		}
	}

	@Override
	public double getDiscount() {
		return this.discount;
	}

	
	
}
