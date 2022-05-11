package dentalClinic.ifaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Dentist;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

public interface PatientManager {
	
	public void addPatient (Patient p) throws SQLException; 
	
	public void assign_Patient (int patientId, int dentistId) throws SQLException;
		
	public List<Patient> searchPatientbyName (String name) throws SQLException;
		
	public List<Patient> listAllPatients() throws SQLException;
	
	public Patient searchPatientById(int patientId) throws SQLException, Exception;
	
	public List<Patient> getPatientsOfDentist(int dentistId) throws SQLException;
	
	public void editPatientsName (String name) throws SQLException;
	
	public void editPatientsSurname (String surname) throws SQLException;
	
	public void editPatientsGender (String gender) throws SQLException;
	
	public void editPatientsAddress (String address) throws SQLException;
	
	public void addAllergy (String allergy) throws SQLException;
	
	
	
	//public void editPatientsAllergies (String allergies) throws SQLException;
	
	//public void editPatientsBackground (String background) throws SQLException;
	
	
	
	
 }
