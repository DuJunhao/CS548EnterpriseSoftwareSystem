package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO.TreatmentExp;
import edu.stevens.cs548.clinic.domain.DAO.PatientDAO;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;

/**
 * Session Bean implementation class PatientService
 */
@Stateless
public class PatientService implements IPatientServiceLocal,IPatientServiceRemote{
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientService.class.getCanonicalName());
	//DEMO5 private IPatientFactory patientFactory;
	private PatientFactory patientFactory;
	
	private PatientDtoFactory patientDtoFactory;

	private IPatientDAO patientDAO;
	/**
	 * Default constructor.
	 */
	// TODO use dependency injection and EJB lifecycle methods to initialize DAOs
	public PatientService() {
		// TODO initialize factories
		patientFactory=new PatientFactory();
		//wrong patientDAO=new PatientDAO(em);
	}
	//@PersistenceContext(unitName="ClinicDomain")
	@Inject @ClinicDomain
	private EntityManager em;
	
	@PostConstruct
	private void intialize(){
		patientDAO=new PatientDAO(em);
	}

	/**
	 * @see IPatientService#addPatient(String, Date, long)
	 */
	@Override
	public long addPatient(PatientDto dto) throws PatientServiceExp {
		// Use factory to create patient entity, and persist with DAO
		try {
			Patient patient = patientFactory.createPatient(dto.getPatientId(), dto.getName(), dto.getDob(), dto.getAge());
			patientDAO.addPatient(patient);
			return patient.getId();
		} catch (PatientExp e) {
			throw new PatientServiceExp(e.toString());
		}
	}
	
	@Override
	public long createPatient(String name, Date dob, long patientId,int age) throws PatientServiceExp {
		// TODO Auto-generated method stub
		Patient patient=this.patientFactory.createPatient(patientId, name, dob, age);
		try {
			patientDAO.addPatient(patient);
			return patient.getId();
		} catch (PatientExp e) {
			// TODO Auto-generated catch block
			throw new PatientServiceExp(e.toString());
		}
	}
	
	/**
	 * @see IPatientService#getPatientByDbId(long)
	 */
	@Override
	public PatientDto getPatientByDbId(long id) throws PatientServiceExp {
		try {
			Patient patient=patientDAO.getPatientByDbId(id);
			return new PatientDto(patient);
		} catch (PatientExp e) {
			throw new PatientServiceExp(e.toString());
		}
	}

	/**
	 * @see IPatientService#getPatientByPatientId(long)
	 */
	@Override
	public PatientDto getPatientByPatientId(long pid) throws PatientServiceExp {
		try {
			Patient patient=patientDAO.getPatientByPatientId(pid);
			return new PatientDto(patient);
		} catch (PatientExp e) {
			throw new PatientServiceExp(e.toString());
		}
	}
	
	@Override
	public PatientDto[] getPatientsByNameDob(String name, Date dob) {
		List<Patient> patients=patientDAO.getPatientByNameDob(name, dob);
		PatientDto[] dto=new PatientDto[patients.size()];
		for(int i=0;i<dto.length;i++)
		{
			dto[i]=new PatientDto(patients.get(i));
		}
		return dto;
	}
	
	@Override
	public void deletePatient(String name, long id) throws PatientServiceExp {
		// TODO Auto-generated method stub

		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			if (!name.equals(patient.getName()))
				throw new PatientServiceExp("Tried to delete wrong patient: name=" + name + ", id=" + id);
			else
				patientDAO.deletePatient(patient);
		} catch (PatientExp e) {
			// TODO Auto-generated catch block
			throw new PatientServiceExp(e.toString());
		}
	}
	
	
	/*
	 * The Original Version
	 * 
	 * public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
		
		private ObjectFactory factory = new ObjectFactory();
		
		@Override
		public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug,
				float dosage) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates) {
			// TODO Auto-generated method stub	
			return null;
		}

		@Override
		public TreatmentDto exportSurgery(long tid, String diagnosis, Date date) {
			// TODO Auto-generated method stub	
			return null;
		}
		
	}*/
	
	// TODO inject resource value
	@Resource(name="SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws PatientNotFoundExp, TreatmentNotFoundExp, PatientServiceExp {
		// Export treatment DTO from patient aggregate
		try {
			Patient patient = patientDAO.getPatientByDbId(tid);
			TreatmentDto treatmentDto=new TreatmentDto();
			TreatmentPdoToDto exporter=new TreatmentPdoToDto();
			patient.exportTreatment(tid, exporter);
			treatmentDto=exporter.getDto();
			return treatmentDto;
		} catch (PatientExp e) {
			throw new PatientNotFoundExp(e.toString());
		} catch (TreatmentExp e) {
			throw new PatientServiceExp(e.toString());
		}
	}
	
	@Override
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExp, TreatmentNotFoundExp, PatientServiceExp {
		// Export treatments DTO from patient aggregate
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			TreatmentDto[] treatmentDtos=new TreatmentDto[tids.length];
			TreatmentPdoToDto exporter=new TreatmentPdoToDto();
			for(int i=0;i<tids.length;i++)
			{
				patient.exportTreatment(tids[i], exporter);
				treatmentDtos[i]=exporter.getDto();
			}
			return treatmentDtos;
		} catch (PatientExp e) {
			throw new PatientNotFoundExp(e.toString());
		} catch (TreatmentExp e) {
			throw new PatientServiceExp(e.toString());
		}
	}

	@Override
	public void addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws PatientNotFoundExp {
		// TODO Auto-generated method stub
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			patient.addDrugTreatment(diagnosis, drug, dosage);
		} catch (PatientExp e) {
			throw new PatientNotFoundExp(e.toString());
		}
	}
	@Override
	public void addRadiology(long id, String diagnosis,List<Date>dates) throws PatientNotFoundExp {
		// TODO Auto-generated method stub
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			patient.addRadiology(diagnosis, dates);
		} catch (PatientExp e) {
			throw new PatientNotFoundExp(e.toString());
		}
	}
	@Override
	public void addSurgery(long id, String diagnosis, Date date) throws PatientNotFoundExp {
		// TODO Auto-generated method stub
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			patient.addSurgery(diagnosis, date);
		} catch (PatientExp e) {
			throw new PatientNotFoundExp(e.toString());
		}
	}

	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExp, TreatmentNotFoundExp, PatientServiceExp {
		// TODO Auto-generated method stub
		try {
			Patient patient = patientDAO.getPatientByDbId(id);
			patient.deleteTreatment(tid);
		} catch (PatientExp e) {
			throw new PatientNotFoundExp(e.toString());
		} catch (TreatmentExp e) {
			// TODO Auto-generated catch block
			throw new TreatmentNotFoundExp(e.toString());
		}
	}

}
