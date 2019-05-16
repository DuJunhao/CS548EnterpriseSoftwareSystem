package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.SurgeryTreatment;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.ClinicDomain;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());
	
	private PatientFactory patientFactory;
	private PatientDtoFactory patientDtoFactory;
	private TreatmentFactory treatmentFactory;
	private TreatmentDtoFactory treatmentDtoFactory;
    private ProviderFactory providerFactory;
    private ProviderDtoFactory providerDtoFactory;

	/**
	 * Default constructor.
	 */
	public InitBean() {
		patientFactory = new PatientFactory();
		patientDtoFactory = new PatientDtoFactory();
		treatmentFactory = new TreatmentFactory();
		treatmentDtoFactory = new TreatmentDtoFactory();
	    providerFactory = new ProviderFactory();
	    providerDtoFactory = new ProviderDtoFactory();
	}
    
	@Inject
	IPatientServiceLocal patientService;
	
	@Inject
	IProviderServiceLocal providerService;
	@Inject @ClinicDomain
	private EntityManager em;
	@PostConstruct
	private void init(){
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Junhao Du ");
		try {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1984, 9, 4);
		@SuppressWarnings("deprecation")
		Date dob= new Date(95, 6, 6);
		IPatientDAO patientDAO = new PatientDAO(em);
		IProviderDAO providerDAO = new ProviderDAO(em);
		ITreatmentDAO treatmentDAO = new TreatmentDAO(em);

		PatientFactory patientFactory = new PatientFactory();
		
		TreatmentFactory treatmentFactory = new TreatmentFactory();
		Treatment drugTreatment=treatmentFactory.createDrugTreatment("cancer", "panacea", (float) 15.0);
		
		Patient john=patientFactory.createPatient(134556789L, "hew1orld", dob, 22);
		patientDAO.addPatient(john);
		logger.info("Added Patient "+john.getName()+" with Id "+john.getId()+" Pid="+john.getPatientId());
		
		/*john.deleteTreatment(drugTreatment);
		logger.info("Deleted Treatment "+drugTreatment+"for patient"+john.getName()+"id="+john.getId()+"Pid="+john.getPatientId());*/
		/*patientDAO.deletePatient(john);
		logger.info("Deleted Patient "+john.getName()+" with Id "+john.getId()+"Pid="+john.getPatientId());
*/
		
		ProviderFactory providerFactory=new ProviderFactory();
		Provider paul=providerFactory.createProvider("oncology",816443212L);
		Provider alex=providerFactory.createProvider("surgery",213454361L);
		
		providerDAO.addProvider(paul);
		logger.info("Added Provider "+paul.getNPI()+" with id "+paul.getId()+" with NPI "+paul.getNPI());
		providerDAO.addProvider(alex);
		logger.info("Added Provider "+alex.getNPI()+" with id "+paul.getId()+" with NPI "+alex.getNPI());
		
		drugTreatment.setPatient(john);
		drugTreatment.setProvider(paul);
		john.addTreatment(drugTreatment);
		logger.info("Added Treatment "+drugTreatment.getId()+" for patient "+john.getName()+" id="+john.getId()+" Pid="+john.getPatientId());
		
		paul.addTreatment(drugTreatment, john);
		logger.info("Added Treatment"+drugTreatment.getId()+drugTreatment.getDiagnosis()+"for Patient"+drugTreatment.getPatient()+"FromProvider "+paul.getNPI()+" with id "+paul.getId()+" with NPI "+paul.getNPI());
		
		
		} catch (ProviderExn e) {
			// TODO Auto-generated catch block
			IllegalStateException ex = new IllegalStateException("Failed to add provider record.");
			ex.initCause(e);
			throw ex;
		} catch (PatientExn e) {
			// TODO Auto-generated catch block
			IllegalStateException ex = new IllegalStateException("Failed to add Patient record.");
			ex.initCause(e);
			e.printStackTrace();
		}
		
	}

}
