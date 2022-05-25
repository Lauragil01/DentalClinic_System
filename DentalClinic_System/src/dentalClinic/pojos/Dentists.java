package dentalClinic.pojos;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Dentists")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dentists {
	@XmlElement(name = "Dentist")
	private List<Dentist> dentists = null;

	public List<Dentist> getDentists() {
		return dentists;
	}

	public void setDentists(List<Dentist> dentists) {
		this.dentists = dentists;
	}
}
