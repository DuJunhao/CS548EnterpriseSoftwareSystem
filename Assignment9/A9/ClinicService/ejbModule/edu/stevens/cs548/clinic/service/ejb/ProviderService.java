package edu.stevens.cs548.clinic.service.ejb;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.RadiologyTreatment;
import edu.stevens.cs548.clinic.domain.SurgeryTreatment;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentFactory;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;

@Stateless(name="ProviderServiceBean")
public class ProviderService implements IProviderServiceLocal, IProviderServiceRemote {

	private IProviderFactory providerFactory;
	
	private ProviderDtoFactory providerDtoFactory;
	
	private ITreatmentFactory treatmentFactory;
	
	private TreatmentDtoFactory treatmentDtoFactory;
	
	private IProviderDAO providerDAO;
	
	private IPatientDAO patientDAO;
	
	private static Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());
	
	public ProviderService() {
		providerFactory = new ProviderFactory();
		providerDtoFactory = new ProviderDtoFactory();
		treatmentFactory = new TreatmentFactory();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}
	
	@Inject
	@ClinicDomain
	private EntityManager em;
	
	@PostConstruct
	private void initialize() {
		patientDAO = new PatientDAO(em);
		providerDAO = new ProviderDAO(em);
		logger.info("dao created");
	}
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		try {
			Provider provider = providerFactory.createProvider(dto.getSpecialization(), dto.getNPI());
			providerDAO.addProvider(provider);
			return provider.getId();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProvider(id);
			return providerDtoFactory.createProviderDto(provider);
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public ProviderDto getProviderByNPI(long NPI) throws ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProviderByNPI(NPI);
			return providerDtoFactory.createProviderDto(provider);
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProvider(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return provider.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new TreatmentNotFoundExn(e.toString());
		}
	}
	
	@Resource(mappedName="jms/clinic/Treatment")
	private ConnectionFactory treatmentConnFactory;
	
	@Resource(mappedName="jms/Treatment")
	private Topic treatmentTopic;
		
	@Override
	public long addTreatment(TreatmentDto t) throws ProviderNotFoundExn,PatientNotFoundExn {
		Connection treatmentConn = null;
		long tid=0;
		try {
			Provider provider = providerDAO.getProvider(t.getProvider());
			Patient patient = patientDAO.getPatient(t.getPatient());
			if (t.getDrugTreatment() != null) {
				Treatment drugtreatment = treatmentFactory.createDrugTreatment(t.getDiagnosis(), t.getDrugTreatment().getName(), t.getDrugTreatment().getDosage());
				tid = provider.addTreatment(drugtreatment, patient);
				
				treatmentConn = treatmentConnFactory.createConnection();
				Session session = treatmentConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(treatmentTopic);
				
				drugtreatment.setId(tid);
				TreatmentDto treatment = treatmentDtoFactory.createTreatmentDto((DrugTreatment) drugtreatment);
				ObjectMessage message = session.createObjectMessage();
				message.setObject(treatment);
				message.setStringProperty("treatmetnType", "Drug");
				producer.send(message);
				logger.info("Message sent");
				return tid;
			}
			else if (t.getSurgery() != null) {
				Treatment surgery = treatmentFactory.createSurgeryTreatment(t.getDiagnosis(), t.getSurgery().getDate());
				tid= provider.addTreatment(surgery, patient);
				
				surgery.setId(tid);
				treatmentConn = treatmentConnFactory.createConnection();
				Session session = treatmentConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(treatmentTopic);
				
				TreatmentDto treatment = treatmentDtoFactory.createTreatmentDto((SurgeryTreatment) surgery);
				
				ObjectMessage message = session.createObjectMessage();
				message.setObject(treatment);
				message.setStringProperty("treatmetnType", "Surgety");
				producer.send(message);
				return tid;
			}
			else {
				Treatment radiology = treatmentFactory.createRadiolotyTreatment(t.getDiagnosis(), t.getRadiology().getDate());
				tid =  provider.addTreatment(radiology, patient);
				radiology.setId(tid);
				
				treatmentConn = treatmentConnFactory.createConnection();
				Session session = treatmentConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(treatmentTopic);
				
				TreatmentDto treatment = treatmentDtoFactory.createTreatmentDto((RadiologyTreatment) radiology);
				
				ObjectMessage message = session.createObjectMessage();
				message.setObject(treatment);
				message.setStringProperty("treatmetnType", "Radiology");
				producer.send(message);
				return tid;
			}
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (JMSException e) {
			logger.severe("JMS Error: " + e.toString());
		} finally {
			try {
				if(treatmentConn != null) treatmentConn.close();
			} catch (JMSException e) {
				logger.severe("JMS Error: " + e.toString());
			}
		}
		return tid;
	}

	@Resource(name = "SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}
}
