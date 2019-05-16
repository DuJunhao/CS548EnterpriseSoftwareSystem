package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(String spec, long NPI) throws ProviderExn {
		Provider p = new Provider();
		p.setNPI(NPI);
		p.setSpecialization(spec);
		return p;
	}

}
