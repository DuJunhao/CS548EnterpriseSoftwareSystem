package edu.stevens.cs548.clinic.billing.jms;

import java.util.Calendar;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.billing.service.BillingDtoFactory;
import edu.stevens.cs548.clinic.billing.service.IBillingService.BillingDTO;
import edu.stevens.cs548.clinic.billing.service.IBillingServiceLocal;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "Treatment"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/Treatment")
public class TreatmentListener implements MessageListener {

    /**
     * Default constructor. 
     */
    private BillingDtoFactory billingDtoFactory;
    
    public TreatmentListener() {
        // TODO Auto-generated constructor stub
    	billingDtoFactory = new BillingDtoFactory();
    }
    
    @Inject 
    private IBillingServiceLocal billingService;
    
	private static Logger logger = Logger.getLogger(TreatmentListener.class.getCanonicalName());
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	logger.info("Message received");
        ObjectMessage objMessage = (ObjectMessage)message;
        try {
        	TreatmentDto treatment = (TreatmentDto)objMessage.getObject();
        	BillingDTO dto = billingDtoFactory.createBillingDTO();
        	dto.setTreatmentId(treatment.getId());
        	Random generator = new Random();
        	float amount = generator.nextFloat() * 500;
    		dto.setDescription(treatment.getDiagnosis());
    		Calendar calendar = Calendar.getInstance();
    		dto.setDate(calendar.getTime());
    		dto.setAmount(amount);
        	billingService.addBillingRecord(dto);
        }
        catch(JMSException e) {
        	
        }
    }

}
