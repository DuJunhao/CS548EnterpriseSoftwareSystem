package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExp;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExp;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExp;

@WebService(
	name="IPatientWebPort",
	targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap/patient")
        
@SOAPBinding(
		style=SOAPBinding.Style.DOCUMENT,
		use=SOAPBinding.Use.LITERAL,
		parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

/*
 * Endpoint interface for the patient Web service.
 */
public interface IPatientWebService {
	
	@WebMethod(operationName="addPatient")
	public long addPatient (
			@WebParam(name="patient-dto",
			          targetNamespace="http://cs548.stevens.edu/clinic/dto")
			PatientDto dto) throws PatientServiceExp;
	
	@WebMethod(operationName="createPatient")
	@WebResult(name="patient-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public long createPatient(String name, Date dob,long patientId,int age) throws PatientServiceExp;
	
	@WebMethod(operationName="getPatientByDbId")
	@WebResult(name="patient-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto getPatientByDbId(long id) throws PatientServiceExp;
	
	@WebMethod(operationName="getPatientByPatientId")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto getPatientByPatientId(long pid) throws PatientServiceExp;
	
	@WebMethod(operationName="getPatientsByNameDob")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto[] getPatientsByNameDob(String name, Date dob);
	
	@WebMethod(operationName="PatientdeletePatient")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void deletePatient(String name, long id) throws PatientServiceExp;
	
	@WebMethod(operationName="PatientaddDrugTreatment")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void addDrugTreatment(long id,String diagnosis,String drug,float dosage) throws PatientNotFoundExp;
	
	@WebMethod(operationName="PatientaddSurgery")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void addSurgery(long id,String diagnosis,Date date) throws PatientNotFoundExp;
	
	@WebMethod(operationName="PatientaddRadiology")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void addRadiology(long id,String diagnosis,List<Date> dates) throws PatientNotFoundExp;
	
	@WebMethod(operationName="PatientGetTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExp, TreatmentNotFoundExp, PatientServiceExp;
	
	@WebMethod(operationName="PatientGetTreatments")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto[] getTreatments(long id,long[]tids)throws PatientNotFoundExp,TreatmentNotFoundExp,PatientServiceExp;
	
	@WebMethod(operationName="PatientdeleteTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void deleteTreatment(long id, long tid)throws PatientNotFoundExp,TreatmentNotFoundExp,PatientServiceExp;
	
	@WebMethod(operationName="PatientsiteInfo")
	public String siteInfo();

}
