package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import static javax.persistence.InheritanceType.JOINED;

/**
 * Entity implementation class for Entity: SurgeryTreatment
 *
 */

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorValue("SU")

public class SurgeryTreatment extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SurgeryTreatment() {
		super();
		this.setTreatmentType("SU");
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportSurgery(this.getId(),this.getDiagnosis(), this.getDate(), this.getPatient().getId(), this.getProvider().getId());
	}
   
}
