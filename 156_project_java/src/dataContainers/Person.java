package dataContainers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * A class modeling the qualities and behaviors of a person. Stores data like: personCode,
 * firstName, lastName, and address. This class extends the Record class.
 * @author Grant
 *
 */
@XmlRootElement
@XmlSeeAlso({dataContainers.Address.class,dataContainers.Email.class})
@XmlType( propOrder = {"personCode","firstName","lastName","address","emails"})
public class Person extends Record {
	
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address; // Person class owns Address class as a field
	private Email emails;
	
	//Constructor
	public Person(String personCode, String name, String address2, String emailAddress) {
		this.personCode = personCode;
		String[] nameArray = UtilityParser.nameParse(name);
		this.firstName = nameArray[1];
		this.lastName = nameArray[0];
		this.address = new Address(address2);
		this.emails = new Email(emailAddress);
	}
	
	public Person(String personCode, String name, String address2) {
		this.personCode = personCode;
		String[] nameArray = UtilityParser.nameParse(name);
		this.firstName = nameArray[1];
		this.lastName = nameArray[0];
		this.address = new Address(address2);
		this.emails = new Email();
	}
	
	public Person() {
		
	}
	
	
	@XmlElement
	public String getPersonCode() {
		return personCode;
	}

	
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@XmlElement
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement
	public Address getAddress() {
		return address;
	}

	
	public void setAddress(Address address) {
		this.address = address;
	}

	@XmlElement
	public Email getEmails() {
		return this.emails;
	}

	
	public void setEmails(String emailAddress) {
		this.emails = new Email(emailAddress);
	}
	
	public void setEmails(Email y) {
		this.emails = y;
	}
	
	/**
	 * This method tests for equality between two Person objects, returning true if the are the
	 * same along the following criteria: <br><br>
	 * <b> 1. PersonCode </b> The PersonCode strings must match exactly between each object. This
	 * is case sensitive. <br>
	 * <b> 2. firstName </b> The Strings representing the first names of each Person must match. 
	 * This is not case sensitive. <br>
	 * <b> 3. lastName </b> The Strings representing the last names of each Person must match.
	 * This is not case sensitive. <br>
	 * <b> 4. Address </b> The Address objects must match using their previously defined .equals()
	 * method. This is not case sensitive. <br>
	 * <b> 5. Emails </b> The Email objects in each Person object must batch based upon a previously
	 * defined .equals() method. This is not case sensitive, and the order of Strings inside the
	 * Email object does matter.  
	 * @param pers
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		Person pers = (Person) obj;
		
		if(this.personCode.equals(pers.getPersonCode())) {
			if(this.firstName.toUpperCase().equals(pers.getFirstName().toUpperCase())) {
				if(this.lastName.toUpperCase().equals(pers.getLastName().toUpperCase())) {
					if(this.address.equals(pers.getAddress())) {
						if(this.emails.equals(pers.getEmails())) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
}

