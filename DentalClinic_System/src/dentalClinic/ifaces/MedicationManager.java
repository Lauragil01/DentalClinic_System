package dentalClinic.ifaces;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Medication;

public interface MedicationManager {
	
	public void addMedication (Medication m) throws SQLException;
	
	public List<Medication> listofMedications(int treatmentId)throws SQLException;
	
	public void assignTreatmentToMedication(int medicationId, int treatmentId) throws SQLException;
	
	public void editMedicationsName(String mName, int medicationId)throws SQLException;
	
	public void editMedicationsDosis(String mDosis, int medicationId)throws SQLException;
	
	public void deleteMedication (int id) throws SQLException;
	
	public Medication searchMedicationById (int medicationId) throws SQLException;
	
	public List <Medication> searchMedicationbyName (String name) throws SQLException;

}
