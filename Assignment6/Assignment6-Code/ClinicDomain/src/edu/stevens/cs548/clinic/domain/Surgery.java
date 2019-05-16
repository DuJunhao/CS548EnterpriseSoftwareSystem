package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Surgery
 *
 */
@Entity
@DiscriminatorValue("SU")
public class Surgery extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public Surgery() {
		super();
		this.setTreatmentType(TreatmentType.SURGERY.getTag());
	}
	public void export(ITreatmentExporter<Treatment> exporter){
		exporter.exportSurgery(this.getId(), this.getDiagnosis(), this.date);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
   
}
