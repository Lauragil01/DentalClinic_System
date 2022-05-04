package jdbc;


import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import dentalClinic.ifaces.PatientManager;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;


public class JDBCPatientManager implements PatientManager {
	private JDBCManager manager;

	public JDBCPatientManager(JDBCManager m) {
		this.manager = m;
	}
	@Override
	public void addPatient(Patient p) throws SQLException{
		String sql = "INSERT INTO patients (name, surname, gender, dob, address, bloodType, allergies, background) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, p.getName());
		prep.setString(2, p.getSurname());
		prep.setString(3,p.getGender());
		prep.setDate(4, p.getBithDate());
		prep.setString(5, p.getAddress());
		prep.setString(6, p.getBloodType());
		prep.setString(7, p.getAllergies());
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
			String allergies = rs.getString("allergies");
			String background = rs.getString("background");
			p = new Patient(name, surname, gender, birthDate, address, bloodType, allergies, background);
			p.setTreatments(this.listofTreatments(id));
			p.setAppointments(this.listofAppointments(id));
			p.setDentists(this.getDentistsOfPatient(id));
		}
	// para que las listas de treatments, dentists y appointments del paciente no esten vacias 
		rs.close();
		prep.close();
		return p;
	}
	
	@Override
	public void editPatient(Patient p) throws SQLException {
			String sql = "UPDATE patient SET name=?" + " surname=?" + " address=?" + " allergies=?" + "background=?";
			PreparedStatement prep= manager.getConnection().prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getSurname());
			prep.setString(3, p.getAddress());
			prep.setString(4, p.getAllergies());
			prep.setString(5, p.getBlackground());
			prep.executeUpdate();
			prep.close();
	}
		
	@Override
	public void assign_Patient(int patientId, int dentistId) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Patient> searchPatientbyName(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Patient> listAllPatients() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Patient> getPatientsOfDentist(int dentistId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}