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

	public JDBCPatientManager(JDBCManager m, JDBCTreatmentManager t, JDBCMedicationManager m2, JDBCAppointmentManager a, JDBCDentistManager d) {
		this.manager = m;
		this.treatmentmanager = t;
		this.medicationmanager = m2;
		this.appointmentmanager = a;
		this.dentistmanager = d;
		
	}
	@Override
	public void addPatient(Patient p) throws SQLException{
		String sql = "INSERT INTO patients (name, surname, gender, dob, address, bloodType, background) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, p.getName());
		prep.setString(2, p.getSurname());
		prep.setString(3,p.getGender());
		prep.setDate(4, p.getBithDate());
		prep.setString(5, p.getAddress());
		prep.setString(6, p.getBloodType());
		prep.setString(8, p.getBlackground());
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
			p = new Patient(name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(this.listAllergies(id));
			p.setTreatments(treatmentmanager.listofTreatments(id));
			p.setAppointments(appointmentmanager.listofAppointments(id));
			p.setDentists(dentistmanager.getDentistsOfPatient(id));
		}
	// para que las listas de treatments, dentists y appointments del paciente no esten vacias 
		rs.close();
		prep.close();
		return p;
	}
			
	@Override
	public void assign_Patient(int patientId, int dentistId) throws SQLException {
		// TODO Auto-generated method stub
		
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
			int id = rs.getInt("patientId");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String background = rs.getString("background");
			p= new Patient(name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(this.listAllergies(id));
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
			int id = rs.getInt("patientId");
			String name = rs.getString("name");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String background = rs.getString("background");
			p= new Patient(name, surname, gender, birthDate, address, bloodType, background);
			p.setAllergies(this.listAllergies(id));
		}
		rs.close();	
		return patients;
	}
	
	@Override
	public List<Patient> getPatientsOfDentist(int dentistId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void editPatientsName(String name, int patientId) throws SQLException {
		String sql = "UPDATE patient SET name = ? WHERE patientId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editPatientsSurname(String surname, int patientId) throws SQLException {
		String sql = "UPDATE patient SET surname = ? WHERE patientId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
	}
	@Override
	public void editPatientsGender(String gender, int patientId) throws SQLException {
		String sql = "UPDATE patient SET gender = ? WHERE patientId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, gender);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
		
	}
	@Override
	public void editPatientsAddress(String address, int patientId) throws SQLException {
		String sql = "UPDATE patient SET address = ? WHERE patientId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, address);
		prep.setInt(2, patientId);
		prep.executeUpdate();
		prep.close();
		
	}
	@Override
	public void addAllergy(String allergy) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<String> listAllergies(int patientId) throws SQLException {
		String sql = "SELECT allergies FROM patients WHERE patientId=?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
		List <String> allergies = new ArrayList<String>();
		while (rs.next()) {
			String allergy = rs.getString("allergies");
			allergies.add(allergy);	
		}
		prep.close();
		rs.close();
		return allergies;
	}
	
}