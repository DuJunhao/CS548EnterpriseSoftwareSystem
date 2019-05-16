package edu.stevens.cs548.clinic.domainmodel;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TTYPE")
@Table(name="TREATMENT")
public abstract class Treatment implements Serializable {

	private static final long serialVersionUID = -6375011083514153849L;
	/**
	 * 
	 */

	@Id
	@GeneratedValue
	private long id;
	@Column(name="DIAGNOSIS")
	private String diagnosis;
	@ManyToOne
	@JoinColumn(name = "patient_fk", referencedColumnName = "id")
	private Patient patient;
	@ManyToOne
	@JoinColumn(name = "provider_fk", referencedColumnName = "NPI")
	private Provider provider;
	
	@Column(name="TTYPE",length=2)
	private String treatmentType;
	
	public Treatment()
	{
		super();
		
	}
	
	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public Patient getPatient() {
		return patient;
	}
	void setPatient(Patient patient) {
		this.patient = patient;
	}
}
