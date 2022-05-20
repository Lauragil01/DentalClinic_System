package dentalClinic.ifaces;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.pojos.Allergy;

public interface AllergyManager {
	
	public void addAllergy (Allergy a) throws SQLException;
	
	public List<Allergy> getAllergiesFromPatient (int patientId) throws SQLException;
	
	public void deleteAllergy (int allergyId) throws SQLException;
	
}
    //n hdhdh