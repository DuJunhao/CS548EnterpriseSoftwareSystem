package edu.stevens.cs548.clinic.domain.DAO;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.Treatment;

public interface ITreatmentDAO{
	public static class TreatmentExp extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TreatmentExp(String msg) {
			super(msg);
		}
	}
	public Treatment getTreatmentByDbId(long id) throws TreatmentExp;
	public void addTreatment(Treatment t);
	public void deleteTreatment(Treatment t);
	//public void deleteTreatments(Patient patient);
	void addTreatment(Treatment t, Patient p);
}
