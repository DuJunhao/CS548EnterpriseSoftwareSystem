package edu.stevens.cs548.clinic.domainmodel;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;

/**
 * Entity implementation class for Entity: Provider
 *
 */

@Entity
@Table(name="PROVIDER")
public class Provider implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7025511983314295144L;
	
	private enum specialization{surgery,radiology,oncology};
	
	@Id
	@Column(name="NPI")
	private long NPI;
	@Column(name="PROVIDER_NAME")
	private String name;
	@Column(name="SPECIALIZATION")
	private String spec;
	@OneToMany(cascade =REMOVE, mappedBy = "provider")
	@OrderBy
	private List<Treatment> treatments;
	private specialization special;
	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
	public long getNPI() {
		return NPI;
	}
	public void setNPI(long nPI) {
		NPI = nPI;
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
	public void setSpec(specialization special) {
		this.spec = special.name();
	}
	
   
}
