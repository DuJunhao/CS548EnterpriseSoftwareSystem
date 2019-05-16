package edu.stevens.cs548.clinic.service.web.soap;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService {
	
	@EJB(beanName="ProviderServiceBean")
	IProviderServiceLocal service;

	@Override
	public long addPrrovider(ProviderDto dto) throws ProviderServiceExn {
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByNPI(long NPI) throws ProviderServiceExn {
		return service.getProviderByNPI(NPI);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		return service.getTreatment(id, tid);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}

	@Override
	public long createProvider(String spec, long NPI) throws ProviderExn {
		// TODO Auto-generated method stub
		return service.createProvider(spec, NPI);
	}

	@Override
	public void deleteProvider(long id, long NPI) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		 service.deleteProvider(id, NPI);
	}

	@Override
	public void addTreatment(TreatmentDto treatmentDto)
			throws ProviderServiceExn, PatientNotFoundExn, TreatmentNotFoundExn {
		// TODO Auto-generated method stub
		 service.addTreatment(treatmentDto);
	}
	
	
}
