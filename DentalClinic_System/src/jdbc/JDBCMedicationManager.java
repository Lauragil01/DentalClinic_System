package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.MedicationManager;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;

public class JDBCMedicationManager implements MedicationManager {
	private JDBCManager manager;

	public JDBCMedicationManager(JDBCManager m) {
		this.manager = m;
	}
	@Override
	public void addMedication(Medication m) throws SQLException {
		String sql = "INSERT INTO medications (id, name, dosis) VALUES (?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, m.getId());
		prep.setString(2, m.getName());
		prep.setInt(3, m.getDosis());		
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Medication> listofMedications(int treatmentId) throws SQLException {
		String sql = "SELECT * FROM medications WHERE treatmentId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, treatmentId);
		ResultSet rs = prep.executeQuery(sql);
		List <Medication> medications = new ArrayList<Medication>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int dosis = rs.getInt("dosis");
			Medication medication = new Medication(id,name,dosis);
			medications.add(medication);		
		}
		prep.close();
		rs.close();
		return medications;
	}
	
	@Override
	public Medication searchMedicationById(int id) throws SQLException {
		Medication medication = null;
		String sql = "SELECT * FROM medications WHERE id = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1,id);
		ResultSet rs = prep.executeQuery();
		List <Medication> medications = new ArrayList<Medication>();
		while(rs.next()){
			String name = rs.getString("name");
			int dosis = rs.getInt("dosis");
			medication = new Medication(id,name,dosis);
		}
		rs.close();	
		return medication;
	}

	@Override
	public List<Medication> searchMedicationbyName(String name) throws SQLException {
		Medication m  = null;
		String sql = "SELECT * FROM medications WHERE name LIKE ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,"%" + name + "%");
		ResultSet rs = prep.executeQuery();
		List <Medication> medications = new ArrayList<Medication>();
		while(rs.next()){
			int id = rs.getInt("id");
			int dosis = rs.getInt("dosis");
			m = new Medication(id,name,dosis);
			medications.add(m);
		}
		rs.close();	
		return medications;
	}
	
	@Override
	public void deleteMedication(int medicationId) {
		try {
			String sql = "DELETE FROM medications WHERE id = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, medicationId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void editMedicationsName(String mName) throws SQLException {
		String sql = "UPDATE medication SET name=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mName);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editMedicationsDosis(String mDosis) throws SQLException {
		String sql = "UPDATE medication SET dosis=?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mDosis);
		prep.executeUpdate();
		prep.close();
	}

}
