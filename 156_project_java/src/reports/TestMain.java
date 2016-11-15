package reports;

import java.sql.SQLException;

import com.ceg.ext.InvoiceData;

import dataContainers.Address;
import dataContainers.Customer;
import dataContainers.Email;
import dataContainers.General;
import dataContainers.MovieTicket;
import dataContainers.Person;
import dataContainers.Product;
import dataContainers.Student;
import database.Database;

public class TestMain {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		String cat = "Dole, bob";
		String cat1 = "Cole, bob";
		int x = cat.compareTo(cat1);
		System.out.println(x);
		
		Email p = new Email("cat,dog,fish");
		Email q = new Email("cat,dog,FISH");
		boolean h = p.equals(q);
		System.out.println(h);
		
		Address y = new Address("ABc","abc","abc","abc","abc");
		Address z = new Address("abc","abc","ABC","abc","abc");
		System.out.println(y.equals(z));
		
		Person Grant = new Person();
		Grant.setFirstName("Grant");
		Grant.setLastName("Harrison");
		Grant.setPersonCode("abc");
		Grant.setAddress(y);
		Grant.setEmails(p);
		
		Person FakeGrant = new Person();
		FakeGrant.setFirstName("GRant");
		FakeGrant.setLastName("HaRRISON");
		FakeGrant.setPersonCode("abc");
		FakeGrant.setAddress(z);
		FakeGrant.setEmails(q);
		
		System.out.println(Grant.equals(FakeGrant));
		
		Student booby = new Student();
		booby.setCustomerAddress(y);
		booby.setCustomerCode("abc");
		booby.setPrimaryContact(Grant);
		booby.setCustomerName("Booby");
		
		Student titty = new Student();
		titty.setCustomerAddress(z);
		titty.setCustomerCode("abc");
		titty.setPrimaryContact(FakeGrant);
		titty.setCustomerName("Booby");
		System.out.println(titty.equals(booby));
		
		Product yoda = new MovieTicket();
		System.out.println(yoda.getClass().getSuperclass().getSuperclass());
	}
}
