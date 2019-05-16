package edu.stevens.cs548.clinic.domain;

public interface IProviderFactory {
	
	public Provider createProvider(String spec, long NPI) throws IProviderDAO.ProviderExn;

}
