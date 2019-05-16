package edu.stevens.cs548.clinic.service.representations;

import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.web.rest.data.SurgeryType;

public class SurgeryRepresentation extends SurgeryType {
	
	public SurgeryRepresentation(TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.date = dto.getSurgery().getDate();
	}

	public SurgeryRepresentation() {
		super();
	}

}
