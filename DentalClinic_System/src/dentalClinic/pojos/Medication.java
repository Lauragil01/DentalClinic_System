package dentalClinic.pojos;

import java.io.Serializable;
import java.util.Objects;

public class Medication implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1452411741113954002L;
	private Integer medicationId;
	private String name;
	private Integer dosis;
	private Treatment treatment; //many to one relationship
	
	public Medication() {
		super();
	}
	
	public Medication(int id, String name, int dosis, Treatment treatment) {
		this.medicationId = id;
		this.name = name; 
		this.dosis = dosis;
		this.treatment = treatment;
	}
	
	public Medication( String name, int dosis, Treatment treatment) {
		this.name = name; 
		this.dosis = dosis;
		this.treatment = treatment;
	}
	public Medication( String name, int dosis) {
		this.name = name; 
		this.dosis = dosis;
	}
	
	public Medication(int id, String name, int dosis) {
		this.medicationId = id;
		this.name = name; 
		this.dosis = dosis;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(medicationId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medication other = (Medication) obj;
		return Objects.equals(medicationId, other.medicationId);
	}
	public Integer getId() {
		return medicationId;
	}
	public void setId(Integer id) {
		this.medicationId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDosis() {
		return dosis;
	}
	public void setDosis(Integer dosis) {
		this.dosis = dosis;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	@Override
	public String toString() {
		return ("Id: " + medicationId + "\nName: " + name + "\nDosis: " + dosis + "\nTreatment: " + treatment);
	}
	
	
	
}
