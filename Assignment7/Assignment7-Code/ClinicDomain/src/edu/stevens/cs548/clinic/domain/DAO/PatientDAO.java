package edu.stevens.cs548.clinic.domain.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.stevens.cs548.clinic.domain.Patient;

public class PatientDAO implements IPatientDAO {
	@PersistenceContext
	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public PatientDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO=new TreatmentDAO(em);
	}

	@Override
	public Patient getPatientByDbId(long id) throws PatientExp {
		// TODO Auto-generated method stub
		Patient p = em.find(Patient.class, id);
		if (p == null) {
			throw new PatientExp("Patient Not Found: primary key=" + id);
		} else {
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public Patient getPatientByPatientId(long pid) throws PatientExp {
		// TODO Auto-generated method stubSearch
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientID", Patient.class)
				.setParameter("pid", pid);
		List<Patient> patients=query.getResultList();
		if(patients.size()>1)
			throw new PatientExp("Duplicate patient records: patient id="+pid);
		else if(patients.size()<1)
			throw new PatientExp("Patient not found: patient id="+pid);
		else {
			Patient p=patients.get(0);
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
			
	}

	@Override
	public List<Patient> getPatientByNameDob(String name, Date dob) {
		// TODO Auto-generated method stub
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByNameDob", Patient.class)
				.setParameter("name", name).setParameter("dob", dob);
		List<Patient>patients=query.getResultList();
		for(Patient p:patients)
		{
			p.setTreatmentDAO(this.treatmentDAO);
		}
		return patients;
	}

	@Override
	public void addPatient(Patient patient) throws PatientExp{
		// TODO Auto-generated method stub
		long pid=patient.getPatientId();
		TypedQuery<Patient>query=em.createNamedQuery("SearchPatientByPatientID", Patient.class)
				.setParameter("pid", pid);
		List<Patient>patients=query.getResultList();
		if(patients.size()<1){
			em.persist(patient);
			patient.setTreatmentDAO(this.treatmentDAO);
		}
		else {
			Patient patient2=patients.get(0);
			throw new PatientExp("Insertion: Patient with patient id("+pid+") already exists.\n** Name :"+patient2.getName());
		}
	}
	
	@Override
	public void deletePatient(Patient patient) throws PatientExp {
		// TODO Auto-generated method stub
		/*em.createQuery("delete t from treatment t where t.patient.id= :id")
		.setParameter("id", patient.getId())
		.executeUpdate();*/
		em.remove(patient);
	}

}
