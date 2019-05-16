package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentFactory implements ITreatmentFactory {

	

	@Override
	public Treatment createRadiology(String diagnosis, List<Date> dates) {
		// TODO Auto-generated method stub
		Radiology radiology=new Radiology();
		radiology.setDates(dates);
		radiology.setDiagnosis(diagnosis);
		radiology.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
		return null;
	}

	@Override
	public Treatment createSurgery(String diagnosis, Date date) {
		// TODO Auto-generated method stub
		Surgery surgery=new Surgery();
		surgery.setDate(date);
		surgery.setDiagnosis(diagnosis);
		surgery.setTreatmentType(TreatmentType.SURGERY.getTag());
		return null;
	}

	@Override
	public Treatment createDrugTreatment(String diagnosis, String drug, float dosage) {
		// TODO Auto-generated method stub
		DrugTreatment treatment = new DrugTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		treatment.setTreatmentType(TreatmentType.DRUG_TREATMENT.getTag());
		return null;
	}
	
}
