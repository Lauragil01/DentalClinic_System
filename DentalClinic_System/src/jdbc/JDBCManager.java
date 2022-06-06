package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.jpa.JPAUserManager;
import dentalClinic.pojos.Allergy;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

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
				+ " userId INTEGER REFERENCES users(id) ON DELETE CASCADE"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE treatments ("
				+ "	treatmentId INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	name	TEXT NOT NULL,"
				+ "	diagnosis	TEXT NOT NULL,"
				+ "	startDate DATE NOT NULL,"
				+ "	finishDate DATE NOT NULL,"
				+ " patient_treat INTEGER REFERENCES patients(patientId) ON DELETE CASCADE"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE dentists ("
				+ "	dentistId 		INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	name	TEXT NOT NULL,"
				+ "	surname	TEXT NOT NULL,"
				+ "	turn	TEXT NOT NULL,"
				+ "	specialty	TEXT NOT NULL,"
				+ " userId INTEGER REFERENCES users(id) ON DELETE CASCADE"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE appointments ("
				+ "	appointmentId	INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	type	TEXT NOT NULL,"
				+ " date DATE NOT NULL,"
				+ " time DATE NOT NULL,"
				+ " dentist TEXT NOT NULL,"
				+ " duration INTEGER NOT NULL,"
				+ "	patient_app INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
				+ "	dentist_app INTEGER REFERENCES dentists(dentistId) ON DELETE CASCADE"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE medications ("
				+ "	medicationId	INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	name	TEXT NOT NULL,"
				+ " dosis INTEGER NOT NULL,"
				+ " treatment_med INTEGER REFERENCES treatments(treatmentId) ON DELETE CASCADE"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE allergies ("
				+ "	allergyId	INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	name	TEXT NOT NULL"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE patient_dentist ("
				+ "	patient_pd INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
				+ "	dentist_pd INTEGER REFERENCES dentists(dentistId) ON DELETE CASCADE,"
				+ "	PRIMARY KEY(patient_pd,dentist_pd)\r\n"
				+ ");";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE patient_allergy ("
				+ "	patient_pa INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
				+ "	allergy_pa INTEGER REFERENCES allergies(allergyId) ON DELETE CASCADE,"
				+ "	PRIMARY KEY(patient_pa,allergy_pa)\r\n"
				+ ");";
				stmt.executeUpdate(sql);
			
			} catch (SQLException e) {
				// Do not complain if tables already exist
				if (!e.getMessage().contains("already exists")) {
					e.printStackTrace();
				}
			}
		}
		public Integer getLastId() throws SQLException{
			String query = "SELECT last_insert_rowid() AS lastId";
			PreparedStatement p = c.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			Integer lastId = rs.getInt("lastId");
			p.close();
			return lastId;	
		}

}