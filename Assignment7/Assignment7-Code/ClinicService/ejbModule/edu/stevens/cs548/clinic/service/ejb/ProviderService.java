package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO.ProviderExp;
import edu.stevens.cs548.clinic.domain.DAO.ITreatmentDAO.TreatmentExp;
import edu.stevens.cs548.clinic.domain.DAO.PatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.ProviderDAO;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO.PatientExp;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;

@Stateless
public class ProviderService implements IProviderServiceLocal,IProviderServiceRemote{

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientService.class.getCanonicalName());
	
	private ProviderFactory providerFactory;
	
	private ProviderDtoFactory providerDtoFactory;

	private IProviderDAO providerDAO;
	
	private IPatientDAO patientDAO;
	
	@Resource(name="SiteInfo2")
	private String siteInformation;
	
	public ProviderService() {
		// TODO Auto-generated constructor stub
		providerFactory=new ProviderFactory();
	}
	
	//@PersistenceContext(unitName="ClinicDomain")
	@Inject @ClinicDomain
	private EntityManager em;
	
	@PostConstruct
	private void intialize(){
		providerDAO=new ProviderDAO(em);
		patientDAO=new PatientDAO(em);
	}
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerFactory.createProvider(dto.getNPI(),dto.getName(),dto.getSpec());
			providerDAO.addProvider(provider);
			return provider.getNPI();
		} catch (ProviderExp e) {
			throw new ProviderServiceExp(e.toString());
		}
	}

	@Override
	public long createProvider(String name, String spec, long NPI) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		Provider provider = this.providerFactory.createProvider(NPI,spec,name);
		try {
			providerDAO.addProvider(provider);
			return provider.getNPI();
		} catch (ProviderExp e) {
			throw new ProviderServiceExp(e.toString());
		}
	}

	@Override
	public ProviderDto getProviderByProviderNPI(long NPI) throws ProviderServiceExp {
		try {
			Provider provider=providerDAO.getProviderByNPI(NPI);
			return new ProviderDto(provider);
		} catch (ProviderExp e) {
			throw new ProviderServiceExp(e.toString());
		}
	}

	@Override
	public ProviderDto getProviderByProviderKey(long id) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Provider provider=providerDAO.getProviderByNPI(id);
			return new ProviderDto(provider);
		} catch (ProviderExp e) {
			throw new ProviderServiceExp(e.toString());
		}
	}
	
	@Override
	public ProviderDto[] getProvidersByName(String name){
		List<Provider> providers=providerDAO.getProviderByName(name);
		ProviderDto[] dto=new ProviderDto[providers.size()];
		for(int i=0;i<dto.length;i++)
		{
			dto[i]=new ProviderDto(providers.get(i));
		}
		return dto;
	}
			
	@Override
	public void deleteProvider(String name, long NPI) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerDAO.getProviderByNPI(NPI);
			if (!name.equals(provider.getName()))
				throw new ProviderServiceExp("Tried to delete wrong provider: name=" + name + ", NPI=" + NPI);
			else
				providerDAO.deleteProvider(provider);
		} catch (ProviderExp e) {
			// TODO Auto-generated catch block
			throw new ProviderServiceExp(e.toString());
		}
	}

	@Override
	public void addDrugTreatment(TreatmentDto treatmentDto,long pid,Long NPI,String diagnosis, String drug, float dosage)
			throws ProviderServiceExp {
		try {
			Patient patient= patientDAO.getPatientByPatientId(pid);
			if(patient.getPatientId()==treatmentDto.getPatient()&&NPI==treatmentDto.getProvider())
				patient.addDrugTreatment(diagnosis, drug, dosage);
			
		} catch (PatientExp e) {
			// TODO Auto-generated catch block
			throw new ProviderServiceExp("Cannot find the patient for the treatment "+e.toString());
		}	
	}

	@Override
	public void addSurgery(TreatmentDto treatmentDto,long pid,Long NPI,String diagnosis, Date date) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Patient patient= patientDAO.getPatientByPatientId(pid);
			if(patient.getPatientId()==treatmentDto.getPatient()&&NPI==treatmentDto.getProvider())
				patient.addSurgery(diagnosis, date);	
		} catch (PatientExp e) {
			// TODO Auto-generated catch block
			throw new ProviderServiceExp("Cannot find the patient for the treatment "+e.toString());
		}	
	}

	@Override
	public void addRadiology(TreatmentDto treatmentDto,long pid,Long NPI, String diagnosis, List<Date> dates) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Patient patient= patientDAO.getPatientByPatientId(pid);
			if(patient.getPatientId()==treatmentDto.getPatient()&&NPI==treatmentDto.getProvider())
				patient.addRadiology(diagnosis, dates);	
		} catch (PatientExp e) {
			// TODO Auto-generated catch block
			throw new ProviderServiceExp("Cannot find the patient for the treatment "+e.toString());
		}	
	}
	
	
	@Override
	public TreatmentDto getTreatment(long NPI, long tid)
			throws ProviderNotFoundExp, TreatmentNotFoundExp, ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerDAO.getProviderByNPI(NPI);
			TreatmentDto treatmentDto=new TreatmentDto();
			TreatmentPdoToDto exporter=new TreatmentPdoToDto();
			provider.exportTreatment(tid, exporter);
			treatmentDto=exporter.getDto();
			return treatmentDto;
		} catch (ProviderExp e) {
			throw new ProviderNotFoundExp(e.toString());
		} catch (TreatmentExp e) {
			throw new ProviderServiceExp(e.toString());
		}
	}

	@Override
	public TreatmentDto[] getTreatments(long NPI, long[] tids)
			throws ProviderNotFoundExp, TreatmentNotFoundExp, ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerDAO.getProviderByNPI(NPI);
			TreatmentDto[] treatmentDtos=new TreatmentDto[tids.length];
			TreatmentPdoToDto exporter=new TreatmentPdoToDto();
			for(int i=0;i<tids.length;i++)
			{
				provider.exportTreatment(tids[i], exporter);
				treatmentDtos[i]=exporter.getDto();
			}
			return treatmentDtos;
		} catch (ProviderExp e) {
			throw new ProviderNotFoundExp(e.toString());
		} catch (TreatmentExp e) {
			throw new ProviderServiceExp(e.toString());
		}
	}

	@Override
	public void deleteTreatment(long NPI, long tid)
			throws ProviderNotFoundExp, TreatmentNotFoundExp, ProviderServiceExp {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerDAO.getProviderByNPI(NPI);
			provider.deleteTreatment(tid);
		} catch (ProviderExp e) {
			throw new ProviderNotFoundExp(e.toString());
		} catch (TreatmentExp e) {
			// TODO Auto-generated catch block
			throw new TreatmentNotFoundExp(e.toString());
		}
	}

	@Override
	public String siteInfo() {
		// TODO Auto-generated method stub
		return siteInformation;
	}

}
