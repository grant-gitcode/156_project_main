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
		
		
		System.out.println("hal");
		Email pee = new Email("adf");
		Email poop = new Email("adf");
		System.out.println(pee.equals(poop));
	}
}
