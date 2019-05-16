package edu.stevens.cs548.clinic.service.representations;

import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.web.rest.data.RadiologyType;

public class RadiologyRepresentation extends RadiologyType {
	
	public RadiologyRepresentation(TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.date = dto.getRadiology().getDate();
	}

	public RadiologyRepresentation() {
		super();
	}

}
