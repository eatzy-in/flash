package com.eatzy.flash.processor;

import com.eatzy.flash.model.order.OrderDetailsDDBRecord;
import com.eatzy.flash.model.order.OrderStatus;
import com.eatzy.flash.request.FetchOrderUpdateRequest;
import com.eatzy.flash.response.order.FetchOrderUpdateResponse;
import com.eatzy.flash.response.order.OrderUpdateMetaData;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.eatzy.flash.model.Constants.TABLE_NAME_ORDER_DETAILS;

public class FetchOrderUpdateProcessor implements Processor<FetchOrderUpdateRequest, FetchOrderUpdateResponse>{

    private final DynamoDBConnector dynamoDBConnector;

    @Inject FetchOrderUpdateProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }


    public FetchOrderUpdateResponse process(FetchOrderUpdateRequest request){
        List<String> orderIDList= request.getOrderIDList();
        List<OrderUpdateMetaData> orderUpdateMetaDataList = new ArrayList<>();

        for(String orderId: orderIDList){
            System.out.println(orderId);
            OrderDetailsDDBRecord orderDetailsDDBRecord =  dynamoDBConnector.getItem(OrderDetailsDDBRecord.class,
                    TABLE_NAME_ORDER_DETAILS, OrderDetailsDDBRecord.ORDER_ID, orderId);

            OrderUpdateMetaData orderUpdateMetaData = OrderUpdateMetaData.builder()
                    .orderID(orderId)
                    .orderStatus(Enum.valueOf(OrderStatus.class, orderDetailsDDBRecord.getOrderStatus()))
                    .build();
            orderUpdateMetaDataList.add(orderUpdateMetaData);

        }
        return FetchOrderUpdateResponse.builder().orderUpdateMetaDataList(orderUpdateMetaDataList).build();

    }
}
