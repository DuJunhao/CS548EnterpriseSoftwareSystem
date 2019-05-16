package edu.stevens.cs548.clinic.domain;

import static javax.persistence.InheritanceType.JOINED;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

/**
 * Entity implementation class for Entity: DrugTreatment
 * 
 */
@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorValue("DT")
public class DrugTreatment extends Treatment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String drug;
	private float dosage;

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public float getDosage() {
		return dosage;
	}

	public void setDosage(float dosage) {
		this.dosage = dosage;
	}

	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportDrugTreatment(this.getId(), 
								   		   this.getDiagnosis(),
								   		   this.drug, 
								   		   this.dosage,
								   		   this.getPatient().getId(),
								   		   this.getProvider().getId());
	}

	public DrugTreatment() {
		super();
		this.setTreatmentType("DT");
	}

}
