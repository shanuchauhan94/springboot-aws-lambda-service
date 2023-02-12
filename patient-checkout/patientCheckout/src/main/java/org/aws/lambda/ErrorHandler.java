package org.aws.lambda;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    public void lambdaErrorHandler(SNSEvent event) {

        event.getRecords().forEach(snsRecord -> LOGGER.info("Dead Letter Queue Event : " + snsRecord.toString()));

    }
}
