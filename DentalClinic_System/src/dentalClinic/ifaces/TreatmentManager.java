package dentalClinic.ifaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Treatment;

public interface TreatmentManager {
	
	public void addTreatment (Treatment t) throws SQLException;
	
	public void assignPatientToTreatment(int patientId, int treatmentId) throws SQLException;

	public List<Treatment> listofTreatments (int patientId) throws SQLException, Exception;
	
	public List<Treatment> searchTreatmentbyName (String name) throws SQLException;
	
	public void deleteTreatment (int treatmentId) throws SQLException;
	
	public Treatment searchTreatmentById (int treatmentId) throws SQLException;
	
	public void editTreatmentsName (String name, int treatmentId) throws SQLException;
	
	public void editTreatmentsStartDate (Date start, int treatmentId) throws SQLException;
	
	public void editTreatmentsFinishDate (Date finish, int treatmentId) throws SQLException;

	

}
