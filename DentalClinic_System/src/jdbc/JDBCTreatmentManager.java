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
	public void addTreatment(Treatment t, int patientId) throws SQLException {
		String sql = "INSERT INTO treatments (id, diagnosis, duration, startDate, finishDate, patient_id) VALUES (?,?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, t.getId());
		prep.setString(2, t.getDiagnosis());			
		prep.setInt(3, t.getConsultDuration());			
		prep.setDate(4, t.getStartDate());
		prep.setDate(5, t.getFinishDate());
		prep.executeUpdate();
		prep.close();
	}

	@Override
	public List<Treatment> listofTreatments(int patientId) throws SQLException {
		String sql = "SELECT * FROM treatments WHERE patientId=? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery(sql);
		List <Treatment> treatments = new ArrayList<Treatment>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String diagnosis = rs.getString("diagnosis");
			int duration = rs.getInt("duration");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			Treatment treatment = new Treatment(id,name,diagnosis,duration,startDate,finishDate);
			treatments.add(treatment);		
		}
		prep.close();
		rs.close();
		return treatments;
	}
	
	@Override
	public Treatment searchTreatmentById(int treatmentId) throws SQLException {
		Treatment t = null;
		String sql = "SELECT * FROM treatments WHERE treatmentId = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, treatmentId);
		ResultSet rs = prep.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String diagnosis = rs.getString("diagnosis");
			int duration = rs.getInt("duration");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			t = new Treatment(id,name,diagnosis,duration,startDate,finishDate);		
		}
		prep.close();
		rs.close();
		return t;
	}

	@Override
	public List<Treatment> searchTreatmentbyName(String name) throws SQLException {
		String sql = "SELECT * FROM treatments WHERE name LIKE ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		ResultSet rs = prep.executeQuery(sql);
		List <Treatment> treatments = new ArrayList<Treatment>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String diagnosis = rs.getString("diagnosis");
			int duration = rs.getInt("duration");
			Date startDate = rs.getDate("startDate");
			Date finishDate = rs.getDate("finishDate");
			Treatment treatment = new Treatment(id,name,diagnosis,duration,startDate,finishDate);
			treatments.add(treatment);		
		}
		prep.close();
		rs.close();
		return treatments;
	}

	@Override
	public void assign_Treatment(int treatmentId, int patientId) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTreatment(int treatmentId) {
		try {
			String sql = "DELETE FROM treatments WHERE treatmentId = ?";
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			prep.setInt(1, treatmentId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void editTreatmentsName(String name, int treatmentId) throws SQLException {
		String sql = "UPDATE patient SET name = ? WHERE treatmentId = ?";
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
