package dentalClinic.ifaces;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;
import dentalClinic.pojos.Dentist;


public interface DentistManager {
	
	//Search patients
	public void addDentist (Dentist d) throws SQLException; 
	
	//Add a treatment
	public void addTreatment (Treatment t, int patientId) throws SQLException;
	
	public void addAppointment (Appointment a) throws SQLException;
	public void addMedication (Medication m) throws SQLException;
	
	//See all patients from a dentist
	public Patient searchPatientById(int patientId);
	/*//See list of patients
	public List<Patient> seePatients();*/
	
	public List<Appointment> seeAppointments(int dentistId)throws SQLException;
	
	/*//See list of treatments
	public List<Treatment> seeTreatments();*/

	
	public void editTreatment (Treatment t, int patientId) throws SQLException;
	public void editMedication(Medication t)throws SQLException;
	
	//Delete treatment
	public void deleteTreatment (Treatment t, int patientId);
	public void deleteMedication (Medication m, int patientId);
	
	//See medication
	public List<Medication> seeMedication ();

	/*//See appointments
	public List<Appointment> seeAppointments();*/
	public List<Treatment> getTreatmentsOfPatient(int patientId);
	public List<Dentist> getDentistsOfPatient(int patientId);
	public List<Appointment> getAppointmentsOfPatient(int patientId);
	public List<Appointment> getAppointmentsOfDentist(int dentistId);
	public List<Medication> getMedicationOfPatient(int patientId);
	public List<Patient> getPatientsOfDentist(int dentistId);

}
