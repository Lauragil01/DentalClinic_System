package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import db.pojos.Shift;
import db.pojos.Worker;
import dentalClinic.ifaces.DentistManager;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

public class JDBCDentistManager implements DentistManager {
	private JDBCManager manager;
	private JDBCPatientManager patientmanager;
	private JDBCAppointmentManager appointmentmanager;

	public JDBCDentistManager(JDBCManager m, JDBCPatientManager pm, JDBCAppointmentManager am) {
		this.manager = m;
		this.patientmanager = pm;
		this.appointmentmanager = am;
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
	public List<Dentist> getDentistsOfPatient(int patientId) throws SQLException {
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
	public void assign_Dentist(int dentistId, int patientId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Dentist> listAllDentists() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dentist> searchDentistByName(String name, String surname) throws SQLException {
		Dentist d = null;
		String sql = "SELECT * FROM dentist WHERE name = ? AND surname = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.setString(2, surname);
		ResultSet rs = prep.executeQuery();
		List<Dentist> dentists = new ArrayList<Dentist>();
		while(rs.next()){ 
			int id = rs.getInt("id");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			d = new Dentist(id, turn, specialty);
			d.setPatients(patientmanager.getPatientsOfDentist(id));
			d.setAppointments(appointmentmanager.listofAppointments(id));
			dentists.add(d);		
		}
		prep.close();
		rs.close();
		return dentists;
	}

	@Override
	public Dentist searchDentistById(int dentistId) throws SQLException {
		Dentist d = null;
		String sql = "SELECT * FROM dentist WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, dentistId);
		ResultSet rs = prep.executeQuery();
		while(rs.next()){ 
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			d = new Dentist(name, surname, turn, specialty);
			d.setPatients(patientmanager.getPatientsOfDentist(dentistId));
			d.setAppointments(appointmentmanager.listofAppointments(dentistId));		
		}
		prep.close();
		rs.close();
		return d;
	}

	@Override
	public void editDentistsName(String name) throws SQLException {
		String sql = "UPDATE dentist SET name=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editDentistSurname(String surname) throws SQLException {
		String sql = "UPDATE dentist SET surname=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editDentistsTurn(String turn) throws SQLException {
		String sql = "UPDATE dentist SET turn=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, turn);
		prep.executeUpdate();
		prep.close();
	}
}
