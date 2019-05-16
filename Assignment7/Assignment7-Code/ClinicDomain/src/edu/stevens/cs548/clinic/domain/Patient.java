package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO.ProviderExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO.TreatmentExp;

import static javax.persistence.CascadeType.REMOVE;

/**
 * Entity implementation class for Entity: Patient
 *
 */
@NamedQueries({
		@NamedQuery(name = "SearchPatientByPatientID", query = "select p from Patient p where p.PatientId = :pid"),
		@NamedQuery(name = "SearchPatientByNameDob", query = "select p from Patient p where p.name = :name and p.dob=:dob"),
		@NamedQuery(name = "CountPatientByPatientID", query = "select count(p) from Patient p where p.PatientId = :pid"),
		@NamedQuery(name = "RemoveAllPatients", query = "delete from Patient p") })
@Entity
@Table(name = "PATIENT")
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private long PatientId;
	@Column(name = "PATIENT_NAME")
	private String name;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private int age;
	@OneToMany(cascade = REMOVE, mappedBy = "patient")
	@OrderBy
	private List<Treatment> treatments;
	@Transient
	private ITreatmentDAO treatmentDAO;

	public Patient() {
		super();
		treatments = new ArrayList<Treatment>();
	}

     void addTreatment(Treatment t)  {
		this.treatmentDAO.addTreatment(t);
		this.getTreatments().add(t);
		if (t.getPatient() != this)
			t.setPatient(this);
	/*	t.getProvider().addTreatment(t, this);
		this.getTreatments().add(t);
		if (t.getPatient() != this)
			t.setPatient(this);*/
	}

	public void addDrugTreatment(String diagnosis, String drug, float dosage) {

		/* TODO:Add providerId parameter to the argument when adding treatments to a patient*/

		DrugTreatment drugTreatment = new DrugTreatment();
		drugTreatment.setDiagnosis(diagnosis);
		drugTreatment.setDrug(drug);
		drugTreatment.setDosage(dosage);
		//drugTreatment.getProvider().setNPI(NPI);
		this.addTreatment(drugTreatment);
	}

	public List<Long> getTreatmentIds() {
		List<Long> tids = new ArrayList<Long>();
		for (Treatment t : this.getTreatments()) {
			tids.add(t.getId());
		}
		return tids;
	}
	
	@SuppressWarnings("unchecked")
	public <T> void exportTreatment(long tid, ITreatmentExporter<T> exporter) throws TreatmentExp{
		Treatment t= treatmentDAO.getTreatmentByDbId(tid);
		if (t.getPatient() == this) {
			throw new TreatmentExp("Inappropriate treatment access: patient=" + id + " ,treatment=" + tid);
		} else {
			t.export((ITreatmentExporter<Treatment>) exporter);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T>void exportTreatments(ITreatmentExporter<T> exporter) {
		for (Treatment t : this.getTreatments()) {
			t.export((ITreatmentExporter<Treatment>) exporter);
		}
	}
	public void deleteTreatment(long tid) throws TreatmentExp {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if (t.getPatient() == this) {
			throw new TreatmentExp("Inappropriate treatment access: patient=" + id + " ,treatment=" + tid);

		}
		treatmentDAO.deleteTreatment(t);
	}

	public void addRadiology(String diagnosis, List<Date> dates)  {

		/* TODO:Add providerId parameter to the argument when adding treatments to a patient*/
		
		Radiology radiology = new Radiology();
		radiology.setDiagnosis(diagnosis);
		radiology.setDates(dates);
		//radiology.getProvider().setNPI(NPI);
		this.addTreatment(radiology);
	}

	public void addSurgery(String diagnosis, Date date) {

		/* TODO:Add providerId parameter to the argument when adding treatments to a patient*/

		Surgery surgery = new Surgery();
		surgery.setDiagnosis(diagnosis);
		surgery.setDate(date);
		//surgery.getProvider().setNPI(NPI);
		this.addTreatment(surgery);
	}

	public void setTreatmentDAO(ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPatientId() {
		return this.PatientId;
	}

	public void setPatientId(long PatientId) {
		this.PatientId = PatientId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	protected List<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}