package containerClasses;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import dataContainers.Person;
import dataContainers.Record;

/**
 * Container is the abstract class which provides a basic template for storing objects and 
 * accessing them. It also provides a method, searchContained(), which searches through the
 * object based upon a code to find a given object reference.
 * <br><br>
 * Subclasses: Customers, Persons, Products.
 * @author Grant
 *
 */
public abstract class Container {
	
	@SuppressWarnings("rawtypes")
	private ArrayList contained = new ArrayList<Container>();
	
	public Container() {
		
	}

	/**
	 * Accepts some ArrayList and takes its objects and places them in the Container ArrayList.
	 * @param contained
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addContained(ArrayList contained) {
		for(int r = 0; r < contained.size(); r++) {
			this.contained.add(contained.get(r));
		}
	}
	
	/**
	 * Searches for a given object based upon a string which corresponds to its personCode.
	 * @param personCode
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Record searchContained(String personCode) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for(int i = 0; i < this.contained.size(); i++) {
			if(this.contained.get(0).getClass().getMethod("getPersonCode").invoke(this.contained.get(0),0).toString().compareTo(personCode) == 0) {
				return (Person) this.contained.get(0);
			}
		}
		return null;
	}

}
