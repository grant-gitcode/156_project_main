package dataContainers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import reports.Invoice;

/**
 * A class modeling a movie ticket sold at this movie theater, contains the following
 * data members: movieDateTime, movieName, screenNo, pricePerUnit, cost, address, discounted.
 * @author Grant
 *
 */
@XmlRootElement
@XmlSeeAlso(dataContainers.Address.class)
@XmlType(propOrder = {"movieDateTime","movieName","cost","address","screenNo","pricePerUnit","discounted"})
public class MovieTicket extends Ticket {
	
	private String movieDateTime;
	private String movieName;
	private String screenNo;
	private double pricePerUnit;
	private double cost;
	private Address address;
	private boolean discounted;

	public MovieTicket(String productCode, String dateTime, String movieName, String address, String screenNo, String pricePerUnit) {
		super(productCode);
		this.movieDateTime = dateTime;
		this.movieName = movieName;
		this.cost = Double.parseDouble(pricePerUnit);
		this.address = new Address(address);
		this.screenNo = screenNo;
		this.pricePerUnit = Double.parseDouble(pricePerUnit);
		this.discounted = UtilityParser.isDiscounted(this.movieDateTime);
	}
	
	public MovieTicket() {
		
	}
	
	/**
	 * A copy constructor used to help create an object with a new reference value but the 
	 * same values for the fields of this class.
	 * @param toClone
	 */
	public MovieTicket(MovieTicket toClone) {
		super(toClone);
		this.movieDateTime = toClone.movieDateTime;
		this.movieName = toClone.movieName;
		this.cost = toClone.cost;
		this.address = toClone.address;
		this.screenNo = toClone.screenNo;
		this.pricePerUnit = toClone.pricePerUnit;
		this.discounted = toClone.discounted;
		
	}

	@XmlElement
	public String getMovieDateTime() {
		return this.movieDateTime;
	}

	
	public void setMovieDateTime(String dateTime) {
		this.movieDateTime = dateTime;
	}

	@XmlElement
	public String getMovieName() {
		return this.movieName;
	}

	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	@XmlElement
	public String getScreenNo() {
		return this.screenNo;
	}

	
	public void setScreenNo(String screenNo) {
		this.screenNo = screenNo;
	}

	@XmlElement
	public double getPricePerUnit() {
		return this.pricePerUnit;
	}

	
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@XmlElement
	public Address getAddress() {
		return this.address;
	}

	
	public void setAddress(Address address) {
		this.address = address;
	}

	@XmlElement
	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@XmlElement
	public boolean isDiscounted() {
		return this.discounted;
	}

	public void setDiscounted(boolean discounted) {
		this.discounted = discounted;
	}
	
	/**
	 * A simple method to convert the standard datetime String into a differently formatted String.
	 * If it's not able to parse the datetime string correctly, it will return a blank string.
	 * This method may throw an instance of ParseException.
	 * @return String
	 */
	public String movieDateTimeToDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
		Date date1 = new Date();
		try {
			date1 = dateFormat.parse(this.movieDateTime);
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.ENGLISH);
			return dateFormat2.format(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public double computeSubTotal(Invoice inv) {
		double p = this.cost * (double) super.getUnits();
		if(this.discounted) p -= p*.07;
		return p;
	}

	@Override
	public double getTax(Invoice inv) {
		return this.computeSubTotal(inv)*super.getTax(inv);
	}

	/**
	 * A great example of method overriding, this method prints out the first line of
	 * text seen in the Individual Invoice Report's line item for a movie ticket.
	 */
	@Override
	public String toString() {
		
		return "MovieTicket \'" + this.movieName + "\' @" + this.address.getStreet();
		
	}
	
	/**
	 * A special method designed to overcome the fact that you can't print out multiple lines
	 * to one row using System.out.printf(). This method prints out the second line of text
	 * seen in an Individual Invoice Report's Movie Ticket line item.
	 * @return
	 */
	public String toStringTwo() {
		NumberFormat formatter = new DecimalFormat("#0.00");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		BigDecimal u = BigDecimal.valueOf(this.getPricePerUnit());
		
		String x = "";
				
		if(this.discounted) {
			x = " - Tue/Thu 7% off";
		}
		
		return this.movieDateTimeToDate() + " (" + super.getUnits() 
				+ " units @ $" + formatter.format(u) + "/unit" + x + ")";
				
	}

	@Override
	public Product copyProduct(Product x) {
		MovieTicket clone = new MovieTicket((MovieTicket) x);
		return clone;
	}

	/**
	 * Unused method which can't be deleted. Call this poor design.
	 */
	@Override
	public void setDiscount() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Unused method which can't be deleted. Call this poor design.
	 */
	@Override
	public double getDiscount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}