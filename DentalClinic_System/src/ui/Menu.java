package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dentalClinic.pojos.*;
import jdbc.JDBCAllergyManager;
import jdbc.JDBCAppointmentManager;
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
			if(userManager.checkEmail(email)==null) {
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
		} while (a==0);
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
		}else if (u.getRole().getName().equals("dentist")){
			dentistMenu(u.getId());
		}else if (u.getRole().getName().equals("patient")){
			patientMenu(u.getId()); 
		}
	}
	
	private static void changePassword() {
		sc = new Scanner (System.in);
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
				System.out.println("The password does not match");
			}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	}
	

	
	private static void dentistMenu(User user) throws Exception {
		sc = new Scanner (System.in);
		Dentist dentist = new Dentist(dentistManager.getDentistByUserId(user.getId()));
		do{ 
			System.out.println("1. See my patients");
			System.out.println("2. Consult my appointments");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());;
			
			switch (choice) {
			case 1:
				PatientsList(dentist.getId());
				break;
					
			case 2:
				DentistAppointments(dentist);
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
	
	private static void PatientsList(Integer dentistId) throws SQLException {
		List<Patient> patients = new ArrayList<Patient>();
		patients = patientManager.getPatientsOfDentist(dentistId);
		System.out.println(patients);
		
	}
	
	private static void DentistAppointments (Dentist dentist) throws Exception {
		
		ListofAppointments();
		System.out.println("0. Return");
		int choice = Integer.parseInt(reader.readLine());;
		
		while(choice != 0) {
			System.out.println("Please, choose a valid option.");
			choice= Integer.parseInt(reader.readLine());
		}
			return;
		
	}

	private static void patientMenu(int userId) throws Exception {
		sc = new Scanner (System.in);
		Patient patient = new Patient(patientManager.getPatientByUserId(userId));
		do{ 
			System.out.println("1. See my profile");
			System.out.println("2. Consult my appointments");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());;
			switch (choice) {
			case 1:
				PatientProfile(patient);
				break;				
			case 2:
				PatientAppointments(patient);	
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
	
	private static void PatientProfile(Patient patient) throws Exception {
		sc = new Scanner (System.in);
		do{ 
			
			PatientInformation(patient);
			System.out.println("1. Modify profile information");
			System.out.println("2. Consult my treatments");
			System.out.println("3. Add an allergy");
			System.out.println("4. Edit an allergy");
			System.out.println("5. Delete an allergy");
			System.out.println("0. Return");
			int choice = Integer.parseInt(reader.readLine());;
		
			switch (choice) {
			case 1:
				ModifyInformation(patient);
				break;				
			case 2:
				ConsultTreatments(patient);	
				break;
			case 3:
				AddAllergy(patient);
				break;
			case 4:
				EditAllergy(patient);
				break;
			case 5:
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
	
	private static void PatientInformation (Patient p) throws Exception {
		System.out.println(p);
		System.out.println("Patient " + p.getName() + " " +p.getSurname() + " allergies: ");
		System.out.println(p.getAllergies());	
	}

	private static void ModifyInformation(Patient patient) {
		// TODO Auto-generated method stub
		
	}

	private static void ConsultTreatments(Patient patient) {
		System.out.println("Patient " + patient.getName() + " " + patient.getSurname() + " treatments :");
		System.out.println(patient.getTreatments());
		//Misma pantalla para patients y dentists???
	}
	
	private static void ListofTreatments(Integer id) {
		// TODO Auto-generated method stub
		
	}

	private static void AddAllergy(Patient patient) {
		// TODO Auto-generated method stub
		
	}
	
	private static void EditAllergy(Patient patient) {
		// TODO Auto-generated method stub
		
	}
	
	private static void DeleteAllergy(Patient patient) {
		// TODO Auto-generated method stub
		
	}

	private static void PatientAppointments(Patient patient) throws Exception{
		sc = new Scanner (System.in);
		do{ 		
			ListofAppointments(patient.getId());
			System.out.println("1. Add an appointment");
			System.out.println("2. Delete an appointment");
			System.out.println("0. Return");
			int choice = Integer.parseInt(reader.readLine());;
		
			while(choice > 2 || choice < 0) {
				System.out.println("Please, choose a valid option.");
				choice= Integer.parseInt(reader.readLine());
			}
			switch (choice) {
			case 1:
				AddAppointment(patient);
				break;				
			case 2:
				DeleteAppointment(patient);	
				break;				
			case 0:
				return;			
			}
		}
		while(true);
	}
	
	private static void ListofAppointments() throws Exception{
		if()
		appointmentManager.listofAppointments_Dentist(id);
	}
	
	private static void AddAppointment(Patient p) throws Exception{
		// TODO Auto-generated method stub
	}
	
	private static void DeleteAppointment(Patient p) throws Exception{
		// TODO Auto-generated method stub
	}
	

}


	

	
	
	
