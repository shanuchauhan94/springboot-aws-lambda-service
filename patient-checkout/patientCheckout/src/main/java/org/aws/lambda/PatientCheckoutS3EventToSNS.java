package org.aws.lambda;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aws.dto.PatientCheckoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientCheckoutS3EventToSNS {

    private Logger logger = LoggerFactory.getLogger(PatientCheckoutS3EventToSNS.class);
    private static final String PATIENT_CHECKOUT_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");
    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void pushEventToS3(S3Event event) throws JsonProcessingException {

        logger.info("************* pushEventToS3 *************");
        List<PatientCheckoutEvent> recordsList = new ArrayList<>();
        event.getRecords().forEach(record -> {
            S3ObjectInputStream s3InputStream = s3.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey()).getObjectContent();
            try {
                recordsList.addAll(Arrays.asList(objectMapper.readValue(s3InputStream, PatientCheckoutEvent[].class)));
                logger.info("recordsList {} ", recordsList);
                pushEventToSNS(recordsList);
                s3InputStream.close();
            } catch (IOException e) {
                logger.error("IOException {} ", e.getMessage());
                throw new RuntimeException("IOException", e);
            }
        });
    }

    private void pushEventToSNS(List<PatientCheckoutEvent> recordsList) {

        recordsList.forEach(events -> {
            try {
                sns.publish(PATIENT_CHECKOUT_TOPIC, objectMapper.writeValueAsString(events));
                logger.info("SNS Events Published.");
            } catch (JsonProcessingException e) {
                logger.error("JsonProcessingException {} ", e.getMessage());
                throw new RuntimeException("sonProcessingException", e);
            }
        });
    }

}
