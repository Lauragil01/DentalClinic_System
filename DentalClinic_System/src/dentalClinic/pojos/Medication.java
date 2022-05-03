package dentalClinic.pojos;

import java.io.Serializable;
import java.util.Objects;

public class Medication implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1452411741113954002L;
	private Integer id;
	private String name;
	private Integer dosis;
	private Treatment treatment;
	
	public Medication() {
		super();
	}
	
	public Medication(int id, String name, int dosis, Treatment treatment) {
		this.id = id;
		this.name = name; 
		this.dosis = dosis;
		this.treatment = treatment;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		return "Medication [id=" + id + ", name=" + name + ", dosis=" + dosis + ", treatment=" + treatment + "]";
	}
	
	
	
}
