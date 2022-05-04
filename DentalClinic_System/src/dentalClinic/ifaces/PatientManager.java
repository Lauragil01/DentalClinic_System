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
	
	//Adding a new patient
	public void addPatient (Patient p) throws SQLException; 
	
	//See list of treatments
	public List<Treatment> listofTreatments (int patientId) throws SQLException, Exception;
	//See appointments
	public List<Appointment> listofAppointments(int patientId)throws SQLException;
	//See medication
	public List<Medication> listofMedications(int treatmentId)throws SQLException;
	
	public void editPatient(Patient p) throws SQLException;
	
	//Delete an appointment 
	public void deleteAppointment(int appointmentId)throws SQLException;
	
	//Assign a patient to a dentist 
	public void assign_Dentist (int dentistId, int patientId)throws SQLException;
	public void assign_Patient (int patientId, int dentistId) throws SQLException;
	public void assign_Treatment (int treatmentId, int patientId)throws SQLException;
	public void assign_Medication (int medicationId, int treatmentId)throws SQLException;
	
	public List<Patient> searchPatientbyName (String name) throws SQLException;
	public List<Treatment> searchTreatmentbyName (String name) throws SQLException;
	public List <Medication> searchMedicationbyName (String name) throws SQLException;
	public List <Appointment> searchAppointmentbyDate (Date date) throws SQLException;
	
	public List <Patient> listAllPatients() throws SQLException;
	
	public Patient searchPatientById(int patientId) throws SQLException, Exception;
	public List<Dentist> getDentistsOfPatient(int patientId)throws SQLException;
	
	
 }
