package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jdbc.JDBCManager;

public class Menu {

	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	public static void main(String[] args) {
		
		System.out.println("Welcome to the Dental Clinic System");
		try {
			do {
				System.out.println("1.");
				System.out.println("2. ");
				System.out.println("0. Exit");
				int choice= Integer.parseInt(reader.readLine());
				switch(choice) {
				case 1:
					//call method
					break;
				case 2:
					// call method
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
}
	private static void createAccount(){
		System.out.println("Email");
		String email = reader.readLine();
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
		if(checkEmail(email)== ) {
			System.out.println("The email is used");			
		}else{
			
			
			newUser();
		};
	}
	private static void login() {
		System.out.print("Username:");
		String email = reader.readLine;
		System.out.print("Password:");
		String password = reader.readLine;
		User u= checkPassword (username,password);
		if(u == null) {
			System.out.print("Incorrect username or password");
		} else {
			//complete
		}
	}
		