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

		public static void main(String[] args) {
			JDBCManager manager = new JDBCManager();
			//manager.createTables();
			JDBCMedicationManager mm = new JDBCMedicationManager(manager);
			JDBCTreatmentManager tm = new JDBCTreatmentManager(manager);
			JDBCAppointmentManager ap = new JDBCAppointmentManager(manager);			
			JDBCAllergyManager am = new JDBCAllergyManager(manager);
			JDBCDentistManager dm = new JDBCDentistManager(manager,ap);
			JDBCPatientManager pm = new JDBCPatientManager(manager, tm, mm, ap, dm, am);
			dm.setPatientmanager(pm);
			
			Date date1 = new Date(2, 1, 2);
			Date date2 = new Date(2, 1, 2);
			Time time = new Time(1, 1, 1);
			JPAUserManager jpam = new JPAUserManager();
			
			Dentist d1 = new Dentist(1,"Paco", "Garcia", "tarde", "ortodoncia");
			Dentist d2 = new Dentist(2,"Juan", "Perez", "tarde", "ortodoncia");
			Dentist d3 = new Dentist(3,"Marta", "Garcia", "tarde", "endodoncista");
			Dentist d4 = new Dentist(4,"Laura", "Lopez", "mañana", "ortodoncia");
			
			Patient p1 = new Patient(1,"Alvaro", "Barrio", "m" ,date1 , "calle", "0", "k");
			Patient p2 = new Patient(2,"Carla", "Barrio", "m" ,date1 , "calle2", "0", "k");
			Patient p3 = new Patient(3,"Javier", "Rodriguez", "m" ,date2 , "calle3", "0", "k");
			Patient p4 = new Patient(4,"Alvaro", "Gomez", "m" ,date2 , "calle", "0", "k");
			Dentist prueba = new Dentist ();
			
			Treatment t = new Treatment(1,"Aparato","d",date1,date2);
			Treatment t2 = new Treatment(2,"Brakets","d",date2,date1);
			Treatment tprueba = null;
					
			Medication m = new Medication (1,"c", 4, t);
			Medication m2 = new Medication (2,"c2", 2, t);
			Medication search = null;
			
			Allergy a = new Allergy("polen");
			Allergy a2 = new Allergy(2, "gluten");
			Allergy a3 = new Allergy("peanuts");
			Allergy a4 = new Allergy(4, "cat hair");
			
			//Appointment ap1 = new Appointment(1, date2, "Consult", 30,null);
			Appointment ap2 = new Appointment(1, date1, "Consult 2", 30, time, d2);
			

			List<Medication> meds = new ArrayList<Medication>();
			List<Patient> patients = new ArrayList<Patient>();
			List<Treatment> treats = new ArrayList<Treatment>();
			List<Allergy> allergies = new ArrayList<Allergy>();
			List<Dentist> dentistsfound = new ArrayList<Dentist>();
			
			try {
				/*tm.addTreatment(t);
				mm.addMedication(m2);
				mm.assignTreatmentToMedication(m2.getId(), t.getId()); 
				System.out.println(mm.listofMedications(t.getId())); // funciona */
				
				
				/*pm.addPatient(p3);
				tm.addTreatment(t);
				tm.addTreatment(t2);
				tm.assignPatientToTreatment(p3.getId(), t.getId());
				tm.assignPatientToTreatment(p3.getId(), t2.getId());
				System.out.println(tm.listofTreatments(p3.getId())); // funciona*/
				
				
				/*pm.addPatient(p2);
				dm.addDentist(d1);
				dm.addDentist(d2);
				dm.assignDentistPatient(d1.getId(), p2.getId()); 
				dm.assignDentistPatient(d2.getId(), p2.getId());
				System.out.println(dm.getDentistsOfPatient(p2.getId())); // funciona*/
				
				/*dm.addDentist(d4);
				pm.addPatient(p3);
				pm.addPatient(p4);
				dm.assignDentistPatient(d4.getId(), p3.getId()); 
				dm.assignDentistPatient(d4.getId(), p4.getId());  
				System.out.println(pm.getPatientsOfDentist(d4.getId())); // funciona*/

				pm.addPatient(p3);
				am.addAllergy(a4);
				am.addAllergy(a2);
				am.assignAllergyPatient(a4.getAllergyId(), p3.getId());
				am.assignAllergyPatient(a2.getAllergyId(), p3.getId()); 
				System.out.println(am.getAllergiesFromPatient(p3.getId())); // NO funciona */

			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
}