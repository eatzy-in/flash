package com.eatzy.flash.processor;

import com.eatzy.flash.model.order.ItemMap;
import com.eatzy.flash.model.order.OrderDetailsDDBRecord;
import com.eatzy.flash.model.order.OrderStatus;
import com.eatzy.flash.request.OrderCreateRequest;
import com.eatzy.flash.response.OrderCreateResponse;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderProcessor {
    private final DynamoDBConnector dynamoDBConnector;

    @Inject OrderProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public OrderCreateResponse process(OrderCreateRequest orderCreateRequest) {
        try {
            System.out.println(orderCreateRequest);
            String orderId = createOrderId();
            OrderDetailsDDBRecord orderDetailsDDBRecord = OrderDetailsDDBRecord.builder().getOrderId(orderId)
                    .getOutletId(orderCreateRequest.getOutletID())
                    .getPrice(orderCreateRequest.getOrderAmount())
                    .getUserId(orderCreateRequest.getUserID())
                    .itemMapList(getItemMap(orderCreateRequest.getItemMap()))
                    .orderStatus(OrderStatus.ORDER_CREATED.name())
                    .getCreateOrderTime(String.valueOf(Instant.now().toEpochMilli())).build();
            dynamoDBConnector.save(orderDetailsDDBRecord);
            return OrderCreateResponse.builder().orderID(orderId).orderStatus(OrderStatus.ORDER_CREATED).build();
        } catch (Exception exception) {
            System.out.println("exception while creating orderr : " + exception);
        }
        return OrderCreateResponse.builder().orderID("").orderStatus(OrderStatus.ORDER_FAILED).build();
    }

    private String createOrderId(){
        return UUID.randomUUID().toString();
    }


    private List<ItemMap> getItemMap(HashMap<String, Integer> itemMap){
        List<ItemMap> itemMaps = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: itemMap.entrySet()){
            itemMaps.add(new ItemMap(entry.getKey(), entry.getValue()));
        }
        return itemMaps;
    }
}
