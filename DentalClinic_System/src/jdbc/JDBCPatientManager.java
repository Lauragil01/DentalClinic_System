package jdbc;


import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
	@Override
	public void editPatientsName(String name) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editPatientsSurname(String surname) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editPatientsGender(String gender) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editPatientsAddress(String address) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addAllergy(String allergy) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}