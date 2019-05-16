package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
	
	private ObjectFactory factory = new ObjectFactory();
	
	@Override
	public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug,
			float dosage, long patient, long provider) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(diagnosis);
		DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
		drugInfo.setDosage(dosage);
		drugInfo.setName(drug);
		dto.setDrugTreatment(drugInfo);
		dto.setPatient(patient);
		dto.setProvider(provider);
		dto.setId(tid);
		return dto;
	}

	@Override
	public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates, long patient, long provider) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(diagnosis);
		RadiologyType RadiologyInfo = factory.createRadiologyType();
		RadiologyInfo.getDate().addAll(dates);
		dto.setRadiology(RadiologyInfo);
		dto.setPatient(patient);
		dto.setProvider(provider);
		dto.setId(tid);
		return dto;
	}

	@Override
	public TreatmentDto exportSurgery(long tid, String diagnosis, Date date, long patient, long provider) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(diagnosis);
		SurgeryType SurgeryInfo = factory.createSurgeryType();
		SurgeryInfo.setDate(date);
		dto.setSurgery(SurgeryInfo);
		dto.setPatient(patient);
		dto.setProvider(provider);
		dto.setId(tid);
		return dto;
	}
}