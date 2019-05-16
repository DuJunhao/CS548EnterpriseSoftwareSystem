package edu.stevens.cs548.clinic.domainmodel;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import static javax.persistence.CascadeType.REMOVE;
/**
 * Entity implementation class for Entity: Patient
 *
 */

@Entity
@Table(name = "PATIENT")
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private long PatientId;
	@Column(name="PATIENT_NAME")
	private String name;
	@Temporal(TemporalType.DATE)
	private Date dob;
	@OneToMany(cascade = REMOVE, mappedBy = "patient")
	@OrderBy
	private List<Treatment> treatments;
	
	public Patient() {
		super();
		treatments = new ArrayList<Treatment>();
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

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
}