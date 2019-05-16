package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IPatientFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;

/**
 * Session Bean implementation class PatientService
 */
@Stateless(name="PatientServiceBean")
public class PatientService implements IPatientServiceLocal,
		IPatientServiceRemote {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientService.class.getCanonicalName());

	private IPatientFactory patientFactory;
	
	private PatientDtoFactory patientDtoFactory;

	private IPatientDAO patientDAO;

	/**
	 * Default constructor.
	 */
	public PatientService() {
		patientFactory = new PatientFactory();
		patientDtoFactory = new PatientDtoFactory();
	}
	
	@Inject
	@ClinicDomain
	private EntityManager em;
	
	@PostConstruct
	private void initialize() {
		patientDAO = new PatientDAO(em);
	}
	
	/**
	 * @see IPatientService#addPatient(String, Date, long)
	 */
	@Override
	public long addPatient(PatientDto dto) throws PatientServiceExn {
		// Use factory to create patient entity, and persist with DAO
		try {
			Patient patient = patientFactory.createPatient(dto.getPatientId(), dto.getName(), dto.getDob(), dto.getAge());
			patientDAO.addPatient(patient);
			return patient.getId();
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}
	
	@Override
	public long createPatient(String name, Date dob, long patientId,int age) throws  PatientExn {
		// TODO Auto-generated method stub
		Patient patient=this.patientFactory.createPatient(patientId, name, dob, age);
		try {
			patientDAO.addPatient(patient);
			return patient.getId();
		} catch (PatientExn e) {
			// TODO Auto-generated catch block
			throw new PatientExn(e.toString());
		}
	}
	
	@Override
	public void deletePatient(long id, long pid) throws PatientServiceExn {
		// TODO Auto-generated method stub

		try {
			Patient patient = patientDAO.getPatient(id);
			if (pid!=patient.getPatientId())
				throw new PatientServiceExn("Tried to delete wrong patient: id=" + id + ", pid=" + pid);
			else
				patientDAO.deletePatient(patient);
		} catch (PatientExn e) {
			// TODO Auto-generated catch block
			throw new PatientServiceExn(e.toString());
		}
	}
	/**
	 * @see IPatientService#getPatient(long)
	 */
	@Override
	public PatientDto getPatient(long id) throws PatientServiceExn {
		try {
			Patient patient = patientDAO.getPatient(id);
			return patientDtoFactory.createPatientDto(patient);
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	/**
	 * @see IPatientService#getPatientByPatId(long)
	 */
	@Override
	public PatientDto getPatientByPatId(long pid) throws PatientServiceExn {
		try {
			Patient patient = patientDAO.getPatientByPatientId(pid);
			return patientDtoFactory.createPatientDto(patient);
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}
	@Override
	public PatientDto[] getPatientsByNameDob(String name, Date dob) {
		List<Patient> patients=patientDAO.getPatientByNameDob(name, dob);
		PatientDto[] dto=new PatientDto[patients.size()];
		for(int i=0;i<dto.length;i++)
		{
			dto[i]=patientDtoFactory.createPatientDto(patients.get(i));
		}
		return dto;
	}
	
	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		// Export treatment DTO from patient aggregate
		try {
			Patient patient = patientDAO.getPatient(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return patient.exportTreatment(tid, visitor);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}
	
	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		// TODO Auto-generated method stub
		try {
			Patient patient = patientDAO.getPatient(id);
			patient.deleteTreatment(tid);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			// TODO Auto-generated catch block
			throw new TreatmentNotFoundExn(e.toString());
		}
	}
	
	@Resource(name = "SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}

}
