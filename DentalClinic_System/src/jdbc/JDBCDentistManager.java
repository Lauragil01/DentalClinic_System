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

	public JDBCDentistManager(JDBCManager m, JDBCPatientManager pm, JDBCAppointmentManager am) {
		this.manager = m;
		this.patientmanager = pm;
		this.appointmentmanager = am;
	}
	
	public JDBCDentistManager(JDBCManager m) {
		this.manager = m;
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
		String sql = "SELECT * from dentists AS d JOIN examines AS e ON d.id = e.dentistId WHERE e.patientId=?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
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
	public void assignDentistPatient(int dentistId, int patientId) throws SQLException { //No funciona
	String sql = "INSERT INTO examines (dentistId, patientId) VALUES (?,?)";
	PreparedStatement prep = manager.getConnection().prepareStatement(sql);
	prep.setInt(1, dentistId);
	prep.setInt(2, patientId);
	prep.executeUpdate();
	prep.close();
		
	}

	@Override
	public List<Dentist> searchDentistByName(String name, String surname) throws SQLException { //Checked
		Dentist d = null;
		String sql = "SELECT * FROM dentists WHERE name = ? AND surname = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.setString(2, surname);
		ResultSet rs = prep.executeQuery();
		List<Dentist> dentists = new ArrayList<Dentist>();
		while(rs.next()){ 
			int id = rs.getInt("id");
			String turn = rs.getString("turn");
			String specialty = rs.getString("specialty");
			d = new Dentist(id,name,surname, turn, specialty);
			if (patientmanager.getPatientsOfDentist(id)!= null) {
				d.setPatients(patientmanager.getPatientsOfDentist(id));
			}
			if (appointmentmanager.listofAppointments(id)!=null){
				d.setAppointments(appointmentmanager.listofAppointments(id));
			}
			dentists.add(d);		
		}
		prep.close();
		rs.close();
		return dentists;
	}

	@Override
	public Dentist searchDentistById(int dentistId) throws SQLException { //Checked
		Dentist d = null;
		String sql = "SELECT * FROM dentists WHERE id = ?";
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
	public void editDentistsName(String name, int dentistId) throws SQLException {
		String sql = "UPDATE dentists SET name= ? WHERE id = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editDentistSurname(String surname, int dentistId) throws SQLException {
		String sql = "UPDATE dentists SET surname=? WHERE id = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editDentistsTurn(String turn, int dentistId) throws SQLException {
		String sql = "UPDATE dentists SET turn=? WHERE id = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, turn);
		prep.executeUpdate();
		prep.close();
	}
	
	public static void main(String[] args) {
		JDBCManager manager = new JDBCManager();
		JDBCPatientManager patientmanager = new JDBCPatientManager(manager);
		JDBCAppointmentManager appointmentmanager = new JDBCAppointmentManager(manager);
		
		JDBCDentistManager dentistManager = new JDBCDentistManager(manager, patientmanager,appointmentmanager );
		
		Date date = new Date(2, 1, 2);
		
		Dentist d = new Dentist(1,"Paco", "Garcia", "tarde", "ortodoncia");
		Dentist d2 = new Dentist(2,"Juan", "Perez", "tarde", "ortodoncia");
		Dentist d3 = new Dentist(3,"Marta", "Garcia", "tarde", "endodoncista");
		Dentist d4 = new Dentist(4,"Laura", "Lopez", "mañana", "ortodoncia");
		
		Patient p = new Patient(1,"Alvaro", "Barrio", "m" ,date , "calle", "0", "k");
		Patient p2 = new Patient(2,"Carla", "Barrio", "m" ,date , "calle2", "0", "k");
		Patient p3 = new Patient(3,"Javier", "Rodriguez", "m" ,date , "calle3", "0", "k");
		Patient p4 = new Patient(4,"Alvaro", "Gomez", "m" ,date , "calle", "0", "k");
		Dentist prueba = new Dentist ();
	
		//List<Allergy> allergies = new ArrayList<Allergy>();
		//Allergy a = new Allergy("polen", p);*/
		//List<Dentist> dentistsfound = new ArrayList<Dentist>();
		
		try {
			//prueba = dentistManager.searchDentistById(d.getId());
			//System.out.print(prueba);
			//dentistManager.editDentistsName(null);
			//dentistManager.assignDentistPatient(d.getId(), p.getId());	
			//dentistsfound = dentistManager.searchDentistByName("Paco", "Garcia");
			//dentistManager.getDentistsOfPatient(p2.getId());
			//dentistManager.assignDentistPatient(d.getId(), p2.getId());
			//dentistManager.searchDentistByName(d.getName(), d.getSurname());
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
