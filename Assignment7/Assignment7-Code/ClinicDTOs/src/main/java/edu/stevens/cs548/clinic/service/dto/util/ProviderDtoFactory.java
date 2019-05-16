package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;

public class ProviderDtoFactory {
	ObjectFactory factory;
	
	public ProviderDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public ProviderDto createProviderDto () {
		return factory.createProviderDto();
	}
	
	public ProviderDto createPatientDto (Provider p) {
		ProviderDto d = factory.createProviderDto();
		/*
		 * TODO: Initialize the fields of the DTO.
		 */
		d.setId(p.getId());
		d.setNPI(p.getNPI());
		d.setName(p.getName());
		d.setSpec(p.getSpec());
		for(int i=0;i<p.getTreatmentIds().size();i++)
		{
			d.getTreatments().add(p.getTreatmentIds().get(i));
		}
		return d;
	}
}
