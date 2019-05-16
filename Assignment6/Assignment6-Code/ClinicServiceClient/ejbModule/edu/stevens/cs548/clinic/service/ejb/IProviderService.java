package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;


public interface IProviderService {
	public class ProviderServiceExp extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ProviderServiceExp(String message)
		{
			super(message);
		}
	}
	public class ProviderNotFoundExp extends ProviderServiceExp {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ProviderNotFoundExp(String m)
		{
			super(m);
		}
	}
	public class TreatmentNotFoundExp extends ProviderServiceExp {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TreatmentNotFoundExp(String m)
		{
			super(m);
		}
	}
	public long addProvider(ProviderDto dto) throws ProviderServiceExp;
	public long createProvider(String name, String spec,long NPI) throws ProviderServiceExp;
	public ProviderDto getProviderByProviderNPI(long NPI) throws ProviderServiceExp;
	public ProviderDto getProviderByProviderKey(long id) throws ProviderServiceExp;
	public ProviderDto[] getProvidersByName(String name);
	public void deleteProvider(String name, long NPI) throws ProviderServiceExp;
	
	public void addDrugTreatment(TreatmentDto treatmentDto,long NPI,String diagnosis,String drug,float dosage) throws ProviderServiceExp;
	public void addSurgery(TreatmentDto treatmentDto,long NPI, Date date,String diagnosis) throws ProviderServiceExp;
	public void addRadiology(TreatmentDto treatmentDto,long NPI,String diagnosis,List<Date> dates) throws ProviderServiceExp;
	
	//public TreatmentDto getTreatment(long NPI,long tid)throws ProviderNotFoundExp,TreatmentNotFoundExp,ProviderServiceExp;
	//public TreatmentDto[] getTreatments(long NPI,long[]tids)throws ProviderNotFoundExp,TreatmentNotFoundExp,ProviderServiceExp;
	
	public void deleteTreatment(long NPI, long tid)throws ProviderNotFoundExp,TreatmentNotFoundExp,ProviderServiceExp;
	public String siteInfo();
}
