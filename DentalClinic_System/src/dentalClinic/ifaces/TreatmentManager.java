package dentalClinic.ifaces;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Treatment;

public interface TreatmentManager {
	
	public void addTreatment (Treatment t, int patientId) throws SQLException;

	public List<Treatment> listofTreatments (int patientId) throws SQLException, Exception;
	
	public List<Treatment> searchTreatmentbyName (String name) throws SQLException;

	public void assign_Treatment (int treatmentId, int patientId)throws SQLException;
	
	public void editTreatment (Treatment t, int patientId) throws SQLException;
	
	public void deleteTreatment (int treatmentId);

	

}
