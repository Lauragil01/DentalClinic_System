package dentalClinic.xml.manager;

import java.io.File;
import java.util.*;
import javax.persistence.*;
import javax.xml.bind.*;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Dentists;


public class Xml2Java  {
	private static final String PERSISTENCE_PROVIDER = "dentalClinic-provider";
	private static EntityManagerFactory factory;
	private static EntityManager em;
	static Scanner sc = new Scanner(System.in);
	
	public static void xml2JavaAppointment() throws Exception {
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
								//ASSUMING THAT THE SHIFTS ARE NOT ALREADY IN THE DATABASE!!
								for (Appointment a : dentist.getAppointments()) {
									em.persist(a);
								}
						        
								// End transaction
								tx1.commit();
							}	
	}
	
	public static void xml2JavaDentist() throws Exception {
		//Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Dentist.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshall the XML document from a file
		File file = new File("./xmls/External-Dentist.xml");
		Dentists dentists=(Dentists)unmarshaller.unmarshal(file);
		
		if(dentists.getDentists().isEmpty()) {
			throw new Exception("XML file is empty. Cannot add dentist to the database. ");
		} else {
			System.out.println(dentists.getDentists().toString());
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
						//ASSUMING THAT THE WORKERS ARE NOT ALREADY IN THE DATABASE!!
						for (Dentist d : dentists.getDentists()) {
							em.persist(d);
						}
				        
						// End transaction
						tx1.commit();
					}	
		}
}
