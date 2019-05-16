package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO.ProviderExp;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO.TreatmentExp;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.DAO.PatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.ProviderDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.DAO.TreatmentDAO;
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
	
	// TODO inject an EM
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
			calendar.set(1984, 10, 15);
			
			IPatientDAO patientDAO = new PatientDAO(em);
			IProviderDAO providerDAO = new ProviderDAO(em);
			ITreatmentDAO treatmentDAO = new TreatmentDAO(em);

			PatientFactory patientFactory = new PatientFactory();
			
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			Treatment drugTreatment=treatmentFactory.createDrugTreatment("cancer", "panacea", (float) 15.0);
			
			ProviderFactory providerFactory=new ProviderFactory();
			Provider paul=providerFactory.createProvider(81654321L,"Paul Fuck","oncology");
			Provider alex=providerDAO.getProviderByNPI(87654321L);
			providerDAO.deleteProvider(alex);
			alex=providerFactory.createProvider(218654321L, "alex shit", "surgery");
			logger.info("deleted Provider "+alex.getName()+" with NPI "+alex.getNPI());
			providerDAO.addProvider(paul);
			logger.info("Added Provider "+paul.getName()+" with NPI "+paul.getNPI());
			providerDAO.addProvider(alex);
			logger.info("Added Provider "+alex.getName()+" with NPI "+alex.getNPI());
			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * If we ensure that deletion of patients cascades deletes of treatments,
			 * then we only need to delete patients.
			 */
			
			Patient john = patientFactory.createPatient(12345678L, "John Doe", calendar.getTime(), 33);
			patientDAO.addPatient(john);
			logger.info("Added Patient "+john.getName()+" with id "+john.getId());	
			
			john.addDrugTreatment("LungsCancer","panacea",(float) 30.0);
			logger.info("Added Treatment "+john.getName()+" with DrugTreatment "+john.getTreatments().get(0).getId());
			
			john.addSurgery("LiverCancer",calendar.getTime());
			logger.info("Added Treatment "+john.getName()+" with Surgery "+john.getTreatmentIds());
			for(int i=0;i<john.getTreatments().size();i++)
				logger.info(john.getTreatments().get(i).getTreatmentType().toString());
			
			patientDAO.deletePatient(john);
			logger.info("deleted "+john.getName()+" with id "+john.getId());;
			
			providerDAO.deleteProvider(paul);
			// TODO add more testing, including treatments and providers
			
		} catch (PatientExp e) {

			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
			
		} catch (ProviderExp e) {
			// TODO Auto-generated catch block
			IllegalStateException ex = new IllegalStateException("Failed to add provider record.");
			ex.initCause(e);
			throw ex;
		}
			
	}

}
