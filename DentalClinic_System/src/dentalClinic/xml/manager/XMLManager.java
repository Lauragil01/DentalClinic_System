package dentalClinic.xml.manager;

import java.io.File;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import dentalClinic.pojos.*;

public class XMLManager {
	
	private static final String PERSISTENCE_PROVIDER = "dentalClinic-provider";
	private static EntityManagerFactory factory;
	private static EntityManager em;
	static Scanner sc = new Scanner(System.in);
	
	public void java2XmlDentist(Dentists d) throws Exception {
		
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Dentists.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/External-Dentist.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(d, file);
		}
	
	public Dentists xml2JavaDentist() throws Exception {
		//Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Dentists.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshall the XML document from a file
		File file = new File("./xmls/External-Dentist.xml");
		Dentists dentists=(Dentists)unmarshaller.unmarshal(file);
			return dentists;
		}
		
	//LO NECESITO????
	public void xml2JavaAppointment() throws Exception {
		//Create the JAXBContext
				JAXBContext jaxbContext = JAXBContext.newInstance(Dentist.class);
				// Get the unmarshaller
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

				// Use the Unmarshaller to unmarshall the XML document from a file
				File file = new File("./xmls/External-Appointment.xml");
				Dentist dentist =   (Dentist) unmarshaller.unmarshal(file);
				
				if(dentist.getAppointments().isEmpty()) {
					throw new Exception("XML file is empty. Cannot add appointments to the database. ");
				} else {
					System.out.println(dentist.getAppointments().toString());
					// Store the dentist in the database
								// Create entity manager
								factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
								em = factory.createEntityManager();
								em.getTransaction().begin();
								em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
								em.getTransaction().commit();

								// Create a transaction
								EntityTransaction tx1 = em.getTransaction();

								// Start transaction
								tx1.begin();

								// Persist
								//ASSUMING THAT THE APPOINTMENTS ARE NOT ALREADY IN THE DATABASE!!
								for (Appointment a : dentist.getAppointments()) {
									em.persist(a);
								}
						        
								// End transaction
								tx1.commit();
							}	
	}
	
	
	//XML to HTML
	/**
	 * Simple transformation method. You can use it in your project.
	 * @param sourcePath - Absolute path to source xml file.
	 * @param xsltPath - Absolute path to xslt file.
	 * @param resultDir - Directory where you want to put resulting files.
	 */
	public void simpleTransform(String sourcePath, String xsltPath,String resultDir) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//PRUEBAS
	public static void main(String[] args) {
		
		Dentists dentist=new Dentists();
		XMLManager xmlManager= new XMLManager();
		try {
			xmlManager.java2XmlDentist(dentist); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			xmlManager.xml2JavaDentist();  
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			xmlManager.xml2JavaAppointment(); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}


