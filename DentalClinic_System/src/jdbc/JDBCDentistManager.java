package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.DentistManager;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

public class JDBCDentistManager implements DentistManager {
	private JDBCManager manager;

	public JDBCDentistManager(JDBCManager m) {
		this.manager = m;
	}

	@Override
	public Patient searchPatientById(int id) {
		Patient p = null;
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patients WHERE id="+id;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String gender = rs.getString("gender");
				Date birthDate = rs.getDate("birthDate");
				String address = rs.getString("address");
				String bloodType = rs.getString("bloodType");
				String allergies = rs.getString("allergies");
				String background = rs.getString("background");
				p = new Patient(name, surname, gender, birthDate, address, 
						bloodType, allergies, background);
		        // para que las listas de treatments, dentists y appointments del paciente no est�n vac�as 
				//p.setTreatments(this.getTreamentsOfPatient(name));
				//p.setDentists(this.getDentistsOfPatient(name));
				//p.setAppointments(this.getAppointmentsOfPatient(name));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	@Override
	public void addTreatment(Treatment t, int patientId) throws SQLException{ //PATIENT ID???
		String sql = "INSERT INTO treatments (id, diagnosis, duration, startDate, finishDate, patient_id) VALUES (?,?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, t.getId());
		prep.setString(2, t.getDiagnosis());			
		prep.setInt(3, t.getConsultDuration());			
		prep.setDate(4, t.getStartDate());
		prep.setDate(5, t.getFinishDate());
		prep.executeUpdate();
		prep.close();
	}
	@Override
	public void addMedication (Medication m) throws SQLException{ //TREATMENT ID???
		String sql = "INSERT INTO medications (id, name, dosis) VALUES (?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, m.getId());
		prep.setString(2, m.getName());
		prep.setInt(3, m.getDosis());		
		prep.executeUpdate();
		prep.close();	
	}
	
	@Override
	public void addAppointment(Appointment a) throws SQLException {
		String sql = "INSERT INTO appointments (id, date, type, time, duration) VALUES (?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, a.getId());
		prep.setDate(2, a.getDate());
		prep.setString(3, a.getType());
		prep.setTime(4, a.getTime());
		prep.setInt(5, a.getDuration());
		prep.executeUpdate();
		prep.close();	
	}
	@Override
	public void editTreatment(Treatment t, int patientId) throws SQLException { 
		String sql = "UPDATE treatment SET diagnosis=?" + " duration=?" + " finishDate=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, t.getDiagnosis());
		prep.setInt(2, t.getConsultDuration());
		prep.setDate(3, t.getFinishDate());
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void deleteTreatment(int treatmentId) {
		try {
			String sql = "DELETE FROM treatments WHERE treatmentId = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, treatmentId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMedication(int medicationId) {
		try {
			String sql = "DELETE FROM medications WHERE medicationId = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, medicationId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Dentist> getDentistsOfPatient(int patientId)throws SQLException{ 
		String sql = "SELECT * FROM dentist WHERE patientId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
		List <Dentist> dentists = new ArrayList<Dentist>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			Dentist dentist = new Dentist(id, name, surname, turn, specialty);
			dentists.add(dentist);		
		}
		prep.close();
		rs.close();
		return dentists;
	}
	
	public List<Patient> getPatientsOfDentist(int patientId)throws SQLException{
		String sql = "SELECT * FROM patients WHERE patientId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
		List <Patient> patients = new ArrayList<Patient>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String address = rs.getString("address");
			String bloodType = rs.getString("bloodType");
			String allergies = rs.getString("allergies");
			String background = rs.getString("background");
			Patient patient = new Patient(id, name, surname, gender, birthDate, address, 
					bloodType, allergies, background);
			patients.add(patient);		
		}
		prep.close();
		rs.close();
		return patients;
	}

	@Override
	public void addDentist(Dentist d) throws SQLException {
		String sql = "INSERT INTO dentists (id, name, surname, turn, specialty) VALUES (?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, d.getId());
		prep.setString(2, d.getName());
		prep.setString(3, d.getSurname());
		prep.setString(4,d.getTurn());
		prep.setString(5, d.getSpeciality());
		prep.executeUpdate();
		prep.close();	
	}

	@Override
	public void editMedicationsName(String mName) throws SQLException {
		String sql = "UPDATE medication SET name=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mName);
		prep.executeUpdate();
		prep.close();
	}
	
	public void editMedicationsDosis(String mDosis) throws SQLException {
		String sql = "UPDATE medication SET dosis=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mDosis);
		prep.executeUpdate();
		prep.close();
	}
	
	public void editMedicationsTreatment(String mTreatment) throws SQLException {
		String sql = "UPDATE medication SET treatment=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mTreatment);
		prep.executeUpdate();
		prep.close();
	}
}
