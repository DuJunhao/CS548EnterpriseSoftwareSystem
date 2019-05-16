package edu.stevens.cs548.clinic.domain.DAO;

import java.util.List;

import edu.stevens.cs548.clinic.domain.Provider;
public interface IProviderDAO {
	public static class ProviderExp extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;	
		public ProviderExp(String msg)
		{
			super(msg);
		}
	}
	public Provider getProviderByDbId(long Id) throws ProviderExp;
	public Provider getProviderByNPI(long NPI) throws ProviderExp;
	public List<Provider> getProviderByName(String name);
	public void addProvider(Provider p) throws ProviderExp;
	public void deleteProvider(Provider p) throws ProviderExp;
}
