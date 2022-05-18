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
	public void addMedication(Medication m) throws SQLException {
		String sql = "INSERT INTO medications (name, dosis, treatmentId) VALUES (?,?,?)";
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
	
	public static void main(String[] args) {
		JDBCManager manager = new JDBCManager();
		JDBCMedicationManager mm = new JDBCMedicationManager(manager);
		JDBCTreatmentManager mm2 = new JDBCTreatmentManager(manager);
		Date d = new Date(2, 1, 2);
		
	
		
		
		
		
		List<Medication> meds = new ArrayList<Medication>();
		
		List<Treatment> treats = new ArrayList<Treatment>();
		try {
			Treatment t = new Treatment("n","d",1,d,d);
			Treatment t2 = new Treatment("n","d",2,d,d);
			mm2.addTreatment(t);
			mm2.addTreatment(t2);
			Medication m = new Medication ("c", 4);
			Medication m2 = new Medication ("c2", 2);
			m.setTreatment(t);
			//mm.addMedication(m);
			treats = mm2.searchTreatmentbyName("n");
			//mm.listofMedications(t.getId());

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
