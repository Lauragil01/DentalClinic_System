package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
	
		private Connection c = null;
		
		
		public JDBCManager() {
			try {
				// Open database connection
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:./db/dentalClinic.db");
				c.createStatement().execute("PRAGMA foreign_keys=ON");
				System.out.println("Database connection opened.");
				// Create tables
				this.createTables();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Libraries not loaded");
			}
		}

		public void disconnect() {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public Connection getConnection() {
			return c;
		}
		
		private void createTables() {
			// Create Tables
			try {
			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE patients ("
			+ "	patientId	    INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "	name	TEXT NOT NULL,"
			+ " surname  TEXT NOT NULL,"
			+ "	gender	TEXT NOT NULL,"
			+ "	dob		DATE NOT NULL,"
			+ "	address	TEXT NOT NULL,"
			+ " bloodType TEXT NOT NULL,"
			+ " allergies TEXT,"
			+ " background TEXT,"
			+ " userId INTEGER REFERENCES patients(patientId) ON DELETE CASCADE)"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE treatments ("
			+ "	treatmentId INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "	name	TEXT NOT NULL,"
			+ "	diagnosis	TEXT NOT NULL,"
			+ "	duration TEXT NOT NULL,"
			+ "	startDate DATE NOT NULL,"
			+ "	finishDate DATE NOT NULL,"
			+ " patientId INTEGER REFERENCES patients(id) ON DELETE CASCADE"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE dentists ("
			+ "	dentistId 		INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "	name	TEXT NOT NULL,"
			+ "	surname	TEXT NOT NULL,"
			+ "	turn	TEXT NOT NULL,"
			+ "	specialty	TEXT NOT NULL,"
			+ " userId INTEGER REFERENCES dentists(dentistId) ON DELETE CASCADE)"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE appointments ("
			+ "	appointmentId	INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "	type	TEXT NOT NULL,"
			+ " date DATE NOT NULL,"
			+ " time DATE NOT NULL,"
			+ " dentist TEXT NOT NULL,"
			+ " duration INTEGER NOT NULL,"
			+ "	patientId INTEGER REFERENCES patients(id) ON DELETE CASCADE,"
			+ "	dentistId INTEGER REFERENCES dentists(id) ON DELETE CASCADE"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE medications ("
			+ "	medicationId	INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "	name	TEXT NOT NULL,"
			+ " dosis INTEGER NOT NULL,"
			+ " treatmentId INTEGER REFERENCES treatments(id) ON DELETE CASCADE"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE allergies ("
			+ "	allergiesId	INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "	name	TEXT NOT NULL"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE examines ("
			+ "	patientId INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
			+ "	dentistId INTEGER REFERENCES dentists(dentistId) ON DELETE CASCADE,"
			+ "	PRIMARY KEY(patientId,dentistId)\r\n"
			+ ");";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE patientsAllergies ("
			+ "	patientId INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
			+ "	allergyId INTEGER REFERENCES allergies(allergyId) ON DELETE CASCADE,"
			+ "	PRIMARY KEY(patientId,allergyId)\r\n"
			+ ");";
			stmt.executeUpdate(sql);
			
			} catch (SQLException e) {
				// Do not complain if tables already exist
				if (!e.getMessage().contains("already exists")) {
					e.printStackTrace();
				}
			}
		}

		public static void main(String[] args) {
			JDBCManager manager = new JDBCManager();
			manager.createTables();
		}
}