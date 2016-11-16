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
		
		Database db = new Database();
		db.connectToDB();
		ObjectFactory fact = new ObjectFactory(db);
		LinkedList list = new LinkedList(new InvoiceComparator(4));
		
		int x = db.getTableSize("Invoice");
		for(int i = 1; i <= x; i++) {
			Invoice inv = fact.createInvoice(i);
			list.add(inv);
		}
		for(int i = 0; i < list.size(); i++) {
			System.out.println(((Invoice)list.get(i)).getInvoiceCode());
		}
		InvoiceReport report = new InvoiceReport();
		report.printSummaryReport(list);
		
		Iterator loop = list.iterator();
		while(loop.hasNext()) {
			Invoice inv = (Invoice) loop.next();
			report.printIndividualReports(inv);
		}
		
		
		
	}
}
