package dataContainers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * The UtilityParser class provides a variety of functions which do not fit neatly into
 * other classes. All its methods are static and a specific instance need not be created.
 * @author Grant
 *
 */
public class UtilityParser {

	/**
	 * This method is used to parse a full name string into two first name/last name strings,
	 * which are returned as part of a String[] object of size 2. It is assumed that the full
	 * name contains a comma separating the last and first names.
	 * @param fullName
	 * @return String[]
	 */
	public static String[] nameParse(String fullName) {
		Scanner scan = new Scanner(fullName);
		scan.useDelimiter(",");
		String[] results = new String[2];
		if(scan.hasNext()) results[0] = scan.next().trim();
		if(scan.hasNext()) results[1] = scan.next().trim();
		scan.close();
		return results;
	}
	
	/**This method is specifically designed to take a string containing one or more emails
	 * and parse them into individual email strings returned as an ArrayList<String>. It is
	 * assumed that the emails are comma delineated.
	 * @param mail
	 * @return ArrayList<String>
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> emailParse(String mail) {
		Scanner scan = new Scanner(mail);
		scan.useDelimiter(",");
		@SuppressWarnings("rawtypes")
		ArrayList<String> emailArray = new ArrayList();
		while(scan.hasNext()) {
			emailArray.add(scan.next().trim());
		}
		scan.close();
		return emailArray;
	}
	
	/** This method is used to determine if a movie ticket falls on a tuesday or a thursday,
	 * and as a result, if it is discounted. Returns a boolean value. This method assumes the
	 * string parameter is formatted as "yyyy-MM-dd HH:mm". 
	 * @param dateTime
	 * @return boolean
	 */
	public static boolean isDiscounted(String dateTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date movieDate = new Date();
		try {
			movieDate = format.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(movieDate);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if(weekDay == 3 || weekDay == 5) return true;
		else return false;
	}
	
	/**This method can turn any string of the form "yyyy-MM-dd" into a Date object.
	 *Returns a Date object. 
	 * @param inputString
	 * @return Date
	 */
	public static Date stringToDate(String inputString) {
		Date newDate = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = format.parse(inputString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newDate;
		
	}
	
	/**
	 * This method is used to convert a date object to a formatted string of the 
	 * format "yyyy-MM-dd HH:mm".
	 * @param date
	 * @return String
	 */
	public static String formatDateOutput(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(date);
	}

}
