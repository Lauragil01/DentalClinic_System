package jdbc;


import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import dentalClinic.ifaces.PatientManager;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

import jdbc.JDBCTreatmentManager;
import jdbc.JDBCAppointmentManager;
import jdbc.JDBCMedicationManager;



public class JDBCPatientManager implements PatientManager {
	private JDBCManager manager;
	private JDBCTreatmentManager treatmentmanager;
	private JDBCMedicationManager medicationmanager;
	private JDBCAppointmentManager appointmentmanager;
	private JDBCDentistManager dentistmanager;
	private JDBCAllergyManager allergymanager;
	

	public JDBCPatientManager(JDBCManager m, JDBCTreatmentManager t, JDBCMedicationManager m2, 
			JDBCAppointmentManager a, JDBCDentistManager d, JDBCAllergyManager am) {
		this.manager = m;
		this.treatmentmanager = t;
		this.medicationmanager = m2;
		this.appointmentmanager = a;
		this.dentistmanager = d;
		this.allergymanager = am;		
	}
	
	public JDBCPatientManager(JDBCManager m) {
		this.manager = m;
	}
	
	@Override
	public void addPatient(Patient p) throws SQLException{
		String sql = "INSERT INTO patients (name, surname, gender, dob, address, bloodType, background) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, p.getName());
		prep.setString(2, p.getSurname());
		prep.setString(3,p.getGender());
		prep.setDate(4,p.getBithDate());
		prep.setString(5, p.getAddress());
		prep.setString(6, p.getBloodType());
		prep.setString(7, p.getBlackground());
		prep.executeUpdate();
		prep.close();
	}
	
	@Override
	public Patient searchPatientById(int id) throws SQLException, Exception {
		Patient p = null;
		String sql = "SELECT * FROM patients WHERE id= ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, id);
		ResultSet rs = prep.executeQuery(sql);
		while (rs.next()) {
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String background = rs.getString("background");
			p = new Patient(id, name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(allergymanager.getAllergiesFromPatient(id));
			p.setTreatments(treatmentmanager.listofTreatments(id));
			p.setAppointments(appointmentmanager.listofAppointments(id));
			p.setDentists(dentistmanager.getDentistsOfPatient(id));
		}
		rs.close();
		prep.close();
		return p;
	}
			
	@Override
	public List<Patient> searchPatientbyName(String name) throws SQLException {
		Patient p = null;
		String sql = "SELECT * FROM patients WHERE name LIKE ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,"%" + name + "%");
		ResultSet rs = prep.executeQuery();
		List <Patient> patients = new ArrayList<Patient>();
		while(rs.next()){
			int id = rs.getInt("id");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String background = rs.getString("background");
			p= new Patient(id, name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(allergymanager.getAllergiesFromPatient(id));
		}
		rs.close();	
		return patients;
	}
	
	@Override
	public List<Patient> searchPatientbySurname(String surname) throws SQLException {
		Patient p = null;
		String sql = "SELECT * FROM patients WHERE surname LIKE ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,"%" + surname + "%");
		ResultSet rs = prep.executeQuery();
		List <Patient> patients = new ArrayList<Patient>();
		while(rs.next()){
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String background = rs.getString("background");
			p= new Patient(id, name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(allergymanager.getAllergiesFromPatient(id));
		}
		rs.close();	
		return patients;
	}
	
	@Override
	public List<Patient> getPatientsOfDentist(int dentistId) throws SQLException {
		Patient p = null;
		String sql = "SELECT * FROM patients AS p JOIN examines AS e ON p.id = e.patientId WHERE e.dentistId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, dentistId);
		ResultSet rs = prep.executeQuery();
		List <Patient> patients = new ArrayList<Patient>();
		while(rs.next()){
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String background = rs.getString("background");
			p= new Patient(id, name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(allergymanager.getAllergiesFromPatient(id));
		}
		rs.close();	
		return patients;
	}
	
	@Override
	public void editPatientsName(String name, int patientId) throws SQLException {
		String sql = "UPDATE patient SET name = ? WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editPatientsSurname(String surname, int patientId) throws SQLException {
		String sql = "UPDATE patient SET surname = ? WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
	}
	@Override
	public void editPatientsGender(String gender, int patientId) throws SQLException {
		String sql = "UPDATE patient SET gender = ? WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, gender);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
		
	}
	@Override
	public void editPatientsAddress(String address, int patientId) throws SQLException {
		String sql = "UPDATE patient SET address = ? WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, address);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
		
	}
	@Override
	public void editPatientsBackground(String background, int patientId) throws SQLException {
		String sql = "UPDATE patient SET background = ? WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, background);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();		
	}

	
}