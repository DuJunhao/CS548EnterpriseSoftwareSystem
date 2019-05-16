package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.InheritanceType.JOINED;

/**
 * Entity implementation class for Entity: RadiologyTreatment
 *
 */

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorValue("RA")

public class RadiologyTreatment extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	@Temporal(DATE)
	@CollectionTable(name = "RADIOLOGYTREATMENT_DATES", joinColumns = @JoinColumn(name = "RADIOLOGYTREATMENT_FK", referencedColumnName = "id"))
	private List<Date> dates;

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public RadiologyTreatment() {
		super();
		this.setTreatmentType("RA");
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportRadiology(this.getId(), this.getDiagnosis(), this.getDates(), this.getPatient().getId(), this.getProvider().getId());
	}
   
}
