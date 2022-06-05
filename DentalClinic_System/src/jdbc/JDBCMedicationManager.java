package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.MedicationManager;
import dentalClinic.pojos.Allergy;
import dentalClinic.pojos.Medication;
import dentalClinic.pojos.Patient;
import dentalClinic.pojos.Treatment;

public class JDBCMedicationManager implements MedicationManager {
	private JDBCManager manager;

	public JDBCMedicationManager(JDBCManager m) {
		this.manager = m;
	}
	@Override
	public void addMedication(Medication m) throws SQLException { //Checked
		String sql = "INSERT INTO medications (name, dosis, treatment_med) VALUES (?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, m.getName());
		prep.setInt(2, m.getDosis());
		if (m.getTreatment() == null) {
			prep.setNull(3, java.sql.Types.INTEGER);
		} 
		else {
			prep.setInt(3,m.getTreatment().getId());
		}
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Medication> listofMedications(int treatmentId) throws SQLException { // checked
		String sql = "SELECT * FROM medications WHERE treatment_med=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, treatmentId);
		ResultSet rs = prep.executeQuery();
		List <Medication> medications = new ArrayList<Medication>();
		while (rs.next()) {
			int id = rs.getInt("medicationId");
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
	public void assignTreatmentToMedication(int medicationId, int treatmentId) throws SQLException { // checked
		String sql = "UPDATE medications SET treatment_med = ? WHERE medicationId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, treatmentId);
		prep.setInt(2,  medicationId);
		prep.executeUpdate();
		prep.close();	
	}
	
	@Override
	public Medication searchMedicationById(int id) throws SQLException { //Checked
		Medication medication = null;
		String sql = "SELECT * FROM medications WHERE medicationId = ?";
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
	public List<Medication> searchMedicationbyName(String name) throws SQLException { //Checked
		Medication m  = null;
		String sql = "SELECT * FROM medications WHERE name LIKE ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,"%" + name + "%");
		ResultSet rs = prep.executeQuery();
		List <Medication> medications = new ArrayList<Medication>();
		while(rs.next()){
			int id = rs.getInt("medicationId");
			int dosis = rs.getInt("dosis");
			m = new Medication(id,name,dosis);
			medications.add(m);
		}
		rs.close();	
		return medications;
	}
	
	@Override
	public void deleteMedication(int medicationId) throws SQLException { //Checked
		try {
			String sql = "DELETE FROM medications WHERE medicationId = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, medicationId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void editMedicationsName(String mName, int medicationId) throws SQLException { //Checked
		String sql = "UPDATE medications SET name = ? WHERE medicationId = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mName);
		prep.setInt(2, medicationId);
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public void editMedicationsDosis(String mDosis, int medicationId) throws SQLException { //Checked
		String sql = "UPDATE medications SET dosis = ? WHERE medicationId = ?";
		PreparedStatement prep= manager.getConnection().prepareStatement(sql);
		prep.setString(1, mDosis);
		prep.setInt(2, medicationId);
		prep.executeUpdate();
		prep.close();
	}

}
