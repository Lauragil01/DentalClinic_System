package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.AllergyManager;
import dentalClinic.pojos.*;

public class JDBCAllergyManager implements AllergyManager {
	private JDBCManager manager;

	public JDBCAllergyManager(JDBCManager m) {
		this.manager = m;
	}

	@Override
	public void addAllergy(Allergy a) throws SQLException { //Checked
		String sql = "INSERT INTO allergies (name) VALUES (?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, a.getName());			
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Allergy> getAllergiesFromPatient(int patientId) throws SQLException {
		String sql = "SELECT * FROM allergies AS a JOIN patient_allergy AS pa ON a.allergyId = pa.allergy_pa WHERE pa.patient_pa=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
		List <Allergy> allergies = new ArrayList<Allergy>();
		while (rs.next()) {
			int id = rs.getInt("allergyId");
			String name = rs.getString("name");
			Allergy allergy = new Allergy(id,name);
			allergies.add(allergy);		
		}
		prep.close();
		rs.close();
		return allergies;
	}

	@Override
	public void deleteAllergy(int allergyId) throws SQLException { //Checked
		String sql = "DELETE FROM allergies WHERE allergyId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, allergyId);
		prep.executeUpdate();
		prep.close();
	}
	
	@Override
	public void assignAllergyPatient(int allergyId, int patientId) throws SQLException { 
		String sql = "INSERT INTO patient_allergy (patient_pa, allergy_pa) VALUES (?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		prep.setInt(2, allergyId);
		prep.executeUpdate();
		prep.close();
	}
}
