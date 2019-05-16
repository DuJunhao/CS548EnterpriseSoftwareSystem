package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;



public interface IPatientService {
	public class PatientServiceExp extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PatientServiceExp(String message)
		{
			super(message);
		}
	}
	public class PatientNotFoundExp extends PatientServiceExp {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PatientNotFoundExp(String m)
		{
			super(m);
		}
	}
	public class TreatmentNotFoundExp extends PatientServiceExp {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TreatmentNotFoundExp(String m)
		{
			super(m);
		}
	}
	public long addPatient(PatientDto dto) throws PatientServiceExp;
	public long createPatient(String name, Date dob,long patientId,int age) throws PatientServiceExp;
	public PatientDto getPatientByDbId(long id) throws PatientServiceExp;
	public PatientDto getPatientByPatientId(long pid) throws PatientServiceExp;
	public PatientDto[] getPatientsByNameDob(String name, Date dob);
	public void deletePatient(String name, long id) throws PatientServiceExp;
	public void addDrugTreatment(long id,String diagnosis,String drug,float dosage) throws PatientNotFoundExp;
	public void addSurgery(long id,String diagnosis,Date date) throws PatientNotFoundExp;
	public void addRadiology(long id,String diagnosis,List<Date> dates) throws PatientNotFoundExp;
	public TreatmentDto getTreatment(long id,long tid)throws PatientNotFoundExp,TreatmentNotFoundExp,PatientServiceExp;
	public TreatmentDto[] getTreatments(long id,long[]tids)throws PatientNotFoundExp,TreatmentNotFoundExp,PatientServiceExp;
	public void deleteTreatment(long id, long tid)throws PatientNotFoundExp,TreatmentNotFoundExp,PatientServiceExp;
	public String siteInfo();
	
}
