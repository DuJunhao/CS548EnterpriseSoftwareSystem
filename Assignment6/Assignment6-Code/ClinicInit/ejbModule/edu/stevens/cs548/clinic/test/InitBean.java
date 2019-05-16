package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO.ProviderExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.DAO.PatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.ProviderDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.DAO.TreatmentDAO;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
    
	@Inject
	IPatientServiceLocal patientService;
	@Inject
	IProviderServiceLocal providerService;
	
	@PersistenceContext(unitName="ClinicDomain")
	EntityManager em;
	
	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: ");
		try {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1984, 9, 4);
		
		IPatientDAO patientDAO = new PatientDAO(em);
		IProviderDAO providerDAO = new ProviderDAO(em);
		ITreatmentDAO treatmentDAO = new TreatmentDAO(em);

		PatientFactory patientFactory = new PatientFactory();
		
		TreatmentFactory treatmentFactory = new TreatmentFactory();
		Treatment drugTreatment=treatmentFactory.createDrugTreatment("cancer", "panacea", (float) 15.0);
		
		ProviderFactory providerFactory=new ProviderFactory();
		Provider paul=providerFactory.createProvider(81694321L,"Paul Fuck","oncology");
		Provider alex=providerFactory.createProvider(210654321L, "alex shit", "surgery");
		
		
		
		providerDAO.addProvider(paul);
		logger.info("Added Provider "+paul.getName()+" with NPI "+paul.getNPI());
		providerDAO.addProvider(alex);
		logger.info("Added Provider "+alex.getName()+" with NPI "+alex.getNPI());
		
		providerDAO.deleteProvider(paul);
		logger.info("deleted Provider "+paul.getName()+" with NPI "+paul.getNPI());
		providerDAO.deleteProvider(alex);
		logger.info("deleted Provider "+alex.getName()+" with NPI "+alex.getNPI());
		} catch (ProviderExp e) {
			// TODO Auto-generated catch block
			IllegalStateException ex = new IllegalStateException("Failed to add provider record.");
			ex.initCause(e);
			throw ex;
		}
			
	}

}
