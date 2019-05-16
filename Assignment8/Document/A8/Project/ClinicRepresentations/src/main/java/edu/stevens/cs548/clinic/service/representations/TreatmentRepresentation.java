package edu.stevens.cs548.clinic.service.representations;

import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.ObjectFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;

@XmlRootElement
public class TreatmentRepresentation extends TreatmentType {
	
	private ObjectFactory repFactory = new ObjectFactory();
	
	private static Logger logger = Logger.getLogger(TreatmentRepresentation.class.getCanonicalName());
	
	public LinkType getLinkPatient() {
		return this.getPatient();
	}
	
	public LinkType getLinkProvider() {
		return this.getProvider();
	}
	
	public static LinkType getTreatmentLink(long tid, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		UriBuilder ubTreatment = ub.clone().path("{tid}");
		String treatmentURI = ubTreatment.build(Long.toString(tid)).toString();
	
		LinkType link = new LinkType();
		link.setUrl(treatmentURI);
		link.setRelation(Representation.RELATION_TREATMENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	private TreatmentDtoFactory treatmentDtoFactory;
	
	public TreatmentRepresentation() {
		super();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}
	
	public TreatmentRepresentation (TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.id = getTreatmentLink(dto.getId(), uriInfo);
		this.patient =  PatientRepresentation.getPatientLink(dto.getPatient(), uriInfo);
		this.provider = ProviderRepresentation.getProviderLink(dto.getProvider(), uriInfo);
		
		this.diagnosis = dto.getDiagnosis();
		
		if (dto.getDrugTreatment() != null) {
			this.drugTreatment = new DrugTreatmentRepresentation(dto,uriInfo);
		} else if (dto.getSurgery() != null) {
			this.surgery = new SurgeryRepresentation(dto,uriInfo);
		} else if (dto.getRadiology() != null) {
			this.radiology = new RadiologyRepresentation(dto,uriInfo);
		}
	}

	public TreatmentDto getTreatment() {
		TreatmentDto m = null;
		if (this.getDrugTreatment() != null) {
			logger.info("start create dto");
			m = treatmentDtoFactory.createDrugTreatmentDto();
			if(Representation.getId(patient) != null) {
				logger.info("getting patient");
				m.setPatient(Representation.getId(patient));
			}
			if(Representation.getId(provider) != null) {
				logger.info("getting provider");
				m.setProvider(Representation.getId(provider));
			}
			logger.info("getting diagnosis");
			m.setDiagnosis(diagnosis);
			logger.info("getting dosage");
			m.getDrugTreatment().setDosage(this.drugTreatment.getDosage());
			logger.info("getting drugname");
			m.getDrugTreatment().setName(this.drugTreatment.getName());
		} else if (this.getSurgery() != null) {
			m = treatmentDtoFactory.createSurgeryDto();
//			if(Representation.getId(id) != null) {
//				m.setId(Representation.getId(id));
//			}
			if(Representation.getId(patient) != null) {
				m.setPatient(Representation.getId(patient));
			}
			if(Representation.getId(provider) != null) {
				m.setProvider(Representation.getId(provider));
			}
			m.setDiagnosis(diagnosis);
			m.getSurgery().setDate(this.surgery.getDate());
		} else if (this.getRadiology() != null) {
			m = treatmentDtoFactory.createSurgeryDto();
//			if(Representation.getId(id) != null) {
//				m.setId(Representation.getId(id));
//			}
			if(Representation.getId(patient) != null) {
				m.setPatient(Representation.getId(patient));
			}
			if(Representation.getId(provider) != null) {
				m.setProvider(Representation.getId(provider));
			}
			m.setDiagnosis(diagnosis);
			for(Date d : this.radiology.getDate()) {
				m.getRadiology().getDate().add(d);
			}
		}
		return m;
	}

	
}
