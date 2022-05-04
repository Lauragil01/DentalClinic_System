package jdbc;


import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import dentalClinic.ifaces.PatientManager;
import dentalClinic.pojos.Appointment;
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
	public void addTreatment(Treatment t) throws SQLException{ //PATIENT ID???
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
	public List<Treatment> seeTreatments (int patientId) throws SQLException, Exception {
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
	public List<Appointment> seeAppointments(Patient p) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Medication> seeMedications(Treatment t) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
	public void editTreatment(Treatment t) throws SQLException { 
		String sql = "UPDATE treatment SET diagnosis=?" + " duration=?" + " finishDate=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, t.getDiagnosis());
		prep.setInt(2, t.getConsultDuration());
		prep.setDate(3, t.getFinishDate());
		prep.executeUpdate();
		prep.close();
		
	}
	@Override
	public void editMedication(Medication t) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteAppointment(Appointment a) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void assign(int dentistId, int patientId) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}