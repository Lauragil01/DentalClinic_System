package jdbc;

import java.sql.Date;
import java.sql.ResultSet;
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
import hospital.jdbc.JDBCManager;
import hospital.pojos.Vet;

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
		        // para que las listas de treatments, dentists y appointments del paciente no estén vacías 
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

	/*@Override
	public List<Patient> seePatients() {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM patients";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String gender = rs.getString("gender");
				Date birthDate = rs.getDate("birthDate");
				String address = rs.getString("address");
				String bloodType = rs.getString("bloodType");
				String allergies = rs.getString("allergies");
				String background = rs.getString("background");
				Patient p = new Patient(id, name, surname, gender, birthDate, address, 
						bloodType, allergies, background);
				patients.add(p);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}*/

	@Override
	public void addTreatment(Treatment t, int patientId) {
		// TODO Auto-generated method stub

	}

	/*@Override
	public List<Treatment> seeTreatments() {
		List<Treatment> treatments = new ArrayList<Treatment>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM treatments";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String diagnosis = rs.getString("diagnosis");
				Integer consultDuration = rs.getInt("consultDuration");
				Date startDate = rs.getDate("startDate");
				Date finishDate = rs.getDate("finishDate");
				Treatment t = new Treatment(id, diagnosis, consultDuration,
						startDate, finishDate);
				treatments.add(t);;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treatments;
	}*/

	@Override
	public void deleteTreatment(Treatment t, int patientId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editTreatment(Treatment t, int patientId) {
		// TODO Auto-generated method stub

	}

	/*@Override
	public List<Medication> seeMedication() {
		List<Medication> medications = new ArrayList<Medication>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM medications";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				Integer dosis = rs.getInt("dosis");
				Treatment treatment = rs.getTreatment("treatment"); // ?
				Medication m = new Medication(id, name, dosis,treatment);
				medications.add(m);;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medications;
	}*/

	@Override
	public void addMedication(Medication m, int patientId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMedication(Medication m, int patientId) {
		// TODO Auto-generated method stub

	}

	/*@Override
	public List<Appointment> seeAppointments() {
		List<Appointment> appointments = new ArrayList<Appointment>();
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT * FROM appointments";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Date date = rs.getDate("date");
				String type = rs.getString("type");
				Integer duration = rs.getInt("duration");
				Time time = rs.getTime("time");
				Dentist dentist = rs.getDentist("dentist"); // ?
				Patient patient = rs.getPatient("patient"); // ?
				Appointment a = new Appointment(id, date, type, duration, time, dentist, patient);
				appointments.add(a);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointments;
	}*/
	
	public List<Treatment> getTreatmentsOfPatient(int patientId){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Dentist> getDentistsOfPatient(int patientId){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Appointment> getAppointmentsOfPatient(int patientId){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Patient> getPatientsOfDentist(int patientId){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Medication> getMedicationOfPatient(int patientId){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Appointment> getAppointmentsOfDentist(int dentistId){
		// TODO Auto-generated method stub
		return null;
	}


}
