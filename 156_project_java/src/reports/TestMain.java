package reports;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Iterator;

import com.ceg.ext.InvoiceData;

import dataContainers.Address;
import dataContainers.Customer;
import dataContainers.Email;
import dataContainers.General;
import dataContainers.MovieTicket;
import dataContainers.ObjectFactory;
import dataContainers.Person;
import dataContainers.Product;
import dataContainers.Student;
import database.Database;
import linkedListADT.InvoiceComparator;
import linkedListADT.LinkedList;

public class TestMain {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		InvoiceData.removeAllPersons();
		InvoiceData.removeAllInvoices();
		
		
		
	}
}
