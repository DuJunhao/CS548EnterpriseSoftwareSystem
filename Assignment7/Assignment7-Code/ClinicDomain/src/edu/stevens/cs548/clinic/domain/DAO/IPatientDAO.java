package edu.stevens.cs548.clinic.domain.DAO;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.Patient;

public interface IPatientDAO {
	public static class PatientExp extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;	
		public PatientExp(String msg)
		{
			super(msg);
		}
	}
	public Patient getPatientByDbId(long id) throws PatientExp;
	public Patient getPatientByPatientId(long pid) throws PatientExp;
	public List<Patient> getPatientByNameDob(String name,Date dob);
	public void addPatient(Patient p) throws PatientExp;
	public void deletePatient(Patient p) throws PatientExp;
}
