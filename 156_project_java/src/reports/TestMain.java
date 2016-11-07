package reports;

import java.sql.SQLException;

import com.ceg.ext.InvoiceData;

import Factories.PersonFactory;
import dataContainers.Person;
import database.Database;

public class TestMain {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		InvoiceData.removeAllPersons();
		InvoiceData.addPerson("12ab", "Grant", "Harrison", "123 Halloween", "Halloween Town", "NE", "68123", "USA");
		
		
	}
}
