package edu.stevens.cs548.clinic.service.representations;

import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.web.rest.data.DrugTreatmentType;

public class DrugTreatmentRepresentation extends DrugTreatmentType {

	public DrugTreatmentRepresentation(TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.dosage = dto.getDrugTreatment().getDosage();
		this.name = dto.getDrugTreatment().getName();
	}

	public DrugTreatmentRepresentation() {
		super();
	}

}
