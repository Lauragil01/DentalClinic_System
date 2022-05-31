package dentalClinic.ifaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Appointment;

public interface AppointmentManager {
	
	public void addAppointment (Appointment a) throws SQLException;
	
	public List<Appointment> listofAppointments(int dentistId, int patientId) throws SQLException;
	
	public void deleteAppointment(int appointmentId) throws SQLException;
	
	public Appointment searchAppointmentById (int appointmentId)throws SQLException;

	public List<Appointment> searchAppointmentbyDate(Date date) throws SQLException;
	

}
