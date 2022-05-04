package dentalClinic.ifaces;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;
import dentalClinic.pojos.Dentist;


public interface DentistManager {
	
	public void addDentist (Dentist d) throws SQLException; 
	
	public void assign_Dentist (int dentistId, int patientId)throws SQLException;
	
	public List<Dentist> getDentistsOfPatient(int patientId)throws SQLException;
	
	
}
