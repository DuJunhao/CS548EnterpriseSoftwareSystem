package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentPdoToDto implements ITreatmentExporter<TreatmentDto> {
	private ObjectFactory factory = new ObjectFactory();
	private TreatmentDto dto;
	public TreatmentDto getDto()
	{
		return dto;
	}
	@Override
	public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug, float dosage) {
		// TODO Auto-generated method stub
		
		dto=factory.createTreatmentDto();//dto=new TreatmentDto();
		DrugTreatmentType drugInfo = factory.createDrugTreatmentType();//drugInfo=new DrugTreatmentType();
		drugInfo.setDosage(dosage);
		drugInfo.setName(drug);
		dto.setDrugTreatment(drugInfo);
		dto.setId(tid);
		dto.setDiagnosis(diagnosis);
		return dto;
	}

	@Override
	public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates) {
		// TODO Auto-generated method stub
		dto=factory.createTreatmentDto();
		RadiologyType radiologyInfo = factory.createRadiologyType();
		for(int i=0;i<dates.size();i++)
			radiologyInfo.getDates().add(dates.get(i));
		dto.setRadiology(radiologyInfo);
		dto.setId(tid);
		dto.setDiagnosis(diagnosis);
		return dto;
	}

	@Override
	public TreatmentDto exportSurgery(long tid, String diagnosis, Date date) {
		// TODO Auto-generated method stub
		dto=factory.createTreatmentDto();
		SurgeryType surgeryInfo = factory.createSurgeryType();
		surgeryInfo.setDate(date);
		dto.setSurgery(surgeryInfo);
		dto.setId(tid);
		dto.setDiagnosis(diagnosis);
		
		return dto;
	}

}
