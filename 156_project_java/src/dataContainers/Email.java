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
	
	

	
}
