package edu.stevens.cs548.clinic.research.jms;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.billing.service.IResearchService;
import edu.stevens.cs548.clinic.billing.service.IResearchService.DrugTreatmentDTO;
import edu.stevens.cs548.clinic.research.service.DrugTreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: DrugTreatmentListener
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "Treatment"),
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(
				propertyName = "treatmetnType", propertyValue = "Drug")
		}, 
		mappedName = "jms/Treatment")
public class DrugTreatmentListener implements MessageListener {
	
	private DrugTreatmentDtoFactory drugTreatmentDtoFactory;

    /**
     * Default constructor. 
     */
    public DrugTreatmentListener() {
    	drugTreatmentDtoFactory = new DrugTreatmentDtoFactory();
    }
	
    @Inject
    private IResearchService researchService;
    
	private static Logger logger = Logger.getLogger(DrugTreatmentListener.class.getCanonicalName());

	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	ObjectMessage objMessage = (ObjectMessage)message;
    	logger.info("DrugTeratmentListener: received Message");
    	try {
        	TreatmentDto treatment = (TreatmentDto)objMessage.getObject();
        	
        	if(treatment.getDrugTreatment() == null) {
        		
        	}
        	else {
        		DrugTreatmentDTO dto = drugTreatmentDtoFactory.createDrugTreatmentDTO();
        		Calendar calendar = Calendar.getInstance();
        		dto.setDate(calendar.getTime());
        		dto.setDosage(treatment.getDrugTreatment().getDosage());
        		dto.setDrugName(treatment.getDrugTreatment().getName());
        		dto.setPatientId(treatment.getPatient());
        		dto.setTreatmentId(treatment.getId());
        		researchService.addDrugTreatmentRecord(dto);
        	}
    	}
    	catch (JMSException e) {
    		
    	}
    }

}
