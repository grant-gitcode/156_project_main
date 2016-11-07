package Factories;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

import dataContainers.Address;
import dataContainers.MovieTicket;
import dataContainers.ParkingPass;
import dataContainers.Product;
import dataContainers.Refreshment;
import dataContainers.SeasonPass;
import database.Database;

/**
 * The purpose of this class is to provide a convenient way to create Product objects
 * when the specific sub class is unknown from the ResultSet given by a specific query.
 * @author Grant
 *
 */
public class ProductFactory {
	
	/**
	 * Given a ResultSet object which contains the join of Products and ProductInvoice,
	 * and a row at which to set the database cursor, this method will create 
	 * the object in question. It also sets the attachedProduct and the Invoice of
	 * said object.
	 * @param rt
	 * @param i
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public Product createProduct(int i, int invoiceID) throws SQLException, ClassNotFoundException {
		
		Database db = new Database();
		ResultSet rt = db.getInvoiceProduct(i);
		String type;
		Product p = null;
		
		type = rt.getString("subType").toUpperCase();
		
		/*This provides a case for all four Product sub classes. The nature of the
		 * switch statement allows for this code to be easily extensible, as the 
		 * only adjustment needed to be made for new Product subclasses would be 
		 * the addition of a case.
		 */
		switch(type) {
			case "M": 
				MovieTicket m = new MovieTicket();
				m.setMovieName(rt.getString("productName"));
				m.setCost(rt.getDouble("cost"));
				m.setMovieDateTime(rt.getString("movieDateTime"));
				m.setScreenNo(rt.getString("movieScreenNo"));
				String str = rt.getString("movieAddress");
				Address address = new Address(str);
				m.setAddress(address);
				p = m;
				break;
			case "S":
				SeasonPass s = new SeasonPass();
				s.setName(rt.getString("productName"));
				s.setCost(rt.getDouble("cost"));
				p = s;
				break;
			case "P":
				ParkingPass pp = new ParkingPass();
				pp.setCost(rt.getDouble("cost"));
				p = pp;
				break;
			case "R":
				Refreshment r = new Refreshment();
				r.setName(rt.getString("productName"));
				r.setCost(rt.getDouble("cost"));
				p = r;
				break;
		}
		
		p.setProductCode(rt.getString("productCode"));
		p.setUnits(rt.getInt("units"));
		
		
		/*The logic of this is simple: If the Product has an attachedProductID,
		 *then a method is called which searches for the appropriate object in
		 *the Products table; and returning this, it sets the attachedProduct
		 *to this object. 
		 */
		if(rt.getInt("attachedProductID") != 0) {
			ResultSet pd = db.getRowsFromTable
					("Products", "ProductsID",rt.getInt("attachedProductID"));
			p.setAttachedProduct(this.createAttachedProduct(pd, 1,invoiceID));
		}
		
		rt.close();
		db.close();
		
		return p;
		
	}
	
	public Product createAttachedProduct(ResultSet rt, int i, int id) throws SQLException {
		
		Database db = new Database();
		String type;
		Product p = null;
		
		rt.next();
		type = rt.getString("subType").toUpperCase();
		
		/*This provides a case for all attached classes. The nature of the
		 * switch statement allows for this code to be easily extensible, as the 
		 * only adjustment needed to be made for new Product subclasses would be 
		 * the addition of a case.
		 */
		switch(type) {
			case "M": 
				MovieTicket m = new MovieTicket();
				m.setMovieName(rt.getString("productName"));
				m.setCost(rt.getDouble("cost"));
				m.setMovieDateTime(rt.getString("movieDateTime"));
				m.setScreenNo(rt.getString("movieScreenNo"));
				String str = rt.getString("movieAddress");
				Address address = new Address(str);
				m.setAddress(address);
				p = m;
				break;
			case "S":
				SeasonPass s = new SeasonPass();
				s.setName(rt.getString("productName"));
				s.setCost(rt.getDouble("cost"));
				p = s;
				break;
		}
		
		p.setProductCode(rt.getString("productCode"));
		p.setUnits(rt.getInt("units"));

		rt.close();
		db.close();
		
		return p;
	}
	
}
