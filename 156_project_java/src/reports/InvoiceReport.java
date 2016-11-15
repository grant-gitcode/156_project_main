package reports;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dataContainers.MovieTicket;
import dataContainers.SeasonPass;
import dataContainers.Student;
import fileReader.InvoiceReader;

public class InvoiceReport {
	
	private ArrayList<Invoice> invoiceList;
	public static final String DASHED_ROW = new String(new char[130]).replace("\0", "-");
	
	/**
	 * Main method for InvoiceReport. Calls the DataConverter's static class uploadData() and uploads
	 * the various Product, Person, and Customer objects. Exports them to XML. Then creates an
	 * InvoiceReader object which stores an ArrayList of Invoice type. This ArrayList is then passed
	 * to the constructor of the InvoiceReport object which is then used to print out the reports.
	 * @param args
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	 public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
		 
		 //Calls all the code used in Phase 1 of this project. Generates XML files and objects.
		 DataConverter.uploadData();
		 
		 //Creation of the reader/writer pair of objects here. Then prints out the report summary.
		 InvoiceReader reader = new InvoiceReader("data/Invoices.dat");
		 InvoiceReport report = new InvoiceReport(reader.getInvoiceList());
		 report.printSummaryReport();
		 
		 System.out.println("Individual Invoice Detail Reports\n" + DASHED_ROW +"\n"+ DASHED_ROW);
		 
		 //Loops to print out the individual reports for each Invoice object in the report object's
		 //Invoice ArrayList.
		 for(int i = 0; i < report.invoiceList.size(); i++) {
			 report.printIndividualReports(report.invoiceList.get(i));
		 }
		 
	 }
	 
	 public InvoiceReport() {
		 
	 }
	 
	 public InvoiceReport(ArrayList<Invoice> invoiceList) {
		 this.invoiceList = invoiceList;
	 }
	 
	public ArrayList<Invoice> getInvoiceList() {
		return this.invoiceList;
	}

	public void setInvoiceList(ArrayList<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
	/**
	 * A method which uses the ArrayList<Invoice> of an InvoiceReport object to print out a summary
	 * for all individual Invoice objects. Sends text to the standard output.
	 */
	public void printSummaryReport() {
		
		System.out.println(DASHED_ROW + "\nExecutive Summary Report\n" + DASHED_ROW);
		System.out.printf("%-10s %-40s %-20s %-10s %-10s %-10s %-10s %-10s\n", "Invoice","Customer","Salesperson","Subtotal","Fees","Taxes","Discount","Total");

		for(int i = 0; i < this.invoiceList.size(); i++) {
			
			String fee = "0.00";
					if(this.invoiceList.get(i).getCustomer().getClass().equals(Student.class)) {
						fee = this.invoiceList.get(i).getStudentFee();
					}
					
			String discount = "-0.00";
			if(this.invoiceList.get(i).getCustomer().getClass().equals(Student.class)) {
				discount = this.invoiceList.get(i).getStudentDiscount();
			}
			
			System.out.printf("%-10s %-40s %-20s $%-10s $%-10s $%-10s $%-10s $%-10s \n", //120 long
					this.invoiceList.get(i).getInvoiceCode(),
					this.invoiceList.get(i).getCustomer().getCustomerName() + " ["+this.invoiceList.get(i).getCustomer().toString() +"]",
					this.invoiceList.get(i).getSalesperson(),
					this.invoiceList.get(i).sumSubTotals(),
					fee,
					this.invoiceList.get(i).sumTax(),
					discount,
					this.invoiceList.get(i).getTotalTotal()
					);
		}
		System.out.println();
		System.out.println(DASHED_ROW);
		System.out.printf("%-70s $%-10s $%-10s $%-10s $%-10s $%-10s \n", "TOTALS",this.sumMultInvSubtotals(),this.sumMultInvFees(),this.sumMultInvTaxes(),this.sumMultInvDiscounts(),this.sumMultInvTotalTotals());
		
		System.out.println();
	}
	
	/**
	 * Prints out a more detailed invoice report for a specific Invoice object in the 
	 * ArrayList<Invoice> parameter of this class. Sends text to the standard output.
	 * @param inv
	 */
	
	public void printIndividualReports(Invoice inv) {
		System.out.println("\nInvoice " + inv.getInvoiceCode() + "\n" + DASHED_ROW + "\n"+ DASHED_ROW);
		System.out.println("Salesperson: " + inv.getSalesperson());
		System.out.println("  "+ inv.getCustomer() + " (" + inv.getCustomer().getCustomerCode()+")");
		System.out.println("  [" + inv.getCustomer().toString()+"]");
		System.out.println("  "+inv.getCustomer().getPrimaryContact().getLastName()+", "+inv.getCustomer().getPrimaryContact().getFirstName());
		System.out.println("  "+inv.getCustomer().getCustomerAddress().getStreet());
		System.out.println("  "+inv.getCustomer().getCustomerAddress().getCity() + " "+inv.getCustomer().getCustomerAddress().getState()
				+" "+ inv.getCustomer().getCustomerAddress().getZip()+ " " + inv.getCustomer().getCustomerAddress().getCountry());
		System.out.println(DASHED_ROW);
		System.out.printf("%-10s %-80s %-10s %-10s %-10s\n", "Code","Item","Subtotal","Tax","Total");
		
		int j = 0;
		int k = 0;
		
		for(int i = 0; i < inv.getProductList().getProducts().size(); i++) {
			System.out.printf("%-10s %-78s $%10s $%10s $%10s\n", inv.getProductCode(i),inv.getProductDescription(i),inv.getProductSubtotal(i),inv.getProductTax(i),inv.getProductTotal(i));
			
			if(inv.getProductList().getProducts().get(i).getClass().equals(MovieTicket.class)) {
				System.out.printf("%10s %-80s\n","",inv.getMovieTickets().get(j).toStringTwo());
				j++;
			}
			
			if(inv.getProductList().getProducts().get(i).getClass().equals(SeasonPass.class)) {
				System.out.printf("%10s %-80s\n","",inv.getSeasonPasses().get(k).toStringTwo(inv));
				k++;
			}
		}
		
		System.out.println(DASHED_ROW);
		System.out.printf("%-89s $%10s $%10s $%10s\n", "SUB-TOTALS",inv.sumSubTotals(),inv.sumTax(),inv.sumTotals());
		if(inv.getCustomer().getClass().equals(Student.class)) {
			System.out.printf("%-113s $%10s%n","DISCOUNT (8% STUDENT AND NO TAX)",inv.getStudentDiscount());
			System.out.printf("%-113s $%10s%n", "ADDITIONAL FEE (STUDENT)",inv.getStudentFee());
		}
		System.out.printf("%-113s $%10s%n", "TOTAL",inv.getTotalTotal());
		
	}
	
	public String sumMultInvSubtotals() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i = 0; i < this.invoiceList.size(); i++) {
			x += Double.parseDouble(this.invoiceList.get(i).sumSubTotals());
		}
		
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
	
	public String sumMultInvFees() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i = 0; i < this.invoiceList.size(); i++) {
			if(this.invoiceList.get(i).getCustomer().getClass().equals(Student.class)) {
				x += Double.parseDouble(this.invoiceList.get(i).getStudentFee());
			}
		}
		
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
	 
	public String sumMultInvTaxes() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i = 0; i < this.invoiceList.size(); i++) {
			x += Double.parseDouble(this.invoiceList.get(i).sumTax());
		}
		
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
	
	public String sumMultInvDiscounts() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i = 0; i < this.invoiceList.size(); i++) {
			if(this.invoiceList.get(i).getCustomer().getClass().equals(Student.class)) {
				x += Double.parseDouble(this.invoiceList.get(i).getStudentDiscount());
			}
		}
		
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
		
	}
	
	public String sumMultInvTotalTotals() {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		double x = 0;
		
		for(int i = 0; i < this.invoiceList.size(); i++) {
			x += Double.parseDouble(this.invoiceList.get(i).getTotalTotal());
		}
		
		BigDecimal u = BigDecimal.valueOf(x);
		return format.format(u);
	}
} 