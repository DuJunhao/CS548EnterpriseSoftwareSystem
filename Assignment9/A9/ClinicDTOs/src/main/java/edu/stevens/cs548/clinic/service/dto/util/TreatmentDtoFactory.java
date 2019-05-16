package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.RadiologyTreatment;
import edu.stevens.cs548.clinic.domain.SurgeryTreatment;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createDrugTreatmentDto () {
		TreatmentDto drugT = factory.createTreatmentDto();
		drugT.setDrugTreatment(factory.createDrugTreatmentType());
		return drugT;
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		TreatmentDto drugTrmtDTO = this.createDrugTreatmentDto();
		drugTrmtDTO.setDiagnosis(t.getDiagnosis());
		drugTrmtDTO.setId(t.getId());
		drugTrmtDTO.setPatient(t.getPatient().getId());
		drugTrmtDTO.setProvider(t.getProvider().getId());
		drugTrmtDTO.getDrugTreatment().setDosage(t.getDosage());
		drugTrmtDTO.getDrugTreatment().setName(t.getDrug());
		return drugTrmtDTO;
	}
	
	public TreatmentDto createSurgeryDto () {
		TreatmentDto surgeryT = factory.createTreatmentDto();
		surgeryT.setSurgery(factory.createSurgeryType());
		return surgeryT;
	}
	
	public TreatmentDto createTreatmentDto (SurgeryTreatment t) {
		TreatmentDto surgTrmtDTO = this.createDrugTreatmentDto();
		surgTrmtDTO.setDiagnosis(t.getDiagnosis());
		surgTrmtDTO.setId(t.getId());
		surgTrmtDTO.setPatient(t.getPatient().getId());
		surgTrmtDTO.setProvider(t.getProvider().getId());
		surgTrmtDTO.getSurgery().setDate(t.getDate());
		return surgTrmtDTO;
	}
	
	public TreatmentDto createRadiologyDto () {
		TreatmentDto radiologyT = factory.createTreatmentDto();
		radiologyT.setRadiology(factory.createRadiologyType());;
		return radiologyT;
	}
	
	public TreatmentDto createTreatmentDto (RadiologyTreatment t) {
		TreatmentDto radiologyTrmtDTO = this.createDrugTreatmentDto();
		radiologyTrmtDTO.setDiagnosis(t.getDiagnosis());
		radiologyTrmtDTO.setId(t.getId());
		radiologyTrmtDTO.setPatient(t.getPatient().getId());
		radiologyTrmtDTO.setProvider(t.getProvider().getId());
		radiologyTrmtDTO.getRadiology().getDate().addAll(t.getDates());
		return radiologyTrmtDTO;
	}

}
