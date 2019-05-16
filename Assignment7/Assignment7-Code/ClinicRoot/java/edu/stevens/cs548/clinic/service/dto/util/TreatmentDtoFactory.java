package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Surgery;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;

	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createTreatmentDto () {
		// TODO
		return factory.createTreatmentDto();
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		/*
		 * TODO: Initialize the DTO from the drug treatment.
		 */
		TreatmentDto d = factory.createTreatmentDto();
		DrugTreatmentType DrugTreatmentInfo=factory.createDrugTreatmentType();
		DrugTreatmentInfo.setDosage(t.getDosage());
		DrugTreatmentInfo.setName(t.getDrug());
		d.setDiagnosis(t.getDiagnosis());
		d.setId(t.getId());
		d.setPatient(t.getPatient().getId());
		d.setProvider(t.getProvider().getNPI());
		d.setDrugTreatment(DrugTreatmentInfo);
		return d;
	}
	
	/*
	 * TODO: Repeat for other treatments.
	 */
	public TreatmentDto createTreatmentDto (Radiology t) {
		/*
		 * TODO: Initialize the DTO from the Radiology.
		 */
		TreatmentDto d = factory.createTreatmentDto();
		RadiologyType radiologyInfo=factory.createRadiologyType();
		for(int i=0;i<t.getDates().size();i++)
			radiologyInfo.getDates().add(t.getDates().get(i));
		d.setDiagnosis(t.getDiagnosis());
		d.setId(t.getId());
		d.setPatient(t.getPatient().getId());
		d.setProvider(t.getProvider().getNPI());getClass();
		d.setRadiology(radiologyInfo);
		return d;
	}
	
	public TreatmentDto createTreatmentDto (Surgery t) {
		/*
		 * TODO: Initialize the DTO from the Surgery.
		 */
		TreatmentDto d = factory.createTreatmentDto();
		SurgeryType surgeryInfo=factory.createSurgeryType();
		surgeryInfo.setDate(t.getDate());
		d.setDiagnosis(t.getDiagnosis());
		d.setId(t.getId());
		d.setPatient(t.getPatient().getId());
		d.setProvider(t.getProvider().getNPI());
		d.setSurgery(surgeryInfo);
		return d;
	}
}
