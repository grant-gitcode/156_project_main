package database;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.*;

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
	public void connectToDB() throws ClassNotFoundException, SQLException {
		
		//Sets the name of this class as a very specific path which is used by the database.
		Class.forName(JDBC_DRIVER);
		
		//Creates a Connection object by calling a method from the DriverManager class.
		//Requires the URL of the database, and a username and password.
		conn = (Connection) DriverManager.getConnection(DATABASE_URL,USER,PASS);
		
	}
	
	
	
	
	
	
	/**A clean up method to close the connection created by the connectToDB() method, so as to
	 * release database resources such as cursors, handles. Checks to make sure the connection 
	 * was actually opened first. 
	 */
	public void closeDBConnection() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
