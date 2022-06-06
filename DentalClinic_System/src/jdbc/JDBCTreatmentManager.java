package jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinic.ifaces.TreatmentManager;
import dentalClinic.pojos.Treatment;

public class JDBCTreatmentManager implements TreatmentManager {	
	private JDBCManager manager;

	public JDBCTreatmentManager(JDBCManager m) {
		this.manager = m;
	}
	@Override
	public void addTreatment(Treatment t) throws SQLException {
		String sql = "INSERT INTO treatments (name, diagnosis, startDate, finishDate, patient_treat) VALUES (?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,t.getName());
		prep.setString(2, t.getDiagnosis());			
		prep.setDate(3, t.getStartDate());
		prep.setDate(4, t.getFinishDate());
		if (t.getPatient() == null) {
			prep.setNull(5, java.sql.Types.INTEGER);
		}
		else {
			prep.setInt(5, t.getPatient().getId());
		}
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Treatment> listofTreatments(int patientId) throws SQLException {
		String sql = "SELECT * FROM treatments WHERE patient_treat = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
		List <Treatment> treatments = new ArrayList<Treatment>();
		while (rs.next()) {
			int id = rs.getInt("treatmentId");
			String name = rs.getString("name");
			String diagnosis = rs.getString("diagnosis");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			Treatment treatment = new Treatment(id,name,diagnosis,startDate,finishDate);
			treatments.add(treatment);		
		}
		prep.close();
		rs.close();
		return treatments;
	}
	
	@Override
	public void assignPatientToTreatment(int patientId, int treatmentId) throws SQLException { 
		String sql = "UPDATE treatments SET patient_treat = ? WHERE treatmentId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		prep.setInt(2,  treatmentId);
		prep.executeUpdate();
		prep.close();	
	}
	
	@Override
	public Treatment searchTreatmentById(int treatmentId) throws SQLException { 
		Treatment t = null;
		String sql = "SELECT * FROM treatments WHERE treatmentId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, treatmentId);
		ResultSet rs = prep.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("treatmentId");
			String name = rs.getString("name");
			String diagnosis = rs.getString("diagnosis");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			t = new Treatment(id,name,diagnosis,startDate,finishDate);		
		}
		prep.close();
		rs.close();
		return t;
	}

	@Override
	public List<Treatment> searchTreatmentbyName(String name, int patientId) throws SQLException {
		String sql = "SELECT * FROM treatments WHERE name LIKE ? AND patient_treat = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		ResultSet rs = prep.executeQuery();
		List <Treatment> treatments = new ArrayList<Treatment>();
		while (rs.next()) {
			int id = rs.getInt("treatmentId");
			String diagnosis = rs.getString("diagnosis");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			Treatment treatment = new Treatment(id,name,diagnosis,startDate,finishDate);
			treatments.add(treatment);		
		}
		prep.close();
		rs.close();
		return treatments;
	}

	@Override
	public void deleteTreatment(int treatmentId) throws SQLException { 
			String sql = "DELETE FROM treatments WHERE treatmentId = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, treatmentId);
			prep.executeUpdate();
			prep.close();
	}
	
	@Override
	public void editTreatmentsName(String name, int treatmentId) throws SQLException { 
		String sql = "UPDATE treatments SET name = ? WHERE treatmentId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.setInt(2, treatmentId);
		prep.executeUpdate();
		prep.close();
	}
	
	@Override
	public void editTreatmentsStartDate(Date start, int treatmentId) throws SQLException {
		String sql = "UPDATE patient SET startDate = ? WHERE treatmentId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setDate(1,start);
		prep.setInt(2, treatmentId);
		prep.executeUpdate();
		prep.close();
	}
	
	@Override
	public void editTreatmentsFinishDate(Date finish, int treatmentId) throws SQLException {
		String sql = "UPDATE patient SET finishDate = ? WHERE treatmentId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setDate(1,finish);
		prep.setInt(2, treatmentId);
		prep.executeUpdate();
		prep.close();	
	}
}
