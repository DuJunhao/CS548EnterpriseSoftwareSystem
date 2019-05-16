package edu.stevens.cs548.clinic.service.ejb;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	public long createProvider( String spec, long NPI) throws  ProviderExn {
		// TODO Auto-generated method stub
		Provider provider = this.providerFactory.createProvider(spec,NPI);
		try {
			providerDAO.addProvider(provider);
			return provider.getNPI();
		} catch (ProviderExn e) {
			throw new ProviderExn(e.toString());
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
	public void deleteProvider(long id, long NPI) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerDAO.getProvider(id);
			if (NPI!=provider.getNPI())
				throw new ProviderServiceExn("Tried to delete wrong provider: id=" + id + ", NPI=" + NPI);
			else
				providerDAO.deleteProvider(provider);
		} catch (ProviderExn e) {
			// TODO Auto-generated catch block
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
	
	@Override
	public long addTreatment(TreatmentDto t) throws ProviderNotFoundExn,PatientNotFoundExn {
		try {
			Provider provider = providerDAO.getProvider(t.getProvider());
			Patient patient = patientDAO.getPatient(t.getPatient());
			if (t.getDrugTreatment() != null) {
				Treatment drugtreatment = treatmentFactory.createDrugTreatment(t.getDiagnosis(), t.getDrugTreatment().getName(), t.getDrugTreatment().getDosage());
				long tid = provider.addTreatment(drugtreatment, patient);
				return tid;
			}
			else if (t.getSurgery() != null) {
				long tid = provider.addTreatment(treatmentFactory.createSurgeryTreatment(t.getDiagnosis(), t.getSurgery().getDate()), patient);
				return tid;
			}
			else {
				return provider.addTreatment(treatmentFactory.createRadiolotyTreatment(t.getDiagnosis(), t.getRadiology().getDate()), patient);
			}
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		}
	}

	@Resource(name = "SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}
}
