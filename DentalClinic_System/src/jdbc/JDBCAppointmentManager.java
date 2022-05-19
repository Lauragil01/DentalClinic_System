package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.AppointmentManager;
import dentalClinic.pojos.Allergy;
import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Patient;

public class JDBCAppointmentManager implements AppointmentManager {
	private JDBCManager manager;
	private JDBCDentistManager dentistmanager;

	public JDBCAppointmentManager(JDBCManager m, JDBCDentistManager dentistmanager) {
		this.manager = m;
		this.dentistmanager = dentistmanager;
	}
	
	public JDBCAppointmentManager(JDBCManager m) {
		this.manager = m;
	}
	
	@Override
	public void addAppointment(Appointment a) throws SQLException {
		String sql = "INSERT INTO appointments (date, type, time, duration, patientId) VALUES (?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setDate(1, a.getDate());
		prep.setString(2, a.getType());
		prep.setTime(3, a.getTime());
		prep.setInt(4, a.getDuration());
		prep.setInt(5, a.getPatient().getId());
		prep.executeUpdate();
		prep.close();	
	}
	
	//public void assignAppointmentTo_PatientDentist(int patientId, int dentistId) throws SQLException{}

	@Override
	public List<Appointment> listofAppointments(int patientId) throws SQLException {
		String sql = "SELECT * FROM appointments WHERE patientId=? ORDER BY date ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
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
		String sql = "DELETE FROM appointment WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, appointmentId);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Appointment> searchAppointmentbyDate(Date date) throws SQLException {
		Appointment a = null;
		Dentist dentist = null;
		String sql = "SELECT * FROM appointments WHERE date = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setDate(1, date);
		ResultSet rs = prep.executeQuery();
		List<Appointment> appointments = new ArrayList<Appointment>();
		while(rs.next()){ 
			int id = rs.getInt("id");
			String type = rs.getString("type");
			int duration = rs.getInt("duration");
			Time time = rs.getTime("time");
			int dentistId = rs.getInt("dentistId");
			dentist = dentistmanager.searchDentistById(dentistId);
			a = new Appointment(id, type, duration, time, dentist);
			appointments.add(a);
		}
		prep.close();
		rs.close();
		return appointments;		
	}
	
	@Override
	public Appointment searchAppointmentById(int appointmentId) throws SQLException {
		Appointment a = null; 
		Dentist dentist = null;
		String sql = "SELECT * FROM appointments WHERE date = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, appointmentId);
		ResultSet rs = prep.executeQuery();
		while(rs.next()){ 
			Date date = rs.getDate("date");
			String type = rs.getString("type");
			int duration = rs.getInt("duration");
			Time time = rs.getTime("time");
			int dentistId = rs.getInt("dentistId");
			dentist = dentistmanager.searchDentistById(dentistId);
			a = new Appointment(date, type, duration, time, dentist);			
		}
		prep.close();
		rs.close();
		return a;
	}
	
	public static void main(String[] args) {
		JDBCManager manager = new JDBCManager();
		JDBCAppointmentManager appointmentManager = new JDBCAppointmentManager(manager);
		
		List<Allergy> allergies = new ArrayList<Allergy>();
		Patient p = new Patient("a", "b", "m", "c", "0", "k", allergies);
		Dentist d = new Dentist("Paco", "Garc�a", "tarde", "ortodoncia");
		Appointment app = new Appointment("consult", 5, d, p);
		
		
		try {
			//appointmentManager.addAppointment(app); 
			//appointmentManager.listofAppointments(p.getId()); 
			//appointmentManager.deleteAppointment(app.getId()); 
			appointmentManager.searchAppointmentById(app.getId());
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
