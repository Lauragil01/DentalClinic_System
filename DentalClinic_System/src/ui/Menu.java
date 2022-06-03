package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import dentalClinic.pojos.*;
import jdbc.JDBCAllergyManager;
import jdbc.JDBCAppointmentManager;
import dentalClinic.xml.manager.Java2Xml;
import dentalClinic.xml.manager.Xml2Html;
import dentalClinic.xml.manager.Xml2Java;
import jdbc.JDBCDentistManager;
import jdbc.JDBCManager;
import jdbc.JDBCMedicationManager;
import jdbc.JDBCPatientManager;
import jdbc.JDBCTreatmentManager;
import dentalClinic.jpa.*;

public class Menu {

	public static JPAUserManager userManager;
	public static JDBCManager manager;
	public static JDBCPatientManager patientManager;
	public static JDBCDentistManager dentistManager;
	public static JDBCTreatmentManager treatmentManager;
	public static JDBCAppointmentManager appointmentManager;
	public static JDBCAllergyManager allergyManager;
	public static JDBCMedicationManager medicationManager;
	public static Appointment appointment;
	static Scanner sc = new Scanner(System.in);
	
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	public static void main(String[] args) {
		userManager = new JPAUserManager();
		medicationManager = new JDBCMedicationManager(manager);
		treatmentManager = new JDBCTreatmentManager(manager);
		appointmentManager = new JDBCAppointmentManager(manager);			
		allergyManager = new JDBCAllergyManager(manager);
		dentistManager = new JDBCDentistManager(manager,appointmentManager);
		JDBCPatientManager pm = new JDBCPatientManager(manager, treatmentManager, medicationManager, appointmentManager, dentistManager, allergyManager);
		dentistManager.setPatientmanager(pm);
		
		System.out.println("Welcome to the Dental Clinic System");
		try {
			do {
				System.out.println("1.Create account");
				System.out.println("2. Login ");
				System.out.println("3. Change password ");
				System.out.println("0. Exit");
				int choice= Integer.parseInt(reader.readLine());
				switch(choice) {
				case 1:
					createAccount();
					break;
				case 2:
					login();
					break;
				case 3:
					changePassword();
					break;
				case 0: 
					System.exit(0);
				default:
					break;
				}
			} while(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createAccount() throws IOException, NoSuchAlgorithmException, SQLException {
		System.out.println("Email:");
		String email = reader.readLine();
		int a=0;
		do {
			if(userManager.checkEmail(email)==null) { //no seria !=null?
				System.out.println("Choose another email:");
			}else {
				a=1;
			}
			
		}while(a==0);
		System.out.println("Password:");
		
		String password = reader.readLine();
		System.out.println(userManager.getRoles());
		System.out.println("Choose your role ID: "); 
		Integer id = null;
		int z=0;
		do {
			try {
				id = Integer.parseInt(reader.readLine());
				z=1;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Not a valid role id. Try again.");
			}
		} while (a==0); // no seria z==0
		Role role = userManager.getRole(id);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(email, hash, role);
		userManager.newUser(user);
		if(user.getRole().getName().equalsIgnoreCase("patient")) {
			patientManager.LinkPatientUser(user.getId(), id); 
		} else if(user.getRole().getName().equalsIgnoreCase("dentist")) {
			dentistManager.LinkDentistUser(user.getId(), id); 
		} 
			
	};
	
	private static void login() throws Exception{
		System.out.print("Email:");
		String email = reader.readLine();
		System.out.print("Password:");
		String password = reader.readLine();
		User u = userManager.checkPassword(email,password);
		if(u == null) {
			System.out.print("Incorrect email or password");
		}else if (u.getRole().getName().equalsIgnoreCase("dentist")){
			dentistMenu(u);
		}else if (u.getRole().getName().equalsIgnoreCase("patient")){
			patientMenu(u); 
		}
	}
	
	private static void changePassword() {
		//sc = new Scanner (System.in);
		try{
			System.out.println("Username:");
			String username = reader.readLine();
			System.out.println("Password:");
			String password = reader.readLine();
			User user = userManager.checkPassword(username, password);
			System.out.println("Introduce the new password: ");
			String newPassword1 = reader.readLine();
			System.out.println("Confirm your new password: ");
			String newPassword2 = reader.readLine();
			if(newPassword1.equals(newPassword2)) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(newPassword1.getBytes());
				byte[] hash = md.digest();
				userManager.updateUser(user, hash);
				System.out.println("Password updated");
			} else {
				System.out.println("The passwords do not match");
			}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	}
	

	
	private static void dentistMenu(User user) throws Exception {
		sc = new Scanner (System.in);
		Dentist dentist = dentistManager.getDentistByUserId(user.getId());
		
		System.out.println("Mr/Mrs" + dentist.getName()+ " "+ dentist.getSurname()+ "profile :");
		System.out.println(dentist);
		
		do{ 
			
			System.out.println("1. Modify my profile");
			System.out.println("2. Check my patients");
			System.out.println("3. Consult my appointments");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());;
			
			switch (choice) {
			case 1:
				ModifyDentistInfo(dentist);
				break;
				
			case 2:
				PatientsofDentist(dentist.getId()); 
				break;
					
			case 3:
				ListofAppointments(user);
				break;
					
			case 0:
				System.exit(0);
				
			default:
				System.out.println("Please, choose a valid option.");
				break;
					
			}
		}
		while(true);

	}
	
	private static void ModifyDentistInfo(Dentist dentist) throws IOException, SQLException {
		do {	
			System.out.println("1.Name");
			System.out.println("2.Surname");
			System.out.println("3.Specialty");
			System.out.println("0.Return");
			
			int choice = Integer.parseInt(reader.readLine());;
		
			switch (choice) {
				case 1:{
					System.out.println("New name:");
					String newName= reader.readLine();
					dentistManager.editDentistsName(newName, dentist.getId());	
					break;
				}
				case 2:{
					System.out.println("New Surname:");
					String newSurname= reader.readLine();
					dentistManager.editDentistSurname(newSurname, dentist.getId());	
					break;
				}
				case 3:{
					System.out.println("New specialty:");
					String newSpecialty= reader.readLine();
					dentistManager.editDentistsSpecialty(newSpecialty, dentist.getId());	
					break;
				}
				default:
					System.out.println("Please, choose a valid option.");
					break;	
				case 0:
					return;
		    }
		} while(true);
		
	}
	//MODIFICAR PARA QUE SALGAN LOS APPOINTMENTS LIBRES A LA HORA DE AÑADIR
	private static void ListofAppointments(User u) throws Exception {
		Patient patient = null;
		Dentist dentist = null;
		if(u.getRole().getName().equalsIgnoreCase("patient")){
			patient = patientManager.getPatientByUserId(u.getId());
			appointmentManager.listofAppointments(0, patient.getId());
		}
		
		if(u.getRole().getName().equalsIgnoreCase("dentist")){
			dentist = dentistManager.getDentistByUserId(u.getId());
			appointmentManager.listofAppointments(dentist.getId(), 0);
		}
		do {
			System.out.println("1. Add appointment");
			System.out.println("2. Delete appointment");
			System.out.println("0. Return");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:{
					if (dentist == null)
					AddAppointment(patient, null);
					if (patient == null)
						AddAppointment(null, dentist);
					break;
				}
						
				case 2:{
					System.out.println("Introduce the ID of the appointment you want to delete");
					DeleteAppointment();
					break;
				}
				case 0:
					return;
				default:
					System.out.println("Please, choose a valid option.");
					break;
				
			}
		}
		while(true);	
	}	
	
	private static void AddAppointment(Patient patient, Dentist dentist) {
		// TODO Auto-generated method stub
		
	}
	private static void DeleteAppointment() throws Exception{
		System.out.println("Introduce the ID of the appointment you want to delete: ");
		int id = Integer.parseInt(reader.readLine());
		int a = 1;
		try{
			appointmentManager.deleteAppointment(id);
		}
		catch(SQLException e) {
			a = 0;
		}
		while(a == 0) {
			try {
				System.out.println("Introduce a valid ID: ");
				id = Integer.parseInt(reader.readLine());
				appointmentManager.deleteAppointment(id);
			}
			catch (SQLException e2) {
				a = 0;
			}
		}
	}

	private static void PatientsofDentist(Integer dentistId) throws Exception {
		
		System.out.println("---List of my patients---");
		List<Patient> patients = new ArrayList<Patient>();
		patients = patientManager.getPatientsOfDentist(dentistId);
		System.out.println(patients);	
		
		do{ 
			System.out.println("1. Search for a patient by Id");
			System.out.println("2. Search for a patient by name");
			System.out.println("3. Search for a patient by surname");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			Patient patient = null;
			switch (choice) {
				case 1:{
					System.out.println("Introduce the Id of the patient:");
					int patientId = Integer.parseInt(reader.readLine());
					patient = patientManager.searchPatientById(patientId);
					break;
				}
						
				case 2:{
					System.out.println("Introduce the name of the patient:");
					String name = reader.readLine();
					patients = patientManager.searchPatientbyName(name);
					break;
				}
				case 3:{
					System.out.println("Introduce the surname of the patient:");
					String surname = reader.readLine();
					patients = patientManager.searchPatientbySurname(surname);
					break;
				}		
				case 0:
					System.exit(0);
					
				default:
					System.out.println("Please, choose a valid option.");
					break;
			}
			//Only if you search a patient by id
			if (patient != null) {
				PatientProfile(patient, 1); //choosedentist is 1
			}
			
		}
		while(true);
		
		
	}
	private static void PatientProfile(Patient patient, int dentistoptions) throws Exception {
		sc = new Scanner (System.in);
		do{ 
			
			System.out.println(patient);
			System.out.println("Mr/Mrs " + patient.getName() + " " +patient.getSurname() + " allergies: ");
			System.out.println(patient.getAllergies());	
			
			System.out.println("1. Modify profile information");
			System.out.println("2. Consult treatments");
			if (dentistoptions == 1) {
				System.out.println("3. Add an allergy");
				System.out.println("4. Delete an allergy");
			}
			System.out.println("0. Return");
			int choice = Integer.parseInt(reader.readLine());
			
			switch (choice) {
			case 1:
				ModifyPatientInfo(patient, dentistoptions);
				break;				
			case 2:
				ConsultTreatments(patient, dentistoptions);
				break;	
			case 3:
				if (dentistoptions == 1) 
				AddAllergy(patient);
				break;
			case 4:
				if (dentistoptions == 1) 
				DeleteAllergy(patient);
				break;
			case 0:
				return;
			default:
				System.out.println("Please, choose a valid option.");
				break;
					
			}
		}
		while(true);
	}
	// dentistoptions is for options that only dentists can do
	private static void ConsultTreatments(Patient patient, int dentistoptions) throws Exception {
		Treatment treatment = null;
		List<Treatment> treatments = new ArrayList<Treatment>();
		List<Treatment> treats = null;
		System.out.println("Patient " + patient.getName() + " " + patient.getSurname() + " treatments :");
		System.out.println(patient.getTreatments());
		
		do{
			System.out.println("1. Search for a treatment by its id");
			System.out.println("2. Search for a treatment by its name");
		
			if(dentistoptions == 1) {
				System.out.println("3. Add treatment");
				System.out.println("4. Delete treatment");
			}	
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:{
				System.out.println("Introduce the ID of the treatment: ");
				int id = Integer.parseInt(reader.readLine());
				treatment = treatmentManager.searchTreatmentById(id);
				if (treatment != null){
					treatment.setId(id);
					System.out.println(treatment);
					MedicationsofTreatment(treatment, dentistoptions);
				}
				else {
					System.out.println("The ID introduced doesn't correspond to any treatment. ");
				}
			    break;	
			}
			case 2:{
				System.out.println("Introduce the name of the treatment: ");
				String name = reader.readLine();
				treats = treatmentManager.searchTreatmentbyName(name);
				if (treats != null){
					System.out.println(treats);
				}
				else {
					System.out.println("The name introduced doesn't correspond to any treatment. ");
				}
				break;
			}	
			case 3:
				if(dentistoptions == 1)
				AddTreatment(patient);
				break;
						
			case 4:
				if(dentistoptions == 1)
				DeleteTreatment();
				break;
			case 0:
				return;
			default:
				System.out.println("Please, choose a valid option.");
				break;
			}
		}
		while(true);
		}
		
	private static void MedicationsofTreatment(Treatment treatment, int dentistoptions) throws SQLException, NumberFormatException, IOException {
		do {
			Medication medication = null;
			List<Medication> meds = null;
			System.out.println(medicationManager.listofMedications(treatment.getId()));
			System.out.println("1. Search for a mediation by ID");
			System.out.println("2. Search for a medication by Name");
			if (dentistoptions == 1) {
				System.out.println("3. Add medication");
				System.out.println("4. Delete medication");
			}
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:{
				System.out.println("Introduce the ID of the medication: ");
				int id = Integer.parseInt(reader.readLine());
				medication = medicationManager.searchMedicationById(id);
				if (medication != null){
					System.out.println(medication);
				}
				else {
					System.out.println("The ID introduced doesn't correspond to any medication. ");
				}
				break;
			}
			case 2:{
				System.out.println("Introduce the name of the medication: ");
				String name = reader.readLine();
				meds = medicationManager.searchMedicationbyName(name);
				if (meds != null){
					System.out.println(meds);
				}
				else {
					System.out.println("The name introduced doesn't correspond to any medication. ");
				}
				break;
			}	
			case 3:
				if(dentistoptions == 1)
				AddMedication(treatment);
				break;
						
			case 4:
				if(dentistoptions == 1)
				DeleteMedication();
				break;
			case 0:
				return;
			default:
				System.out.println("Please, choose a valid option.");
				break;
			}
		}
		while(true);	
	}
	
	private static void AddMedication(Treatment treatment) throws IOException, SQLException {
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Dosis: ");
		int dosis = Integer.parseInt(reader.readLine());
		Medication med = new Medication(name,dosis,treatment);
		medicationManager.addMedication(med);
		
	}

	private static void DeleteMedication() throws NumberFormatException, IOException {
		System.out.println("Introduce the ID of the medication you want to delete: ");
		int id = Integer.parseInt(reader.readLine());
		int a = 1;
		try{
			medicationManager.deleteMedication(id);
		}
		catch(SQLException e) {
			a = 0;
		}
		while(a == 0) {
			try {
				System.out.println("Introduce a valid ID: ");
				id = Integer.parseInt(reader.readLine());
				medicationManager.deleteMedication(id);
			}
			catch (SQLException e2) {
				a = 0;
			}
		}	
		
	}

	
	private static void AddTreatment(Patient p) throws IOException, Exception {
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Diagnosis: ");
		String diagnosis = reader.readLine();
		System.out.println("Start date year-month-day: ");
		Date startDate = null;
		try {
			startDate = Date.valueOf(sc.next());
		}
		catch (Exception e) {
			startDate = null;
		}
		while(startDate == null) {
			System.out.println("Please introduce a valid date: ");
			try {
			startDate = Date.valueOf(sc.next());
			}
			catch (Exception e1) {
				startDate = null;
			}
		}
		System.out.println("Finish date year-month-day: ");
		Date finishDate = null;
		try{
			finishDate = Date.valueOf(sc.next());
		}
		catch(Exception e2) {
			finishDate = null;
		}
		while(finishDate == null || startDate.after(finishDate)) { //la startDate no puede ser despues que la finishDate
			System.out.println("Please introduce a valid date: ");
			try {
			finishDate = Date.valueOf(sc.next());
			}
			catch (Exception e1) {
				finishDate = null;
			}
		}
		Treatment treat = new Treatment(name, diagnosis, startDate, finishDate, p);
		treatmentManager.addTreatment(treat);
	}
	
	private static void DeleteTreatment() throws NumberFormatException, IOException {
		System.out.println("Introduce the ID of the treatment you want to delete: ");
		int id = Integer.parseInt(reader.readLine());
		int a = 1;
		try{
			treatmentManager.deleteTreatment(id);
		}
		catch(SQLException e) {
			a = 0;
		}
		while(a == 0) {
			try {
				System.out.println("Introduce a valid ID: ");
				id = Integer.parseInt(reader.readLine());
				treatmentManager.deleteTreatment(id);
			}
			catch (SQLException e2) {
				a = 0;
			}
		}	
	}

	private static void ModifyPatientInfo(Patient patient, int dentistoptions) throws NumberFormatException, IOException, SQLException {
		do {	
			System.out.println("1.Name");
			System.out.println("2.Surname");
			System.out.println("3.Gender");
			System.out.println("4.Adress");
			if(dentistoptions == 1) System.out.println("5.Background");
			System.out.println("0.Return");
			
			int choice = Integer.parseInt(reader.readLine());;
		
			switch (choice) {
				case 1:{
					System.out.println("New name:");
					String newName= reader.readLine();
					patientManager.editPatientsName(newName, patient.getId());		
					break;
				}
				case 2:{
					System.out.println("New Surname:");
					String newSurname= reader.readLine();
					patientManager.editPatientsSurname(newSurname, patient.getId());	
					break;
				}
				case 3:{
					System.out.println("New gender:");
					String newGender= reader.readLine();
					patientManager.editPatientsGender(newGender, patient.getId());	
					break;
				}
				case 4:{
					System.out.println("New adress:");
					String newAdress= reader.readLine();
					patientManager.editPatientsAddress(newAdress, patient.getId());
					break;
				}
				case 5:{
					if (dentistoptions == 1) {
						System.out.println("New background:");
						String newBackground= reader.readLine();
						patientManager.editPatientsBackground(newBackground, patient.getId());
					}
					break;
				}
				default:
					System.out.println("Please, choose a valid option.");
					break;	
				case 0:
					return;
		    }
		} while(true);
		
	}


	private static void patientMenu(User user) throws Exception {
		sc = new Scanner (System.in);
		Patient patient = new Patient(patientManager.getPatientByUserId(user.getId()));
		do{ 
			System.out.println("1. See my profile");
			System.out.println("2. Consult my appointments");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());;
			switch (choice) {
			case 1:
				PatientProfile(patient, 0); //dentistoptions = 0 so a patient can't do dentist things
				break;				
			case 2:
				ListofAppointments(user);
				break;				
			case 0:
				System.exit(0);
			default:
				System.out.println("Please, choose a valid option.");
				break;
					
			}
		}
		while(true);
		
	}
	private static void AddAllergy(Patient patient) throws IOException, SQLException {
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Diagnosis: ");
		Allergy allergy = new Allergy(name);
		//ASSIGN ???
		allergyManager.addAllergy(allergy);
	}

	private static void DeleteAllergy(Patient patient) throws NumberFormatException, IOException {
		System.out.println("Introduce the ID of the allergy you want to delete: ");
		int id = Integer.parseInt(reader.readLine());
		int a = 1;
		try{
			allergyManager.deleteAllergy(id);;
		}
		catch(SQLException e) {
			a = 0;
		}
		while(a == 0) {
			try {
				System.out.println("Introduce a valid ID: ");
				id = Integer.parseInt(reader.readLine());
				allergyManager.deleteAllergy(id);
			}
			catch (SQLException e2) {
				a = 0;
			}
		}
	}	
	

	//METHODS FROM XML
	
		public static void appointmentToXml(Dentist dentist) throws Exception {
			Java2Xml.java2XmlAppointment(dentist);
		}
		
		public static void xmlToAppointment(Dentist dentist) {
			try {
				Xml2Java.xml2JavaAppointment(); 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void appointmentXmlToHtml () {
			Xml2Html.simpleTransform("./xmls/External-Appointment.xml", "./xmls/Appointment-Style.xslt", "./xmls/Appointment.html");
		}
		
		public static void xmlToDentist() {
			try {
				Xml2Java.xml2JavaDentist();  
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void dentistToXml() throws JAXBException {
			try {
				Java2Xml.java2XmlDentist(); 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void dentistXmlToHtml () {
			Xml2Html.simpleTransform("./xmls/External-Dentist.xml", "./xmls/Dentist-Style.xslt", "./xmls/Dentist.html");
		}
	
	
	
}


	

	
	
	
