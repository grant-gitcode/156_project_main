package Factories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import containerClasses.Products;
import dataContainers.Product;
import database.Database;

/**
 * This class will help create Products objects to hold collections of Product objects.
 * @author Grant
 *
 */
public class ProductsFactory {
	
	/**
	 * We pass the invoiceID to this method to get the appropriate list of Product 
	 * objects to create.
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Products createProducts(int id) throws SQLException, ClassNotFoundException {
		
		Products products = new Products();
		ArrayList<Product> productList = new ArrayList<Product>();
		ProductFactory factoryP = new ProductFactory();
		Database db = new Database();
		
		ResultSet rt = db.getRowsFromTable("ProductsInvoice", "invoiceID", id);
		while(rt.next()) {
			int productsInvoiceID = rt.getInt("productsInvoiceID");
			productList.add(factoryP.createProduct(productsInvoiceID, id));
		}
		
		products.setProducts(productList);
		
		rt.close();
		db.close();
		
		return products;
	}

}
