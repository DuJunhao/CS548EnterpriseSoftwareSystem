package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@DiscriminatorValue("RA")
public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@ElementCollection
	@Temporal(TemporalType.DATE)
	private List<Date> dates;
	
	public Radiology() {
		super();
		this.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
	}
	public void export(ITreatmentExporter<Treatment> exporter){
		exporter.exportRadiology(this.getId(), this.getDiagnosis(), this.dates);
	}
	public List<Date> getDates() {
		return dates;
	}
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}
}
