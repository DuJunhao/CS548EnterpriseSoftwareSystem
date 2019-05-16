package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExp;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExp;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExp;

@WebService(
		name="IProviderWebPort",
		targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap/provider")
	        
	@SOAPBinding(
			style=SOAPBinding.Style.DOCUMENT,
			use=SOAPBinding.Use.LITERAL,
			parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public interface IProviderWebService {
	@WebMethod(operationName="addProvider")
	public long addProvider(@WebParam(name="provider-dto",
	          targetNamespace="http://cs548.stevens.edu/clinic/dto")
								ProviderDto dto) throws ProviderServiceExp;
	
	@WebMethod(operationName="createProvider")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public long createProvider(String name, String spec,long NPI) throws ProviderServiceExp;
	
	@WebMethod(operationName="getProviderByProviderNPI")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto getProviderByProviderNPI(long NPI) throws ProviderServiceExp;

	@WebMethod(operationName="getProviderByProviderKey")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto getProviderByProviderKey(long id) throws ProviderServiceExp;
	
	@WebMethod(operationName="getProvidersByName")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto[] getProvidersByName(String name);
	
	@WebMethod(operationName="deleteProvider")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void deleteProvider(String name, long NPI) throws ProviderServiceExp;
	
	@WebMethod(operationName="ProvideraddDrugTreatment")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void AddDrugTreatment(TreatmentDto treatmentDto,long NPI,String diagnosis,String drug,float dosage) throws ProviderServiceExp;
	
	@WebMethod(operationName="ProvideraddSurgery")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void AddSurgery(TreatmentDto treatmentDto,long NPI,Date date,String diagnosis) throws ProviderServiceExp;
	
	@WebMethod(operationName="ProvideraddRadiology")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void AddRadiology(TreatmentDto treatmentDto,long NPI,String diagnosis,List<Date> dates) throws ProviderServiceExp;
	/*
	@WebMethod(operationName="ProvidergetTreatment")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto GetTreatment(long NPI,long tid)throws ProviderNotFoundExp,TreatmentNotFoundExp,ProviderServiceExp;
	
	@WebMethod(operationName="ProvidergetTreatments")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto[] GetTreatments(long NPI,long[]tids)throws ProviderNotFoundExp,TreatmentNotFoundExp,ProviderServiceExp;*/
	
	@WebMethod(operationName="ProviderdeleteTreatment")
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void DeleteTreatment(long NPI, long tid)throws ProviderNotFoundExp,TreatmentNotFoundExp,ProviderServiceExp;
	
	@WebMethod(operationName="ProvidersiteInfo")
	public String SiteInfo();
}
