package linkedListADT;

import java.util.Comparator;

import reports.Invoice;

/**
 * A class to compare Invoice objects, the Comparator class can accept an integer value in its 
 * constructor which then tells the Comparator object which compare method to use. Each integer
 * value is mapped to a unique field of the Invoice object that is comparable. This allows the
 * class to be flexible and for different objects to be created with different purposes. The keys
 * are criteria for sorting are as follows: 
 * <br>
 * <br>
 * <b>0: InvoiceID</b> Since this is a String, normal lexicographic ordering rules apply, so for
 * proper functioning ensure that all invoiceIDs have the same general format. <br>
 * <b>1: Date</b> Sorts the Invoices by date field. <br>
 * <b>2: Customer</b> Sorts by the Customer's Name, alphabetically.<br>
 * <b>3: Salesperson</b> Sorts by the Salesperson's last and then first name.<br>
 * @author Grant
 *
 */
public class InvoiceComparator implements Comparator<Invoice> {

	private int key;
	
	/**
	 * This constructor sets the key to the default 0 value, which sorts Invoice objects by 
	 * invoiceID.
	 */
	public InvoiceComparator() {
		this.key = 0;
	}
	
	/**
	 * This constructor sets the key to whichever int argument is passed to its parameter.
	 * @param key
	 */
	public InvoiceComparator(int key) {
		this.key = key;
	}
	
	/**
	 * A compare method that changes given some value which was passed to the InvoiceComparator 
	 * object in its constructor. The values and their methods are:
	 * <br>
	 * <br>
	 * <b>0: InvoiceID</b> <br>
	 * <b>1: Date</b> <br>
	 * <b>2: Customer (name)</b><br>
	 * <b>3: Salesperson (name)</b><br>
	 */
	@Override
	public int compare(Invoice inv0, Invoice inv1) {
		
		//What to do when sorting by invoiceID
		if(key == 0) {
			return inv0.getInvoiceCode().compareTo(inv1.getInvoiceCode());
		}
		
		//what to do if sorting by date
		if(key == 1) {
			return inv0.getInvoiceDateDate().compareTo(inv1.getInvoiceDateDate());
		}
		
		//what to do if sorting by customer
		if(key == 2) {
			return inv0.getCustomer().getCustomerName().toUpperCase().compareTo(inv1.getCustomer().getCustomerName().toUpperCase());
		}
		
		//what to do if sorting by person
		if(key == 3) {
			return inv0.getSalesperson().toUpperCase().compareTo(inv1.getSalesperson().toUpperCase());
		}
		
		return 0;
	}
	
}
