package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExp;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExp;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExp;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceRemote;

// Use JSR-181 annotations to specify Web service.
//Specify: endpoint interface (FQN), target namespace, service name, port name

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IPatientWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap/patient", 
		serviceName = "PatientWebService", portName = "PatientWebPort")

public class PatientWebService implements IPatientWebService {

	// Use CDI to inject the service
	//@EJB(beanName="PatientServiceBean")
	@Inject
	IPatientServiceLocal service;


	public long addPatient(PatientDto dto) throws PatientServiceExp {
		return service.addPatient(dto);
	}


	public PatientDto getPatientByDbId(long id) throws PatientServiceExp {
		return service.getPatientByDbId(id);
	}


	public PatientDto getPatientByPatientId(long pid) throws PatientServiceExp {
		return service.getPatientByPatientId(pid);
	}


	public String siteInfo() {
		return service.siteInfo();
	}


	public long createPatient(String name, Date dob, long patientId, int age) throws PatientServiceExp {
		// TODO Auto-generated method stub
		return service.createPatient(name, dob, patientId, age);
	}


	public PatientDto[] getPatientsByNameDob(String name, Date dob) {
		// TODO Auto-generated method stub
		return service.getPatientsByNameDob(name, dob);
	}


	public void deletePatient(String name, long id) throws PatientServiceExp {
		// TODO Auto-generated method stub
		service.deletePatient(name, id);
	}


	public void addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws PatientNotFoundExp {
		// TODO Auto-generated method stub
		service.addDrugTreatment(id, diagnosis, drug, dosage);
	}


	public void addSurgery(long id, String diagnosis, Date date) throws PatientNotFoundExp {
		// TODO Auto-generated method stub
		service.addSurgery(id, diagnosis, date);
	}


	public void addRadiology(long id, String diagnosis, List<Date> dates) throws PatientNotFoundExp {
		// TODO Auto-generated method stub
		service.addRadiology(id, diagnosis, dates);
	}

	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExp, TreatmentNotFoundExp,
	PatientServiceExp {
		return service.getTreatment(id, tid);
	}
	
	
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExp, TreatmentNotFoundExp, PatientServiceExp {
		// TODO Auto-generated method stub
		return service.getTreatments(id, tids);
	}

	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExp, TreatmentNotFoundExp, PatientServiceExp {
		// TODO Auto-generated method stub
		service.deleteTreatment(id, tid);
	}

}
