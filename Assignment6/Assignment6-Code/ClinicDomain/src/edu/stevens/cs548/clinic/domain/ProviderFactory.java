package edu.stevens.cs548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long NPI, String name, String spec) {
		// TODO Auto-generated method stub
		Provider p=new Provider();
		p.setNPI(NPI);
		p.setName(name);
		p.setSpec(spec);
		return p;
	}

}
