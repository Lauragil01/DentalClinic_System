package dentalClinic.xml.manager;

import java.io.File;
import java.util.*;
import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import dentalClinic.pojos.*;


public class Java2Xml {
	
	private static final String PERSISTENCE_PROVIDER = "dentalClinic-provider";
	private static EntityManagerFactory factory;
	private static EntityManager em;
	static Scanner sc = new Scanner(System.in);
	
	@SuppressWarnings("unchecked")
	public static void java2XmlAppointment(Dentist dentist) throws Exception {
			// Get the entity manager
			em = Persistence.createEntityManagerFactory("dentalClinic-provider").createEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
			em.getTransaction().commit();
					
			// Create the JAXBContext
			JAXBContext jaxbContext = JAXBContext.newInstance(Appointment.class);
			// Get the marshaller
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			// Pretty formatting
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			
			// Choose the worker's shift to turn into an XML
			Query q2 = em.createNativeQuery("SELECT * FROM appointments WHERE dentist_id = ?", Appointment.class);
			q2.setParameter(1, dentist.getId());
			List <Appointment> a = q2.getResultList();
			if(a.isEmpty()) {
				throw new Exception("No appointments");
			}else {
			Dentist d = new Dentist();
			d.getAppointments();
					
			// Use the Marshaller to marshal the Java object to a file
			File file = new File("./xmls/External-Appointment.xml");
			// Use the Marshaller to marshal the Java object to a file
			marshaller.marshal(d, file);
			// Printout
			marshaller.marshal(d, System.out);
			}
	}
	
	@SuppressWarnings("unchecked")
	public static void java2XmlDentist() throws Exception {
		
		// Get the entity manager
		em = Persistence.createEntityManagerFactory("dentalClinic-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
				
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Dentist.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		Query q2 = em.createNativeQuery("SELECT * FROM dentists", Dentist.class);
		List<Dentist> dentist = q2.getResultList();
		if(dentist.isEmpty()) {
			throw new Exception("No dentists");
		}else {
		List<Dentist> dentists = new ArrayList<Dentist>();
		dentists.addAll(dentist);
		System.out.println(dentist);		
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/External-Dentist.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(dentists, file);
		// Printout
		marshaller.marshal(dentists, System.out);
		}
	} 
}
