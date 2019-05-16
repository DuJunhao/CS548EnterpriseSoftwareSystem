package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO.ProviderExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO.TreatmentExp;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@NamedQueries({
	@NamedQuery(name = "SearchProviderByProviderNPI", query = "select p from Provider p where p.NPI = :NPI"),
	@NamedQuery(name = "SearchProviderByName", query = "select p from Provider p where p.name = :name"),
	@NamedQuery(name = "CountProviderByProviderNPI", query = "select count(p) from Provider p where p.NPI = :NPI"),
	@NamedQuery(name = "RemoveAllProviders", query = "delete from Provider p") })
@Entity
@Table(name="PROVIDER")
public class Provider implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7025511983314295144L;
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name="NPI")
	private long NPI;
	@Column(name="PROVIDER_NAME")
	private String name;
	@Column(name="SPECIALIZATION")
	private String spec;
	@OneToMany(cascade =REMOVE, mappedBy = "provider")
	@OrderBy
	private List<Treatment> treatments;
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
	public void addTreatment(Treatment t,Patient p) throws TreatmentExp, PatientExp, ProviderExp {
		p.addTreatment(t);
	}
	public List<Long> getTreatmentIds() {
		List<Long> tids = new ArrayList<Long>();
		for (Treatment t : this.getTreatments()) {
			tids.add(t.getId());
		}
		return tids;
	}
	
	public void deleteTreatment(long tid) throws TreatmentExp {
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if (t.getProvider() == this) {
			throw new TreatmentExp("Inappropriate treatment access: provider=" + NPI + " ,treatment=" + tid);

		}
		treatmentDAO.deleteTreatment(t);
	}
	
	@SuppressWarnings("unchecked")
	public<T> void exportTreatment(long tid, ITreatmentExporter<T>exporter) throws TreatmentExp{
		Treatment t= treatmentDAO.getTreatmentByDbId(tid);
		if (t.getProvider() == this) {
			throw new TreatmentExp("Inappropriate treatment access: provider=" + NPI + " ,treatment=" + tid);
		} else {
			t.export((ITreatmentExporter<Treatment>) exporter);
		}
	}
	public void exportTreatments(ITreatmentExporter<Treatment> exporter) {
		for (Treatment t : this.getTreatments()) {
			t.export(exporter);
		}
	}
	
	public void setTreatmentDAO(ITreatmentDAO tdao) {
		this.treatmentDAO=tdao;	
	}
	public long getNPI() {
		return NPI;
	}
	public void setNPI(long nPI) {
		this.NPI = nPI;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String special) {
		this.spec = special;
	}
	protected List<Treatment> getTreatments() {
		return treatments;
	}
	protected void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
   
}