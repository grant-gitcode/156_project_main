package Factories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataContainers.Email;
import database.Database;

public class EmailFactory {
	
	/**
	 * The purpose of this method is to create an Email object based upon a given
	 * row or rows in the Email table in the database which corresponds to a given 
	 * Person object. Finds all emails associated with a PersonID and creates a sole
	 * Email object with multiple email strings in it.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Email createEmail(int i) throws SQLException, ClassNotFoundException {
		
		Email e = new Email();
		ArrayList<String> emailList = new ArrayList<String>();
		Database db = new Database();
		
		ResultSet rt = db.getRowsFromTable("Email","personID", i);
		
		while(rt.next()) {
			emailList.add(rt.getString("emailAddress"));
		}
		
		e.setEmail(emailList);
		
		rt.close();
		db.close();
		return e;
	}
	
}
