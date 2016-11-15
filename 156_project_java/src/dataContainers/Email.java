package dataContainers;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class simply to hold several emails. Typically used inside of the Customer or Person classes.
 * This class holds an ArrayList<String> which contains one or more emails.
 * @author Grant
 *
 */
@XmlRootElement(name = "email")
public class Email {
	
	private ArrayList<String> email;
	
	public Email() {
		
	}
	
	public Email(String mailString) {
		this.email = UtilityParser.emailParse(mailString);
	}

	@XmlElement
	public ArrayList<String> getEmail() {
		return email;
	}

	public void setEmail(String mailString) {
		this.email = UtilityParser.emailParse(mailString);
	}
	
	public void setEmail(ArrayList<String> x) {
		this.email = x;
	}
	
	/**
	 * A simple method to test for equality between two non-null, non-empty email objects. 
	 * Two email objects are considered equal if two criteria are met:
	 * <br><br>
	 * 1. The lengths of the ArrayList of type Email in both are equal. <br>
	 * 2. The uppercase version of the email strings at position <i>n</i> matches in both email
	 * ArrayLists for all positions <i>n</i>. That is to say, both Email objects hold the same 
	 * emails in the same order.
	 * @param mail
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		Email mail = (Email) obj;
		
		if(!this.isEmpty() && !mail.isEmpty()) {
			if(this.email.size() == mail.email.size()) {
				for(int i = 0; i < this.email.size();i++) {
					if(!this.email.get(i).toUpperCase().equals(mail.email.get(i).toUpperCase())) {
						return false;
					}
					if(i == this.email.size()-1) return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * A method to determine if an Email object is empty -- that is to say,
	 * if an Email object has all of its fields as being null.
	 * @return
	 */
	public boolean isEmpty() {
		
		if(this.email == null) return true;
		
		return false;
	}

	/**Overriden hashCode() method for the Email class.
	 * 
	 */
	@Override
	public int hashCode() {
		int result = 31;
		result += ((this.email == null) ? 0: this.email.hashCode());
		return result;
	}
	
}
