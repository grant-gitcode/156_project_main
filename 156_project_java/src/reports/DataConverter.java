package reports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import containerClasses.*;
import dataContainers.*;
import fileReader.FlatFileReader;
import fileWriter.XMLWriter;

/**
 * Class designed very specifically to parse the expected Customer, Person and Product flat files.
 * Provides a method for creating the various objects associated with the flat files, along with
 * accessing the Products, Customers, and Persons objects which hold these objects. Also provides
 * methods to help write the XML files.
 * @author Grant
 *
 */
public class DataConverter {
	
	/** A lot of class attributes which make this code quite rigid. 
	*There is a specific order which this is all done in: first the Person file (0 subclasses),
	*then the Customer file (2 subclasses, General and Student), and finally the Product file 
	*(4 subclasses, MovieTicket, SeasonPass, ParkingPass, Refreshment). This order is kept
	*throughout the rest of this class and its methods, and it'd be imperative for future
	*additions that a consistent order is kept.
	*/
	private ArrayList<Class<?>[]> paramArray = new ArrayList<Class<?>[]>();
	
	/**An array of strings which correspond to the type codes found in the flat files.
	 * A blank is left for the Person class, but otherwise
	 * G = General, S = Student, M = MovieTicket, S = SeasonPass, P = ParkingPass, and 
	 * R = Refreshment.
	 */
	private String[] classPatternArray = {"","G","S","M","S","P","R"};
	private String delimiter = ";";
	@SuppressWarnings("rawtypes")
	
	//Loads all the classes based upon the order listed above.
	private Class[] classArray = {Person.class,General.class,Student.class,MovieTicket.class,SeasonPass.class,ParkingPass.class,Refreshment.class};
	
	//This is the expected length of the type code ("G","S", so on...) It's 0 for the first entry
	//because the Person flat file has no subclasses and no type code.
	private int[] classPatternLocationArray = {0,1,1,1,1,1,1};
	
	//Creates static array of various Container subclass objects.
	private static Container[] containerArray = {new Persons(),new Customers(),new Products()};
		
	public static void uploadData() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		DataConverter conv = new DataConverter();
		
		
		/**
		 * Here we're creating a bunch of arrays which have elements of the Class.class which
		 * are used in the constructors of the various classes to be created. So, for example,
		 * the Person class constructor takes 4 arguments of the String type, and adderArray1
		 * reflects this fact. This also follows the pattern established earlier, but I'll comment
		 * for clarity and reminder.
		 */
		Class<?>[] adderArray1 = {String.class,String.class,String.class,String.class};		//Person
		Class<?>[] adderArray2 = {String.class,String.class,String.class,String.class};		//General
		Class<?>[] adderArray3 = {String.class,String.class,String.class,String.class};		//Student
		Class<?>[] adderArray4 = {String.class,String.class,String.class,String.class,String.class,String.class};	//MovieTicket
		Class<?>[] adderArray5 = {String.class,String.class,String.class,String.class,String.class};	//SeasonPass
		Class<?>[] adderArray6 = {String.class,String.class};	//ParkingPass
		Class<?>[] adderArray7 = {String.class,String.class,String.class};		//Refreshment
		
		//Now we add those arrays, in order, of course.
		
		conv.paramArray.add(adderArray1);
		conv.paramArray.add(adderArray2);
		conv.paramArray.add(adderArray3);
		conv.paramArray.add(adderArray4);
		conv.paramArray.add(adderArray5);
		conv.paramArray.add(adderArray6);
		conv.paramArray.add(adderArray7);
		
		//Here's where we upload the specific files which we are parsing.
		String[] fileArray = {"data/Persons.dat","data/Customers.dat","data/Products.dat"};
		
		//And here's where we create the specific output files to be written to.
		FileOutputStream[] outputArray = {new FileOutputStream("data/Persons.xml"),new FileOutputStream("data/Customers.xml"),new FileOutputStream("data/Products.xml")};
		
		conv.filesToParse(7, fileArray, outputArray,DataConverter.containerArray);
			
		}
	
	/**
	 * The general idea of this method is to create a mechanism which waits until all 
	 * subclasses within a given flat file are created to write it to an XML file.
	 * So, for example, this method will wait until both the General and Student subclasses 
	 * are created to then write it to the appropriate XML file. 
	 * @param subclassNum
	 * @param fileArray
	 * @param outputArray
	 * @param containerArray
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void filesToParse(int subclassNum,String[] fileArray,FileOutputStream[] outputArray,Object[] containerArray) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		//Loops through a certain number of times equal to the total number of subclasses being
		//used for creation. So think of p as being representative to the number of subclasses
		//and their relative position in the specific order defined above.
		for(int p = 0; p < subclassNum; p++) {
			
			/**r is used to determine which objects are pulled from the containerArray, 
			 * outputArray, and fileArray. This logic is very specific to the assignment 
			 * and would change were, say, 4 flat files to be parsed.
			 */
			int r = 0;
			if(p==1 || p==2) r = 1;
			if(p>2) r = 2;
			
			FlatFileReader fileReader = new FlatFileReader(fileArray[r]);
			XMLWriter writer = new XMLWriter();
			//Adds to a given Container object some object created by the fileReader object.
			((Container) containerArray[r]).addContained((ArrayList) fileReader.createObject(this.classArray[p],this.classPatternArray[p],this.classPatternLocationArray[p],this.delimiter,this.paramArray.get(p)));
			
			/**This bit of logic decides if all the subclasses in a given flat file have been
			 * created; if so, then the XMLWriter object is allowed to write to the specific 
			 * file and with the specific object denoted by r (which is determined by the total
			 * number of flat files being parsed).
			 */
			if(p == 0 || p == 2 || p == 6) writer.write((Container) containerArray[r],outputArray[r]);
		}
		
	}
		
	public DataConverter() {
			
		}
	
	/**
	 * Methods which access specific Container objects from this class.
	 */
	
	public static Persons accessPersonArrayList() {
		return (Persons) DataConverter.containerArray[0];
	}
	
	public static Customers accessCustomerArrayList() {
		return (Customers) DataConverter.containerArray[1];
	}
	
	public static Products accessProductArrayList() {
		return (Products) DataConverter.containerArray[2];
	}
	
}
