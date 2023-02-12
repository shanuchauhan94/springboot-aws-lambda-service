package org.aws.lambda;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClaimManagementLambda {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimManagementLambda.class);

    public void handleSQSEvent(SQSEvent event) {

        event.getRecords().forEach(message -> {
            LOGGER.info("SQS Events :: {} ", message.getBody());

        });

    }
}
