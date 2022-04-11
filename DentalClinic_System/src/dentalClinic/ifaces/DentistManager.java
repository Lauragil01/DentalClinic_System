package dentalClinic.ifaces;

import java.util.List;

import dentalClinic.pojos.Patient;


public interface DentistManager {
	//Adding a new patient
	public void addPatient (Patient p); 
	//Search patients
	public List<Patient> searchPatientByName(Patient p);
	

}
