package Factories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import reports.Invoice;

public class InvoiceFactory {
	
	/**
	 * This method creates the Invoice located at a specific row in the Invoice table.
	 * Accepts a customerID to create potentially multiple Invoice objects, returned
	 * as an ArrayList.
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ArrayList<Invoice> createInvoiceList(int i) throws SQLException, ClassNotFoundException {
		
		Database db = new Database();
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		ProductsFactory factoryPRDTS = new ProductsFactory();
		ResultSet rt = db.getRowsFromTable("Invoice","customerID", i);
		
		while(rt.next()) {
			Invoice inv = new Invoice();
			inv.setInvoiceCode(rt.getString("invoiceCode"));
			inv.setInvoiceDate(rt.getDate("date"));	
			inv.setProductList(factoryPRDTS.createProducts(i));
		}
		
		
		rt.close();
		db.close();
		
		return invoices;
	}

}
