package dentalClinic.ifaces;

import java.util.List;

import dentalClinic.pojos.Appointment;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Treatment;

public interface PatientManager {
	//See list of treatments
	public List<Treatment> seeTreatments();
	//See appointments
	public List<Appointment> seeAppointments();
	//See medication
	public List<Medication> seeMedication ();
	//Make an appointment 
	
	//Delete an appointment 
	public void deleteAppointment(Appointment a);

}
