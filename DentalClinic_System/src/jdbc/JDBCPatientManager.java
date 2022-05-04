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
		
	// para que las listas de treatments, dentists y appointments del paciente no est�n vac�as 
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
	public List<Treatment> listofTreatments (int patientId) throws SQLException, Exception {
		String sql = "SELECT * FROM treatments WHERE patientId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
		List <Treatment> treatments = new ArrayList<Treatment>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String diagnosis = rs.getString("diagnosis");
			int duration = rs.getInt("duration");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			Treatment treatment = new Treatment(id,diagnosis,duration,startDate,finishDate);
			treatments.add(treatment);		
		}
		prep.close();
		rs.close();
		return treatments;
	}
	@Override
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
	
	@Override
	public List<Medication> listofMedications (int treatmentId) throws SQLException {
		String sql = "SELECT * FROM medications WHERE treatmentId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, treatmentId);
		ResultSet rs = prep.executeQuery(sql);
		List <Medication> medications = new ArrayList<Medication>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int dosis = rs.getInt("dosis");
			Medication medication = new Medication(id,name,dosis);
			medications.add(medication);		
		}
		prep.close();
		rs.close();
		return medications;
	}
	
	@Override
	public List<Appointment> listofAppointments(int patientId) throws SQLException {
		String sql = "SELECT * FROM appointments WHERE patientId=? ORDER BY date ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
		List <Appointment> appointments = new ArrayList<Appointment>();
		while (rs.next()) {
			int id = rs.getInt("id");
			Date date = rs.getDate("date");
			String type = rs.getString ("type");
			int duration = rs.getInt("duration");
			Time time = rs.getTime("time");
			Appointment appointment = new Appointment(id,date,type,duration,time);
			appointments.add(appointment);		
		}
		prep.close();
		rs.close();
		return appointments;
	}
	
	@Override
	public void deleteAppointment(int appointmentId) throws SQLException {
			String sql = "DELETE FROM appointment WHERE appointmentId = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, appointmentId);
			prep.executeUpdate();
			prep.close();
	}
	
	@Override
	public void assign_Dentist(int dentistId, int patientId) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void assign_Patient(int patientId, int dentistId) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void assign_Treatment(int treatmentId, int patientId) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void assign_Medication(int medicationId, int treatmentId) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}