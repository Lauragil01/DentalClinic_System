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
	
	public void setDentistManager(JDBCDentistManager dentistmanager) {
		this.dentistmanager = dentistmanager;
	}

	@Override
	public void addAppointment(Appointment a, int dentistId) throws SQLException { // checked
		String sql = "INSERT INTO appointments (date, time, duration, dentist_app) VALUES (?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setDate(1, a.getDate());
		prep.setTime(2, a.getTime());
		prep.setInt(3, a.getDuration());
		prep.setInt(4, dentistId);
		prep.executeUpdate();
		prep.close();	
	}
	
	@Override
	public List<Appointment> listofAppointments(int dentistId, int patientId) throws SQLException {
		Appointment a = null;
		String sql = "SELECT * FROM appointments WHERE ";
		String sql2 = null;
		PreparedStatement prep = null;
		if (dentistId == 0) {
			sql2 = "patient_app = ?";
			String SQL = sql + sql2;
			prep = manager.getConnection().prepareStatement(SQL);
			prep.setInt(1, patientId);
		}
		else if (patientId == 0) {
			sql2 = "dentist_app = ?";
			String SQL = sql + sql2;
			prep = manager.getConnection().prepareStatement(SQL);
			prep.setInt(1, dentistId);
			
		}
		else {
			sql2 = "patient_app = ? AND dentist_app = ?";
			String SQL = sql + sql2;
			prep = manager.getConnection().prepareStatement(SQL);
			prep.setInt(1, patientId);
			prep.setInt(2, dentistId);
			
		}
		ResultSet rs = prep.executeQuery();
		List <Appointment> appointments = new ArrayList<Appointment>();
		while (rs.next()) {
			int id = rs.getInt("appointmentId");
			Date date = rs.getDate("date");
			String type = rs.getString ("type");
			int duration = rs.getInt("duration");
			Time time = rs.getTime("time");
			a = new Appointment(id,date,type,duration,time);
			appointments.add(a);
			
		}
		prep.close();
		rs.close();
		return appointments;
	}

	@Override
	public void deleteAppointment(int appointmentId) throws SQLException { // no funciona
		String sql = "UPDATE appointments SET patient_app = NULL WHERE appointmentId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, appointmentId); 
		prep.executeUpdate();
		prep.close();
		
		sql = "UPDATE appointments SET type = NULL WHERE appointmentId = ?";
		PreparedStatement prep2 = manager.getConnection().prepareStatement(sql);
		prep2.setInt(1, appointmentId); 
		prep2.executeUpdate();
		prep2.close();
	}

	@Override
	public List<Appointment> searchFreeAppointmentsByDate(Date date) throws SQLException {
		Appointment a = null;
		Dentist dentist = null;
		String sql = "SELECT * FROM appointments WHERE date = ? AND patient_app IS NULL";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setDate(1, date);
		ResultSet rs = prep.executeQuery();
		List<Appointment> appointments = new ArrayList<Appointment>();
		while(rs.next()){ 
			int id = rs.getInt("appointmentId");
			int duration = rs.getInt("duration");
			Time time = rs.getTime("time");
			int dentistId = rs.getInt("dentist_app");
			dentist = dentistmanager.searchDentistById(dentistId);
			a = new Appointment(id, date, duration, time, dentist);
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
		String sql = "SELECT * FROM appointments WHERE appointmentId = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, appointmentId);
		ResultSet rs = prep.executeQuery();
		while(rs.next()){ 
			int id = rs.getInt("appointmentId");
			Date date = rs.getDate("date");
			int duration = rs.getInt("duration");
			Time time = rs.getTime("time");
			int dentistId = rs.getInt("dentist_app");
			dentist = dentistmanager.searchDentistById(dentistId);
			a = new Appointment(id, date, duration, time, dentist);			
		}
		prep.close();
		rs.close();
		return a;
	}
}
