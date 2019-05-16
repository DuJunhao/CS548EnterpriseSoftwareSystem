package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;


import javax.persistence.*;


/**
 * Entity implementation class for Entity: Treatment
 *
 */

import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO.ProviderExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO.TreatmentExp;

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
	public abstract void export(ITreatmentExporter<Treatment> exporter);

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
		if(!patient.getTreatments().contains(this))
			patient.addTreatment(this);
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
}
