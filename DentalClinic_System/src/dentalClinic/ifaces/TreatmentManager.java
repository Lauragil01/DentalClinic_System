package dentalClinic.ifaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Treatment;

public interface TreatmentManager {
	
	public void addTreatment (Treatment t, int patientId) throws SQLException;

	public List<Treatment> listofTreatments (int patientId) throws SQLException, Exception;
	
	public List<Treatment> searchTreatmentbyName (String name) throws SQLException;

	public void assign_Treatment (int treatmentId, int patientId)throws SQLException;
	
	public void deleteTreatment (int treatmentId);
	
	public Treatment searchTreatmentById (int treatmentId) throws SQLException;
	
	public void editTreatmentsStartDate (Date start) throws SQLException;
	
	public void editTreatmentsFinishDate (Date finish) throws SQLException;

	

}
