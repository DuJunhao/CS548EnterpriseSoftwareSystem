package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(PatientResource.class.getCanonicalName());

    @Context
    private UriInfo uriInfo;
    
    private ProviderDtoFactory providerDtoFactory;

    /**
     * Default constructor. 
     */
    public ProviderResource() {
    	providerDtoFactory = new ProviderDtoFactory();
    }
    
    @Inject
    private IProviderServiceLocal providerService;

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public ProviderRepresentation getProvider(@PathParam("id") String id) {
        try {
        	long key = Long.parseLong(id);
			ProviderDto dto = providerService.getProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(dto, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
    }
    
    @GET
    @Path("byNPI")
    @Produces("application/xml")
    public ProviderRepresentation getProviderByNPI(@QueryParam("id") String id) {
        try {
        		long key = Long.parseLong(id);
        		logger.info("key = "+key);
			ProviderDto dto = providerService.getProviderByNPI(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(dto, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
    }
    
    @GET
    @Path("{id}/treatment/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
        try {
        	TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid));
        	logger.info("treatment : "+ treatment.getDiagnosis() + treatment.getId());
        	TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
        	logger.info("treatmentRep : "+ treatmentRep.getDiagnosis() + treatmentRep.getId());
        	return treatmentRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
    }

    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep) {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
        	dto.setNPI(providerRep.getNPI());
        	dto.setSpecialization(providerRep.getSpecialization());
			long id = providerService.addProvider(dto);
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(Long.toString(id));
			return Response.created(url).build();
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.CREATED);
		}
    }

    @POST
    @Path("{id}/treatment")
    @Consumes("application/xml")
	public Response addTreatment(@PathParam("id") String id, TreatmentRepresentation treatmentRep) {
		try {
			TreatmentDto treatment = treatmentRep.getTreatment();
			treatment.setProvider(Long.parseLong(id));
			long tid = providerService.addTreatment(treatment);
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(tid);
			return Response.created(url).build();
		} catch (PatientNotFoundExn | ProviderNotFoundExn e) {
			throw new WebApplicationException(Response.Status.CREATED);
		}
	}
}