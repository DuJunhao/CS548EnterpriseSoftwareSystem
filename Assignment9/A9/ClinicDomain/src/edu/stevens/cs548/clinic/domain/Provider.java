package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

import static javax.persistence.CascadeType.REMOVE;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity

@NamedQueries({
	@NamedQuery(
		name="SearchProviderByNPI",
		query="select p from Provider p where p.NPI = :NPI"),
	@NamedQuery(
		name="CountProviderByNPI",
		query="select count(p) from Provider p where p.NPI = :NPI")
})

@Table(name = "PROVIDER")
public class Provider implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private long NPI;
	
	private String specialization;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public long getNPI() {
		return NPI;
	}

	public void setNPI(long nPI) {
		NPI = nPI;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}
	
	public void getTreatmentIds(List<Long> treatmentIds) {
		for (Treatment t : this.getTreatments()) {
			treatmentIds.add(t.getId());
		}
	}
	
	public <T> T exportTreatment(long tid, ITreatmentExporter<T> visitor) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getProvider() != this) {
			throw new TreatmentExn("Inappropriate treatment access: provider = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
	
	@OneToMany(cascade = REMOVE, mappedBy = "provider")
	private List<Treatment> treatments;

	protected List<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	public long addTreatment(Treatment t,Patient p) {
		if(t.getProvider() != this) {
			t.setProvider(this);
		}
		long tid = p.addTreatment(t);
		this.getTreatments().add(t);
		return tid;
	}
	
	public Provider() {
		super();
		this.setTreatments(new ArrayList<Treatment>());
	}
}
