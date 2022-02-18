package com.eatzy.flash.processor;

import com.eatzy.flash.model.order.OrderDetailsDDBRecord;
import com.eatzy.flash.model.order.OrderStatus;
import com.eatzy.flash.model.profile.UserProfileDDBRecord;
import com.eatzy.flash.request.FetchNewOrderRequest;
import com.eatzy.flash.response.FetchNewOrderResponse;
import com.eatzy.flash.response.order.OrderMetaData;
import com.eatzy.flash.response.profile.UserMetaData;
import com.eatzy.flash.service.DynamoDBConnector;
import com.eatzy.flash.service.S3Connector;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.eatzy.flash.model.Constants.TABLE_NAME_OUTLET_DETAILS;
import static com.eatzy.flash.model.Constants.TABLE_NAME_USER_PROFILE;
import static com.eatzy.flash.model.order.OrderDetailsDDBRecord.SECONDARY_INDEX_OUTLET_ID;

public class FetchNewOrderProcessor implements  Processor<FetchNewOrderRequest, FetchNewOrderResponse>{
    DynamoDBConnector dynamoDBConnector;
    S3Connector s3Connector;

    @Inject
    public FetchNewOrderProcessor(DynamoDBConnector dynamoDBConnector, S3Connector s3Connector) {
        this.dynamoDBConnector =dynamoDBConnector;
        this.s3Connector =s3Connector;
    }

    public FetchNewOrderResponse process(FetchNewOrderRequest fetchNewOrderRequest){
        String outletID = fetchNewOrderRequest.getOutletID();
        List<OrderDetailsDDBRecord> orderDetailsDDBRecordList=
                dynamoDBConnector.filterNewOrders(SECONDARY_INDEX_OUTLET_ID, outletID);
        HashMap<OrderStatus, List<OrderMetaData>> orderStatusListHashMap = new HashMap<>();
        for(OrderDetailsDDBRecord orderDetailsDDBRecord : orderDetailsDDBRecordList){
            UserProfileDDBRecord userProfileDDBRecord = dynamoDBConnector.getItem(UserProfileDDBRecord.class,
                    TABLE_NAME_USER_PROFILE, UserProfileDDBRecord.USER_ID, orderDetailsDDBRecord.getGetUserId());
            UserMetaData userMetaData = UserMetaData.builder()
                    .userID(userProfileDDBRecord.getGetUserId())
                    .userName(userProfileDDBRecord.getGetUserName())
                    .userEmail(userProfileDDBRecord.getGetUserEmail())
                    .userMobile(userProfileDDBRecord.getGetUserMobile())
                    .build();

            OrderMetaData orderMetaData = OrderMetaData.builder()
                    .orderID(orderDetailsDDBRecord.getGetOrderId())
                    .orderAmount(orderDetailsDDBRecord.getGetOrderId())
                    .orderTime(orderDetailsDDBRecord.getGetCreateOrderTime())
                    .orderStatus(Enum.valueOf(OrderStatus.class, orderDetailsDDBRecord.getOrderStatus()))
                    .orderedMenuItemList(orderDetailsDDBRecord.getItemMapList())
                    .userId(orderDetailsDDBRecord.getGetUserId())
                    .userMetaData(userMetaData)
                    .build();

                List<OrderMetaData> orderMetaDataList = orderStatusListHashMap.getOrDefault(Enum.valueOf(OrderStatus.class, orderDetailsDDBRecord.getOrderStatus()),
                        new ArrayList<>());
                orderMetaDataList.add(orderMetaData);
                orderStatusListHashMap.put(Enum.valueOf(OrderStatus.class, orderDetailsDDBRecord.getOrderStatus()),
                        orderMetaDataList);
        }
        return  FetchNewOrderResponse.builder().orderStatusListHashMap(orderStatusListHashMap).build();
    }
}
