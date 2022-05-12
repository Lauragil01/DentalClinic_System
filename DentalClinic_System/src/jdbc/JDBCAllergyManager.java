package jdbc;

import java.sql.SQLException;
import java.util.List;

import dentalClinic.ifaces.AllergyManager;
import dentalClinic.pojos.Allergy;

public class JDBCAllergyManager implements AllergyManager {

	@Override
	public void addAllergy(Allergy a) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Allergy> getAllergiesFromPatient(int patientId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assign_Allergy(int allergyId, int patientId) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllergy(int allergyId) throws SQLException {
		// TODO Auto-generated method stub

	}

}
