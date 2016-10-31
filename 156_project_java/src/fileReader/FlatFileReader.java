package fileReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class FlatFileReader is used to create objects based upon a given flat file.
 * @author Grant
 *
 */
public class FlatFileReader {
	
	private String file;
	
	public FlatFileReader(String file) {
		this.file = file;
	}
	
	/**
	 * The createObject() method will parse a given flat file for a specific pattern of arguments
	 * to use in the constructor of a provided class.
	 * @param inputClass
	 * @param classPattern
	 * @param classPatternLocation
	 * @param delimiter
	 * @param objectArray
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings({ "hiding", "rawtypes", "unchecked" })
	public <Object> java.lang.Object createObject(Class<?> inputClass,String classPattern,int classPatternLocation,String delimiter, Class<?>[] objectArray) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException {
		
		//Creates a scanner for some file using some delimiter.
		Scanner scan = new Scanner(new FileInputStream(this.file));
		scan.useDelimiter(delimiter);

		String subclassType = "";
		
		//Skips the typical integer at the beginning of the flat files provided in this
		//homework
		scan.nextLine();
		
		ArrayList fields = new ArrayList();
		ArrayList objects = new ArrayList();
		
		//Big loop for each line in a flat file.
		while(scan.hasNextLine()) {
			
			String substr = scan.nextLine();
			Scanner scan2 = new Scanner(substr);
			scan2.useDelimiter(delimiter);
			
				//Scans a given line in a flat file and breaks it into substrings stored in fields.
				while(scan2.hasNext()) {
					
					fields.add(scan2.next());
					
				}
				
			scan2.close();
			
			/** For all flat files which have subclasses (not the Person flat file), then
			 * this bit of logic removes the type codes ("G","S",etc..) from the array of
			 * strings which are to be used in the constructor of these objects (since they
			 * don't need to know these type codes).
			 */
			if(classPatternLocation != 0) {
			subclassType = (String) fields.get(classPatternLocation);
			fields.remove(classPatternLocation);
			}
			
			
			if(fields.size() < objectArray.length && classPatternLocation == 0) {
				int diff = objectArray.length - fields.size();
				for(int y = 0; y < diff; y++) {
					fields.add("");
				}
			}
			
			
			if(fields.size() == objectArray.length) {
				
				//Creates an object using the fields array of strings.
				if(subclassType.compareTo(classPattern) == 0) {
					Constructor<?> construct = inputClass.getConstructor((objectArray));
					java.lang.Object object = construct.newInstance(fields.toArray());
					objects.add(object);
				}
			}
			fields = new ArrayList();
			
			
		}
		scan.close();
		return objects;
	}

}
