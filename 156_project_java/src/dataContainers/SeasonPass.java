package dataContainers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import reports.Invoice;

/**
 * A subclass of Product, SeasonPass is used to model a season long parking pass at the movie
 * theater. It contains attributes: name (String), seasonStartDate (Date), seasonEndDate (Date),
 * and cost (double).
 * @author Grant
 *
 */
@XmlRootElement
@XmlType(propOrder = {"name","cost","seasonStartDate","seasonEndDate"})
public class SeasonPass extends Ticket {
	
	private String name;
	private Date seasonStartDate;
	private Date seasonEndDate;
	private double cost;
	
	public SeasonPass(String productCode, String name, String startDate, String endDate, String cost, Invoice inv) {
		super(productCode, inv);
		this.name = name;
		this.seasonStartDate = UtilityParser.stringToDate(startDate);
		this.seasonEndDate = UtilityParser.stringToDate(endDate);
		this.cost = Double.parseDouble(cost);
		
	}
	
	public SeasonPass(String productCode, String name, String startDate, String endDate, String cost) {
		super(productCode);
		this.name = name;
		this.seasonStartDate = UtilityParser.stringToDate(startDate);
		this.seasonEndDate = UtilityParser.stringToDate(endDate);
		this.cost = Double.parseDouble(cost);
		
	}
	
	public SeasonPass() {
		
	}
	
	/**
	 * A copy constructor designed to copy a given SeasonPass object and create a new one.
	 * @param toClone
	 */
	SeasonPass (SeasonPass toClone) {
		
		super(toClone);
		this.name = toClone.name;
		this.seasonStartDate = toClone.seasonStartDate;
		this.seasonEndDate = toClone.seasonEndDate;
		this.cost = toClone.cost;
		
	}

	@XmlElement
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getSeasonStartDate() {
		return UtilityParser.formatDateOutput(this.seasonStartDate);
	}
	
	public Date getSeasonStartDateDate() {
		return this.seasonStartDate;
	}
	
	public void setSeasonStartDate(String startDate) {
		this.seasonStartDate = UtilityParser.stringToDate(startDate);
	}

	@XmlElement
	public String getSeasonEndDate() {
		return UtilityParser.formatDateOutput(seasonEndDate);
	}
	
	public Date getSeasonEndDateDate() {
		return this.seasonEndDate;
	}
	
	public void setSeasonEndDate(String endDate) {
		this.seasonEndDate = UtilityParser.stringToDate(endDate);
	}
	@XmlElement
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * A method for computing the subtotal for SeasonPass objects.
	 */
	@Override
	public double computeSubTotal() {
		long d = 1;
		long q = 1;
		
		if(this.seasonStartDate.getTime() < this.getInv().getInvoiceDateDate().getTime()) {
			d =  (this.seasonEndDate.getTime() - this.getInv().getInvoiceDateDate().getTime())/1000/60/60/24;
			q = ((this.seasonEndDate.getTime() - this.seasonStartDate.getTime())/1000/3600/24);
		}

		return this.cost * (double) super.getUnits() *d/q + 8.0*super.getUnits();
	}

	@Override
	public double getTax() {
		return this.computeSubTotal()*super.getTax();
	}

	@Override
	public String toString() {

		return "Season Pass - " + this.name; 
	
	}
	
	/**
	 * Because there is no way to format System.out.printf() in such a way that one row has multiple lines,
	 * this method had to be created. This method returns a String representing the second half
	 * of the text seen on the individual invoice reports for a given SeasonPass object.
	 * @return
	 */
	public String toStringTwo() {
		
		NumberFormat formatter = new DecimalFormat("#0.00");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
				
		String x = "";
		
		if(this.getInv().getInvoiceDateDate().after(this.seasonStartDate)) {
			long d = (this.seasonEndDate.getTime() - this.getInv().getInvoiceDateDate().getTime())/1000/60/60/24;
			long q =  (this.seasonEndDate.getTime() - this.seasonStartDate.getTime())/1000/60/60/24;
			x = " prorated " + d+"/"+q+" days";
		}
		BigDecimal u = BigDecimal.valueOf(this.cost);
		return "(" + super.getUnits() + " units @ $" + formatter.format(u) + "/unit" + x + " + $8.00 fee/unit)";
		
	}

	@Override
	public Product copyProduct(Product x) {
		SeasonPass clone = new SeasonPass((SeasonPass) x);
		return clone;
	}

	@Override
	public void setDiscount() {
		
	}

	@Override
	public double getDiscount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
