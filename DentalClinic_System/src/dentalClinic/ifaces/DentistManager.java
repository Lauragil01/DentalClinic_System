package dentalClinic.ifaces;

import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;
import jdbc.Dentist;


public interface DentistManager {
	
	//Search patients
	public Patient searchPatientById(int id);
	/*//See list of patients
	public List<Patient> seePatients();*/
	//Add a treatment
	public void addTreatment (Treatment t, int patientId);
	/*//See list of treatments
	public List<Treatment> seeTreatments();*/
	//Delete treatment
	public void deleteTreatment (Treatment t, int patientId);
	//Edit treatment
	public void editTreatment (Treatment t, int patientId);
	//See medication
	public List<Medication> seeMedication ();
	//Add medication
	public void addMedication (Medication m, int patientId);
	//Delete medication
	public void deleteMedication (Medication m, int patientId);
	/*//See appointments
	public List<Appointment> seeAppointments();*/
	public List<Treatment> getTreatmentsOfPatient(int patientId);
	public List<Dentist> getDentistsOfPatient(int patientId);
	public List<Appointment> getAppointmentsOfPatient(int patientId);
	public List<Appointment> getAppointmentsOfDentist(int dentistId);
	public List<Medication> getMedicationOfPatient(int patientId);
	public List<Patient> getPatientsOfDentist(int dentistId);

}
