package edu.stevens.cs548.clinic.domain.DAO;

import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.Treatment;

public class TreatmentDAO implements ITreatmentDAO {
	
	private EntityManager em;
	
	public TreatmentDAO(EntityManager em) {
		// TODO Auto-generated constructor stub
		this.em=em;
	}
	@Override
	public Treatment getTreatmentByDbId(long id) throws TreatmentExp {
		// TODO Auto-generated method stub
		Treatment t=em.find(Treatment.class, id);
		if(t==null) {
			throw new TreatmentExp("Missing treatment: id="+id);
		}
		else {
			return t;
		}
	}

	@Override
	public void addTreatment(Treatment t) {
		// TODO Auto-generated method stub
		em.persist(t);
	}
	
	@Override
	public void deleteTreatment(Treatment t)
	{
		// TODO Auto-generated method stub
		em.remove(t);
	}
	/*@Override
	public void deleteTreatments(Patient patient) {
		// TODO Auto-generated method stub

	}*/
	@Override
	public void addTreatment(Treatment t, Patient p) {
		// TODO Auto-generated method stub
		em.persist(t);
	}

}
