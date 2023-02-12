package com.aws.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GetOrdersLambda {

   private final ObjectMapper objectMapper = new ObjectMapper();
    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();

    public APIGatewayProxyResponseEvent getOrders(APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {

        ScanResult scanResult = dynamoDB.scan(new ScanRequest()
                .withTableName(System.getenv("ORDERS_TABLE")));
        List<Order> ordersList = scanResult.getItems()
                .stream()
                .map(item -> new Order(Integer.parseInt(item.get("id").getN()),
                        item.get("itemName").getS(), Integer.parseInt(item.get("quantity").getN())))
                .collect(Collectors.toList());
        String orders = objectMapper.writeValueAsString(ordersList);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(orders);
    }
}
