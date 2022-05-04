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

	public void addTreatment (Treatment t, int patientId) throws SQLException;
	
	public void addAppointment (Appointment a) throws SQLException;
	public void addMedication (Medication m) throws SQLException;
	
	public Patient searchPatientById(int patientId);
	
	public void editTreatment (Treatment t, int patientId) throws SQLException;
	public void editMedicationsName(String mName)throws SQLException;
	public void editMedicationsDosis(String mDosis)throws SQLException;
	public void editMedicationsTreatment(String mTreatment)throws SQLException;
	
	public void deleteTreatment (int treatmentId);
	public void deleteMedication (int medicationId);

	public List<Dentist> getDentistsOfPatient(int patientId) throws SQLException;
	public List<Patient> getPatientsOfDentist(int dentistId) throws SQLException;
}
