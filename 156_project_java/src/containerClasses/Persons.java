package containerClasses;

import dataContainers.Person;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * An subclass of the Container Class, Persons is a container class designed to hold 
 * an ArrayList of Person objects and provide methods to search that ArrayList.
 * @author Grant
 *
 */
@XmlRootElement ( name = "Persons")
@XmlSeeAlso({dataContainers.Person.class})
public class Persons extends Container{

	@SuppressWarnings("rawtypes")
	private ArrayList persons = new ArrayList<Person>();
	
	public Persons(ArrayList<Person> customerList) {
		this.persons = customerList;
	}
	
	public Persons() {
	
	}
	
	/**
	 * Overwrites the current ArrayList with the parameter ArrayList.
	 * @param person
	 */
	public void setPersons(ArrayList<Person> person) {
		this.persons = person;
	}
	
	/**
	 * Returns the current ArrayList of Person objects.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@XmlAnyElement(lax=true)
	public ArrayList getPersons() {
		return this.persons;
	}
	
	/**
	 * Adds to, but does not overwrite, the current ArrayList of Person objects.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addContained(ArrayList person) {
		for(int r = 0; r < person.size(); r++) {
			this.persons.add(person.get(r));
		}
	}
	
	/**
	 * Searches through the ArrayList of this Persons object to find a specific Person object
	 * which has a personCode which matches the string passed to the method. If no such Person
	 * object is found, null is returned.
	 */
	public Person searchContained(String personCode) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for(int i = 0; i < this.persons.size(); i++) {
			if(this.persons.get(i).getClass().getMethod("getPersonCode").invoke(this.persons.get(i),new Object[0]).toString().compareTo(personCode) == 0) {
				
				return (Person) this.persons.get(i);
			}
		}
		return null;
	}
	
}
