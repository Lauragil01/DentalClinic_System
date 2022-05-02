package dentalClinic.ifaces;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

public interface PatientManager {
	
	//Adding a new patient
	public void addPatient (Patient p) throws SQLException; 
	
	public void addTreatment (Treatment t) throws SQLException;
	public void addAppointment (Appointment a) throws SQLException;
	public void addMedication (Medication m) throws SQLException;
	
	//See list of treatments
	public List<Treatment> seeTreatments(Patient p) throws SQLException, Exception;
	//See appointments
	public List<Appointment> seeAppointments(Patient p)throws SQLException;
	//See medication
	public List<Medication> seeMedications(Treatment t)throws SQLException;
	
	public void editPatient(Patient p) throws SQLException;
	public void editTreatment(Treatment t)throws SQLException;
	public void editMedication(Medication t)throws SQLException;
	
	//Delete an appointment 
	public void deleteAppointment(Appointment a)throws SQLException;
	
	//Assign a patient to a dentist 
	public void assign (int dentistId, int patientId)throws SQLException;
}
