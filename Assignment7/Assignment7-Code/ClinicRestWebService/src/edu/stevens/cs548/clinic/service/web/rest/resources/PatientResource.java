package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExp;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.representations.PatientRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/patient")
@RequestScoped
public class PatientResource {

	final static Logger logger = Logger.getLogger(PatientResource.class.getCanonicalName());

	/*
	 * TODO inject using HK2 (not CDI)
	 */
	@SuppressWarnings("unused")
	@Context
	private UriInfo uriInfo;

	private PatientDtoFactory patientDtoFactory;

	/**
	 * Default constructor.
	 */
	public PatientResource() {
		/*
		 * TODO finish this
		 */
		patientDtoFactory = new PatientDtoFactory();
	}

	/*
	 * TODO inject using CDI
	 */
	//@EJB(beanName="PatientServiceBean")
	@Inject
	private IPatientServiceLocal patientService;

	@GET
	@Path("site")
	@Produces("text/plain")
	public String getSiteInfo() {
		return patientService.siteInfo();
	}

	/*
	 * TODO input should be application/xml
	 */
	@POST
	@Consumes("application/xml")
	public Response addPatient(PatientRepresentation patientRep) {
		try {
			PatientDto dto = patientDtoFactory.createPatientDto();
			dto.setPatientId(patientRep.getPatientId());
			dto.setName(patientRep.getName());
			dto.setDob(patientRep.getDob());
			dto.setAge(patientRep.getAge());
			long id = patientService.addPatient(dto);
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(Long.toString(id));
			return Response.created(url).build();
		} catch (PatientServiceExp e) {
			throw new WebApplicationException(Response.Status.CREATED);
		}
	}
	/**
	 * Query methods for patient resources.
	 */
	/*
	 * TODO output should be application/xml
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public PatientRepresentation getPatient(@PathParam("id") String id) {
		try {
			long key = Long.parseLong(id);
			PatientDto patientDTO = patientService.getPatientByDbId(key);
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, uriInfo);
			return patientRep;
		} catch (PatientServiceExp e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	/*
	 * TODO output should be application/xml
	 */
	@GET
	@Path("byPatientId")
	@Produces("application/xml")
	public PatientRepresentation getPatientByPatientId(@QueryParam("id") String patientId) {
		/*
		 * TODO finish this
		 */
		try {
			long pid = Long.parseLong(patientId);
			PatientDto patientDTO = patientService.getPatientByPatientId(pid);
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, uriInfo);
			return patientRep;
		} catch (PatientServiceExp e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	/*
	 * TODO output should be application/xml
	 */
	@GET
	@Path("nameDob")
	@Produces("application/xml")
	public PatientRepresentation[] getPatientByNameDob(@QueryParam("name") String name, @QueryParam("dob") String dob) {
		Date birthdate = DatatypeConverter.parseDate(dob).getTime();
		PatientDto[] patientDtos = patientService.getPatientsByNameDob(name, birthdate);
		PatientRepresentation[] patientReps = new PatientRepresentation[patientDtos.length];
		for (int i = 0; i < patientDtos.length; i++) {
			patientReps[i] = new PatientRepresentation(patientDtos[i], uriInfo);
		}
		return patientReps;
	}

	/*
	 * TODO output should be application/xml
	 */
	@GET
	@Path("treatments")
	@Produces("application/xml")
	public TreatmentRepresentation getPatientTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
		try {
			TreatmentDto treatment = patientService.getTreatment(Long.parseLong(id), Long.parseLong(tid));
			TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
			return treatmentRep;
		} catch (PatientServiceExp e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}


}