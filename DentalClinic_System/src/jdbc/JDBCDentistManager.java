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
import dentalClinic.pojos.Allergy;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

public class JDBCDentistManager implements DentistManager {
	private JDBCManager manager;
	private JDBCPatientManager patientmanager;
	private JDBCAppointmentManager appointmentmanager;

	public JDBCDentistManager(JDBCManager m, JDBCAppointmentManager am) {
		this.manager = m;
		this.appointmentmanager = am;
	}
	

	public void setPatientmanager(JDBCPatientManager patientmanager) {
		this.patientmanager = patientmanager;
	}
	
	
	

	/*public List<Patient> getPatientsOfDentist(int patientId)throws SQLException{
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
			String background = rs.getString("background");
			Patient patient = new Patient(id, name, surname, gender, birthDate, address, 
					bloodType, background);
			patients.add(patient);		
		}
		prep.close();
		rs.close();
		return patients;
	}*/

	
	/*public int getPatientId (Dentist d) throws SQLException {
		String sql = "SELECT id FROM dentists WHERE name =? AND surname =? AND address =? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,d.getName());
		
	}*/
	
	
	@Override
	public void addDentist(Dentist d) throws SQLException { //Checked
		String sql = "INSERT INTO dentists (name, surname, turn, specialty) VALUES (?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, d.getName());
		prep.setString(2, d.getSurname());
		prep.setString(3,d.getTurn());
		prep.setString(4, d.getSpecialty());
		prep.executeUpdate();
		prep.close();	
	}
	
	@Override
	public List<Dentist> getDentistsOfPatient(int patientId) throws SQLException { 
		String sql = "SELECT * from dentists AS d JOIN patient_dentist AS pd ON d.dentistId = pd.dentist_pd WHERE pd.patient_pd=?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
		List <Dentist> dentists = new ArrayList<Dentist>();
		while (rs.next()) {
			int id = rs.getInt("dentistId");
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
	public void assignDentistPatient(int dentistId, int patientId) throws SQLException { //Checked
		String sql = "INSERT INTO patient_dentist (patient_pd, dentist_pd) VALUES (?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		prep.setInt(2, dentistId);
	prep.executeUpdate();
	prep.close();
		
	}

	@Override
	public List<Dentist> searchDentistByName(String name) throws SQLException { //Checked
		Dentist d = null;
		String sql = "SELECT * FROM dentists WHERE name = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		ResultSet rs = prep.executeQuery();
		List<Dentist> dentists = new ArrayList<Dentist>();
		while(rs.next()){ 
			int id = rs.getInt("dentistId");
			String surname = rs.getString("surname");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			
			d = new Dentist(id,name,surname, turn, specialty);
			d.setPatients(patientmanager.getPatientsOfDentist(id));
			d.setAppointments(appointmentmanager.listofAppointments_Dentist(id));
			dentists.add(d);		
		}
		prep.close();
		rs.close();
		return dentists;
	}
	
	@Override
	public List<Dentist> searchDentistBySurname(String surname) throws SQLException { //Checked
		Dentist d = null;
		String sql = "SELECT * FROM dentists WHERE surname = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		ResultSet rs = prep.executeQuery();
		List<Dentist> dentists = new ArrayList<Dentist>();
		while(rs.next()){ 
			int id = rs.getInt("dentistId");
			String name = rs.getString("name");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			d = new Dentist(id,name,surname, turn, specialty);
			d.setPatients(patientmanager.getPatientsOfDentist(id));
			d.setAppointments(appointmentmanager.listofAppointments_Dentist(id));
			dentists.add(d);		
		}
		prep.close();
		rs.close();
		return dentists;
	}

	@Override
	public Dentist searchDentistById(int dentistId) throws SQLException { //Checked
		Dentist d = null;
		String sql = "SELECT * FROM dentists WHERE dentistId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, dentistId);
		ResultSet rs = prep.executeQuery();
		while(rs.next()){ 
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			d = new Dentist(dentistId, name, surname, turn, specialty);
			d.setPatients(patientmanager.getPatientsOfDentist(dentistId));
			d.setAppointments(appointmentmanager.listofAppointments_Dentist(dentistId));		
		}
		prep.close();
		rs.close();
		return d;
	}

	@Override
	public void editDentistsName(String name, int dentistId) throws SQLException { //No funciona
		String sql = "UPDATE dentists SET name = ? WHERE dentistId = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editDentistSurname(String surname, int dentistId) throws SQLException { //No funciona
		String sql = "UPDATE dentists SET surname = ? WHERE dentistId = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editDentistsTurn(String turn, int dentistId) throws SQLException { //No funciona
		String sql = "UPDATE dentists SET turn=? WHERE dentistId = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, turn);
		prep.executeUpdate();
		prep.close();
	}
	
	@Override
	public void editDentistsSpecialty(String specialty, int dentistId) throws SQLException { //No funciona
		String sql = "UPDATE dentists SET specialty=? WHERE dentistId = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, specialty);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public Dentist getDentistByUserId(Integer userId) throws SQLException {
		String sql = "SELECT * FROM dentists WHERE userId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1,userId);
		ResultSet rs = prep.executeQuery();
		Dentist dentist = null;
		if(rs.next()){
			dentist = new Dentist (rs.getString("name"), rs.getString("surname"), 
					rs.getString("turn"), rs.getString("specialty")); 
		}
		prep.close();
		rs.close();
		return dentist;
	}


	@Override
	public void LinkDentistUser(int dentistId, int userId) throws SQLException {
		String sql = "UPDATE dentists SET userId = ? WHERE dentistId = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, userId);
		prep.setInt(2, dentistId);
		prep.executeUpdate();
		prep.close();
		
	}
	
}
