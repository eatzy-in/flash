package com.eatzy.flash.processor;

import com.eatzy.flash.model.order.OrderDetailsDDBRecord;
import com.eatzy.flash.request.OrderUpdateRequest;
import com.eatzy.flash.response.OrderUpdateResponse;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;

import static com.eatzy.flash.model.Constants.TABLE_NAME_ORDER_DETAILS;

public class PostOrderUpdateProcessor implements Processor<OrderUpdateRequest, OrderUpdateResponse> {

    private final DynamoDBConnector dynamoDBConnector;

    @Inject PostOrderUpdateProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public OrderUpdateResponse process(OrderUpdateRequest orderUpdateRequest) {
        try {
            System.out.println(orderUpdateRequest);
            String orderId = orderUpdateRequest.getOrderId();
            dynamoDBConnector.updateItem(OrderDetailsDDBRecord.class, TABLE_NAME_ORDER_DETAILS,
                    OrderDetailsDDBRecord.ORDER_ID, orderId, OrderDetailsDDBRecord.ORDER_STATUS, orderUpdateRequest.getOrderStatus().name());
            return OrderUpdateResponse.builder().orderStatus(orderUpdateRequest.getOrderStatus()).build();
        } catch (Exception exception) {
            System.out.println("exception while creating order : " + exception);
            throw exception;
        }
    }
}
