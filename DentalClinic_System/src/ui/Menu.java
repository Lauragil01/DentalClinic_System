package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import dentalClinic.pojos.*;
import jdbc.JDBCDentistManager;
import jdbc.JDBCManager;
import jdbc.JDBCPatientManager;
import pojos.users.Role;
import pojos.users.User;
import dentalClinic.jpa.*;

public class Menu {

	public static JPAUserManager userManager;
	public static JDBCPatientManager patientManager;
	public static JDBCDentistManager dentistManager;
	
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	public static void main(String[] args) {
		userManager = new JPAUserManager();
		
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
		System.out.println("Email");
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
	
	public static void changePassword() {
		
	}

	
	public static void dentistMenu(Integer dentistId) throws Exception {
		Dentist dentist = new Dentist(dentistManager.getDentistByUserId(dentistId));
	}
	
	public static void patientMenu(Integer patientId) throws Exception {
		Patient patient = new Patient(patientManager.getPatientByUserId(0));
	}
}


	

	
	
	
