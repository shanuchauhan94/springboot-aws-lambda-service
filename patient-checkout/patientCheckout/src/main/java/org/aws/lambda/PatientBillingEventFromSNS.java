package org.aws.lambda;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aws.dto.PatientCheckoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatientBillingEventFromSNS {

    private final Logger logger = LoggerFactory.getLogger(PatientBillingEventFromSNS.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void listenSNSEvents(SNSEvent event) {

        logger.info("***************** listenSNSEvents ***************");
        event.getRecords().forEach(snsRecord -> {
            try {
                PatientCheckoutEvent patientCheckoutEvents = objectMapper.readValue(snsRecord.getSNS().getMessage(),
                        PatientCheckoutEvent.class);
                logger.info("patientCheckout SNS Events {} ", patientCheckoutEvents);
            } catch (JsonProcessingException e) {
                logger.error("JsonProcessingException In SNS Lambda Handler {} ", e.getMessage());
                throw new RuntimeException("JsonProcessingException In SNS Lambda Handler", e);
            }
        });

    }
}
