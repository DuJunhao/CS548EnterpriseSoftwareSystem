package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExp;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExp;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExp;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

//Use JSR-181 annotations to specify Web service.
//Specify: endpoint interface (FQN), target namespace, service name, port name

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap/provider", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService{
	
	// Use CDI to inject the service
	@EJB(beanName="ProviderServiceBean")
	IProviderServiceLocal service;
		
	public long addProvider(ProviderDto dto) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		return service.addProvider(dto);
	}

	public long createProvider(String name, String spec, long NPI) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		return service.createProvider(name, spec, NPI);
	}

	public ProviderDto getProviderByProviderNPI(long NPI) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		return service.getProviderByProviderNPI(NPI);
	}

	public ProviderDto getProviderByProviderKey(long id) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		return service.getProviderByProviderKey(id);
	}

	public ProviderDto[] getProvidersByName(String name) {
		// TODO Auto-generated method stub
		return service.getProvidersByName(name);
	}

	public void deleteProvider(String name, long NPI) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		service.deleteProvider(name, NPI);
	}

	public void addDrugTreatment(TreatmentDto treatmentDto, long pid, Long NPI, String diagnosis, String drug,
			float dosage) throws ProviderServiceExp {
		// TODO Auto-generated method stub
		service.addDrugTreatment(treatmentDto, pid, NPI, diagnosis, drug, dosage);
	}

	public void addSurgery(TreatmentDto treatmentDto, long pid, Long NPI, String diagnosis, Date date)
			throws ProviderServiceExp {
		// TODO Auto-generated method stub
		service.addSurgery(treatmentDto, pid, NPI, diagnosis, date);
	}

	public void addRadiology(TreatmentDto treatmentDto, long pid, Long NPI, String diagnosis, List<Date> dates)
			throws ProviderServiceExp {
		// TODO Auto-generated method stub
		service.addRadiology(treatmentDto, pid, NPI, diagnosis, dates);
	}

	public TreatmentDto getTreatment(long NPI, long tid)
			throws ProviderNotFoundExp, TreatmentNotFoundExp, ProviderServiceExp {
		// TODO Auto-generated method stub
		return service.getTreatment(NPI, tid);
	}

	public TreatmentDto[] getTreatments(long NPI, long[] tids)
			throws ProviderNotFoundExp, TreatmentNotFoundExp, ProviderServiceExp {
		// TODO Auto-generated method stub
		return service.getTreatments(NPI, tids);
	}

	public void deleteTreatment(long NPI, long tid)
			throws ProviderNotFoundExp, TreatmentNotFoundExp, ProviderServiceExp {
		// TODO Auto-generated method stub
		service.deleteTreatment(NPI, tid);
	}

	public String siteInfo() {
		// TODO Auto-generated method stub
		return service.siteInfo();
	}

}
