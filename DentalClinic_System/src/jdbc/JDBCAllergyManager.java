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
	public void addAllergy(Allergy a) throws SQLException {
		String sql = "INSERT INTO allergies (id, name, patientId) VALUES (?,?,?)";
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
			int id = rs.getInt("id");
			String name = rs.getString("name");
			Allergy allergy = new Allergy(id,name);
			allergies.add(allergy);		
		}
		prep.close();
		rs.close();
		return allergies;
	}

	@Override
	public void deleteAllergy(int allergyId) throws SQLException {
		String sql = "DELETE FROM allergies WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, allergyId);
		prep.executeUpdate();
		prep.close();
	}
	
	public static void main(String[] args) {
		JDBCManager manager = new JDBCManager();
		JDBCAllergyManager allergyManager = new JDBCAllergyManager(manager);
		
		Patient p = new Patient(1, "a", "b", "m", "c", "0", "k");
		List<Allergy> allergies = new ArrayList<Allergy>();
		Allergy a = new Allergy(22, "polen", p);
		allergies.add(a);
		Patient p2 = new Patient(1, "a", "b", "m", "c", "0", "k", allergies);
		
		try {
			//allergyManager.addAllergy(a); // ERROR: abort due to constraint violation (FOREIGN KEY constraint failed)
			//allergyManager.getAllergiesFromPatient(p2.getId()); // ERROR: not implemented by SQLite JDBC driver
			allergyManager.deleteAllergy(a.getAllergyId());
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
