package edu.stevens.cs548.clinic.service.representations;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.ObjectFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;

@XmlRootElement(name="treatment",namespace=Representation.NAMESPACE)
public class TreatmentRepresentation extends TreatmentType {
	
	private ObjectFactory repFactory = new ObjectFactory();
	edu.stevens.cs548.clinic.service.dto.ObjectFactory  factory;
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
		/*
		 * TODO: Need to fill in provider information.
		 */
		this.provider = ProviderRepresentation.getProviderLink(dto.getProvider(), uriInfo);
		
		this.diagnosis = dto.getDiagnosis();
		
		if (dto.getDrugTreatment() != null) {
			/*
			 * TODO finish this
			 */
			this.drugTreatment.setDosage(dto.getDrugTreatment().getDosage());
			this.drugTreatment.setName(dto.getDrugTreatment().getName());
		} else if (dto.getSurgery() != null) {
			/*
			 * TODO finish this
			 */
			this.surgery.setDate(dto.getSurgery().getDate());
		} else if (dto.getRadiology() != null) {
			/*
			 * TODO finish this
			 */
			for(int i=0;i<dto.getRadiology().getDates().size();i++)
				this.radiology.getDate().add(dto.getRadiology().getDates().get(i));
		}
	}

	public TreatmentDto getTreatment() {
		TreatmentDto m = null;
		if (this.getDrugTreatment() != null) {
			m = treatmentDtoFactory.createTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			/*
			 * TODO finish this
			 */
			DrugTreatmentType DrugTreatmentInfo=factory.createDrugTreatmentType();
			DrugTreatmentInfo.setDosage(this.getDrugTreatment().getDosage());
			DrugTreatmentInfo.setName(this.getDrugTreatment().getName());
			m.setDrugTreatment(DrugTreatmentInfo);
		} else if (this.getSurgery() != null) {
			/*
			 * TODO finish this
			 */
			m = treatmentDtoFactory.createTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			
			SurgeryType surgeryInfo=factory.createSurgeryType();
			surgeryInfo.setDate(this.getSurgery().getDate());
			m.setSurgery(surgeryInfo);
		} else if (this.getRadiology() != null) {
			/*
			 * TODO finish this
			 */
			m = treatmentDtoFactory.createTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			
			RadiologyType radiologyInfo=factory.createRadiologyType();
			for(int i=0;i<this.getRadiology().getDate().size();i++)
				radiologyInfo.getDates().add(this.getRadiology().getDate().get(i));
			m.setRadiology(radiologyInfo);
		}
		return m;
	}

	
}
