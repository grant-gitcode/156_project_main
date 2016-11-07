package database;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.*;

import dataContainers.Product;

/**The class Database is intended to connect some java code with a specific MySQL database hosted
 * on the unl.cse.edu server, particularly the portion which contains a given student's database.
 * The class has these overarching functions: Create a connection to the database; handle all 
 * queries and updates which might be called by the java code; and clean up the connections at the
 * end of the program.
 * 
 * @author Grant
 *
 */
public class Database {
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	ResultSet rt = null;
	
	//Creating database string constants.
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://cse.unl.edu:3306/gharrison";
	
	//Import info. that should be hacked.
	static final String USER = "gharrison";
	static final String PASS = "gharrison";
	
	/**The connectToDB method is designed to take existing class constants and use them to 
	 * connect to a given database. Since the variables this method uses come directly from
	 * the class itself and are fixed, this makes it very rigid and specific to this assignment.
	 * First, this method calls the Class.forName() method to set the appropriate name of the 
	 * database. Second, the DriverManager class's method .getConnection() is called to create
	 * a Connection object.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public Connection connectToDB() throws ClassNotFoundException, SQLException {
		
		//Sets the name of this class as a very specific path which is used by the database.
		Class.forName(JDBC_DRIVER);
		
		//Creates a Connection object by calling a method from the DriverManager class.
		//Requires the URL of the database, and a username and password.
		conn = (Connection) DriverManager.getConnection(DATABASE_URL,USER,PASS);
		return conn;
	}
	
	/**A clean up method to close the connection created by the connectToDB() method, so as to
	 * release database resources such as cursors, handles. Checks to make sure the connection 
	 * was actually opened first. 
	 */
	public void close() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**The purpose of this method is to have an easy way to access the Products
	 * table data in a ResultSet in a convenient and concise method.
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet getTableSet(String table) throws SQLException {
		
		String query = "SELECT * FROM !";
		query = query.replace("!", table);
		ps = conn.clientPrepareStatement(query);
		rt = (ResultSet) ps.executeQuery();
		
		return rt;
		
	}
	
	/**
	 * The purpose of this method is to be a general way to return a single row of
	 * a table in the form of a ResultSet. The method requires the name of the table
	 * to be specified, and some specific identified (an ID for the table) to be 
	 * given.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ResultSet getRowsFromTable(String table, String column, int id) throws SQLException, ClassNotFoundException {
		
		String query = "SELECT * FROM ! WHERE $=?";
		this.connectToDB();
		query = query.replace("!",table);
		query = query.replace("$", column);
		ps = conn.clientPrepareStatement(query);
		ps.setInt(1, id);
		rt = (ResultSet) ps.executeQuery();
		
		
		return rt;
		
	}
	
	/**
	 * Gets the join of the Products, ProductInvoice and Invoice tables and returns 
	 * them as a ResultSet.
	 * @throws SQLException 
	 */
	public ResultSet getBigProductsJoin() throws SQLException {
		
		String query = "Products AS P JOIN ProductsInvoice AS PI JOIN Invoice AS I " +
				"ON P.productsID = PI.productsID AND PI.invoiceID = I.invoiceID";
		ps = conn.clientPrepareStatement(query);
		rt = (ResultSet) ps.executeQuery();
		
		ps.close();
		return rt;
	}
	
	/**
	 * Retrieves a table of an Invoice's Products from the database.
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet getInvoiceResultSet(int i) throws SQLException {
		
		String query = "SELECT * FROM Products AS P JOIN ProductsInvoice AS PI "
				+ "ON P.productsID = PI.productsID WHERE invoiceID=?";
		ps = conn.clientPrepareStatement(query);
		ps.setInt(1, i);
		
		rt = (ResultSet) ps.executeQuery();
		
		
		return rt;
	}
	
	/**
	 * Takes a specific productsID and finds a specific row of the join between
	 * Products and ProductsInvoice.
	 * @param i
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getInvoiceProduct(int i) throws SQLException {
			
			String query = "SELECT * FROM Products AS P JOIN ProductsInvoice AS PI "
					+ "ON P.productsID = PI.productsID WHERE productsInvoiceID=?";
			ps = conn.clientPrepareStatement(query);
			ps.setInt(1, i);
			
			rt = (ResultSet) ps.executeQuery();
			
			
			return rt;
		}
	
	

}
