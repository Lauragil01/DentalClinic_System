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
	
	public List<Dentist> listAllDentists() throws SQLException;
	
	public List<Dentist> searchDentistByName (String name, String surname) throws SQLException;
	
	public Dentist searchDentistById (int dentistId) throws SQLException;
	
	public void editDentistsName (String name) throws SQLException;
	
	public void editDentistSurname (String surname) throws SQLException;
	
	public void editDentistsTurn (String turn) throws SQLException;
}
