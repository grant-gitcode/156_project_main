package fileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import reports.Invoice;

public class InvoiceReader {
	
	private ArrayList<Invoice> invoiceList;
	
	public InvoiceReader() {
		
	}
	
	/**Constructor for the InvoiceReader class. Takes in a file location as a String. Parses the 
	 * file to create individual invoice objects and collects them into an ArrayList of Invoice
	 * type. 
	 * 
	 * @param fileString
	 * @throws FileNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public InvoiceReader(String fileString) throws FileNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		 
		 //Creation of scanner object and necessary String array to store the delineated strings from
		 //the Invoice.dat file and also the creation of an array list to hold the invoice objects.
		 File file = new File(fileString);
		 Scanner scan = new Scanner(file);
		 scan.useDelimiter(";");
		 String[] stringArray = new String[5];
		 this.invoiceList = new ArrayList<Invoice>();
		 
		 //for my algorithm, it's not necessary to know the length of the file, so we ignore the initial
		 //integer value by skipping to the next line.
		 scan.nextLine();
		 
		 //The outer for loop loops through each line of the document. The inner for loop breaks up any
		 //given line into its 5 subcomponents, which are then stored in a string array and used in
		 //the constructor of a new Invoice object, which is added to an Invoice array list.
		 while(scan.hasNext()) {
			 for(int i = 0; i < 5; i++) {
				 if(i != 4) {
					 stringArray[i] = scan.next().replaceAll("(\\r|\\n)","");
				 }
				 else {
					 stringArray[i] = scan.nextLine();
				 }
			 }
			 this.invoiceList.add(new Invoice(stringArray[0],stringArray[1],stringArray[2],stringArray[3],stringArray[4]));
		 }
		 
		 scan.close();
	 }

	public ArrayList<Invoice> getInvoiceList() {
		return this.invoiceList;
	}

	public void setInvoiceList(ArrayList<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
}
