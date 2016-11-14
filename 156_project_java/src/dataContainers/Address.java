package dataContainers;
import java.util.Scanner;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sun.xml.internal.txw2.annotation.XmlElement;


/**
 * A container class meant to store the various fields of your standard address. These fields
 * typically include: city, state, street, zip and country. This class contains several
 * constructors designed to handle different ways to parse a string to break it into its
 * subsequent fields.
 * @author Grant
 *
 */
@XmlRootElement
@XmlType(propOrder = {"street","city","state","zip","country"})
public class Address {
	private String city;
	private String state;
	private String street;
	private String zip;
	private String country;
	
	// Constructor
	public Address(String street,String city, String state, String zip,String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		
	}
	
	//Constructor for an array of strings with Address fields already sorted in order.
	public Address(String[] stringArray) {
		this.street = stringArray[0];
		this.city = stringArray[1];
		this.state = stringArray[2];
		this.zip = stringArray[3];
		this.country = stringArray[4];
	}
	
	//Constructor for a raw string with all address information yet to be sorted.
	public Address(String address2) {
		Scanner scan = new Scanner(address2);
		scan.useDelimiter(",");
		String[] addressBits = new String[5];
		int i = 0;
		while(scan.hasNext()) {
			addressBits[i] = scan.next().trim();
			i++;
		}
		this.street = addressBits[0];
		this.city = addressBits[1];
		this.state = addressBits[2];
		this.zip = addressBits[3];
		this.country = addressBits[4];
		scan.close();
	}
	
	public Address() {
		
	}
	
	// Getter & Setter methods

	public String getCity() {
		return city;
	}
	@XmlElement
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	@XmlElement
	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}
	@XmlElement
	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}
	@XmlElement
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}
	@XmlElement
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * A great example of method overriding, this method combines the various fields of this
	 * class to return a string of all of them. Very useful!
	 */
	@Override 
	public String toString() {
		String string = this.getStreet()+","+this.getCity()+","+this.getState()+","+this.getZip()+","+this.getCountry();
		return string;
		
	}
	
}

