package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.AllergyManager;
import dentalClinic.pojos.Allergy;
import dentalClinic.pojos.Treatment;

public class JDBCAllergyManager implements AllergyManager {
	private JDBCManager manager;

	public JDBCAllergyManager(JDBCManager m) {
		this.manager = m;
	}

	@Override
	public void addAllergy(Allergy a) throws SQLException {
		String sql = "INSERT INTO allergies (allergyId, name, patientId) VALUES (?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, a.getAllergyId());
		prep.setString(2, a.getName());			
		prep.setInt(3, a.getPatient().getId());
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Allergy> getAllergiesFromPatient(int patientId) throws SQLException {
		String sql = "SELECT * FROM allergies WHERE patientId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
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
	public void assign_Allergy(int allergyId, int patientId) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAllergy(int allergyId) throws SQLException {
		String sql = "DELETE FROM allergies WHERE allergyId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, allergyId);
		prep.executeUpdate();
		prep.close();
	}
}
