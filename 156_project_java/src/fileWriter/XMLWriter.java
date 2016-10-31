package fileWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLWriter {
	
	public XMLWriter() {
		
	}
	
	/**
	 * Writes a given Class object (list) to a given FileOutputStream in XML. Utilizes the JAXB
	 * API to marshall the given class into a new XML file.
	 * @param list
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("hiding")
	public <Class> void write(Class list,FileOutputStream file) throws IOException {
		
	try {
				//Creates a new JAXBContext object. 
				JAXBContext context = JAXBContext.newInstance(list.getClass());
				//Creates the Marshaller.
				Marshaller marshaller = context.createMarshaller();
				//Edits properties of this marshaller to format the output.
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				//Marshalls the passed class to the passed FileOutputStream.
				marshaller.marshal(list, file);
				
			
		} catch (JAXBException e1) {
			// My code will never break.
			e1.printStackTrace();
		}
		
		
	}
		
}
