package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExp;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());
	
	/*
	 * TODO inject using HK2 (not CDI)
	 */
	@SuppressWarnings("unused")
    @Context
    private UriInfo uriInfo;
    
    private ProviderDtoFactory providerDtoFactory;
    private TreatmentDtoFactory treatmentDtoFactory;
    /**
     * Default constructor. 
     */
    public ProviderResource() {
		/*
		 * TODO finish this
		 */
    	providerDtoFactory=new ProviderDtoFactory();
    	treatmentDtoFactory=new TreatmentDtoFactory();
    }
    
	/*
	 * TODO inject using CDI
	 */
    @EJB(beanName="ProviderServiceBean")
    //@Inject
    private IProviderServiceLocal providerService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }

	/*
	 * TODO input should be application/xml
	 */
    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep) {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setNPI(Long.parseLong(providerRep.getId().getUrl()));
    		dto.setName(providerRep.getName());
    		dto.setSpec(providerRep.getSpec());
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{NPI}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExp e) {
    		throw new WebApplicationException("Created",201);
    	}
    }
    
	/**
	 * Query methods for patient resources.
	 */
	/*
	 * TODO output should be application/xml
	 */
    @GET
    @Path("{NPI}")
    @Produces("application/xml")
	public ProviderRepresentation getProvider(@PathParam("id") String NPI) {
		try {
			long key = Long.parseLong(NPI);
			ProviderDto providerDto = providerService.getProviderByProviderKey(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDto, uriInfo);
			return providerRep;
		} catch (ProviderServiceExp e) {
			throw new WebApplicationException("Not Found",404);
		}
	}
    
	/*
	 * TODO output should be application/xml
	 */
	 @GET
	 @Path("byNPI")
	 @Produces("application/xml")
	public ProviderRepresentation getProviderByNPI(@QueryParam("id") String NPI) {
		/*
		 * TODO finish this
		 */
		 try {
				long key = Long.parseLong(NPI);
				ProviderDto providerDto = providerService.getProviderByProviderNPI(key);
				ProviderRepresentation providerRep = new ProviderRepresentation(providerDto, uriInfo);
				return providerRep;
			} catch (ProviderServiceExp e) {
				throw new WebApplicationException("Not Found",404);
			}
	}
    
	/*
	 * TODO output should be application/xml
	 */
	 @GET
	 @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String NPI, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(NPI), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExp e) {
    		throw new WebApplicationException("Not Found",404);
    	}
    }
	 
	 @POST
	 @Path("id/treatments")
	 @Produces("application/xml")
	 public Response addProviderTreatmentForPatient(@PathParam("id") String NPI,@PathParam("tid") String tid) {
	    	try {
	    		/*
	    		 * To finish this
	    		 */
	    		TreatmentDto dto=treatmentDtoFactory.createTreatmentDto();
	    		dto = providerService.getTreatment(Long.parseLong(NPI), Long.parseLong(tid)); 
	    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(dto, uriInfo);
	    		dto.setDiagnosis(treatmentRep.getDiagnosis());
	    		long npi=Long.parseLong(NPI);
	    		long PID=dto.getPatient();
	    		long id=0;
	    		if(treatmentRep.getDrugTreatment()!=null)
	    			providerService.addDrugTreatment(dto, PID, npi, treatmentRep.getDiagnosis(), treatmentRep.getDrugTreatment().getName(),treatmentRep.getDrugTreatment().getDosage());
	    		else if(treatmentRep.getSurgery()!=null)
	    			providerService.addSurgery(dto, PID, npi, treatmentRep.getDiagnosis(), treatmentRep.getSurgery().getDate());
	    		else if(treatmentRep.getRadiology()!=null)
	    			providerService.addRadiology(dto, PID, npi, treatmentRep.getDiagnosis(), treatmentRep.getRadiology().getDate());
	    		
	    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
	    		URI url = ub.build(Long.toString(id));
	    		return Response.created(url).build();
	    	} catch (ProviderServiceExp e) {
	    		throw new WebApplicationException("Created",201);
	    	}
	    }
	 
}
