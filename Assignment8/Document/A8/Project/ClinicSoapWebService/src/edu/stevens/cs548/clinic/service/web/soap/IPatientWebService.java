package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;

@WebService(
	name="IPatientWebPort",
	targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap")
        
@SOAPBinding(
		style=SOAPBinding.Style.DOCUMENT,
		use=SOAPBinding.Use.LITERAL,
		parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

/*
 * Endpoint interface for the patient Web service.
 */
public interface IPatientWebService {
	
	@WebMethod
	public long addPatient (
			@WebParam(name="patient-dto",
			          targetNamespace="http://cs548.stevens.edu/clinic/dto")
			PatientDto dto) throws PatientServiceExn;

	@WebMethod
	@WebResult(name="patient-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto getPatient(long id) throws PatientServiceExn;
	
	@WebMethod
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto getPatientByPatId(long pid) throws PatientServiceExn;
	
	@WebMethod(operationName="patientGetTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;

	@WebMethod
	public String siteInfo();
	
	@WebMethod(operationName="createPatient")
	@WebResult(name="patient-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	long createPatient(String name, Date dob, long patientId, int age) throws PatientExn;
	
	@WebMethod(operationName="getPatientsByNameDob")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	PatientDto[] getPatientsByNameDob(String name, Date dob);
	
	@WebMethod(operationName="PatientdeletePatient")
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	void deletePatient(long id, long pid) throws PatientServiceExn;
	
	@WebMethod(operationName="PatientdeleteTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
}
