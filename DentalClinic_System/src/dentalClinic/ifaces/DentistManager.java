package dentalClinic.ifaces;

import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;


public interface DentistManager {
	//Adding a new patient
	public void addPatient (Patient p); 
	//Search patients
	public List<Patient> searchPatientByName(Patient p);
	//See list of patients
	public List<Patient> seePatients();
	//Add a treatment
	public void addTreatment (Treatment t);
	//See list of treatments
	public List<Treatment> seeTreatments();
	//Delete treatment
	public void deleteTreatment (Treatment t);
	//Edit treatment
	public void editTreatment (Treatment t);
	//See medication
	public List<Medication> seeMedication ();
	//Add medication
	public void addMedication (Medication m);
	//Delete medication
	public void deleteMedication (Medication m);
	//See appointments
	public List<Appointment> seeAppointments();

}
