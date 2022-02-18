package com.eatzy.flash.processor;

import com.eatzy.flash.model.menu.MenuDDBRecord;
import com.eatzy.flash.model.order.ItemMap;
import com.eatzy.flash.model.order.OrderDetailsDDBRecord;
import com.eatzy.flash.model.order.OrderStatus;
import com.eatzy.flash.model.outlet.OutletDDBRecord;
import com.eatzy.flash.request.OrderHistoryRequest;
import com.eatzy.flash.response.order.OrderHistoryResponse;
import com.eatzy.flash.response.order.OrderMetaData;
import com.eatzy.flash.response.order.OrderedMenuItem;
import com.eatzy.flash.service.DynamoDBConnector;
import com.eatzy.flash.service.S3Connector;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.eatzy.flash.model.Constants.S3Constants.S3_BUCKET_NAME_FOR_OUTLETS;
import static com.eatzy.flash.model.Constants.S3Constants.S3_PREFIX_NAME_FOR_OUTLETS_ICON_TEMPLATE;
import static com.eatzy.flash.model.Constants.*;

public class OrderHistoryProcessor implements Processor<OrderHistoryRequest, OrderHistoryResponse> {
    private final DynamoDBConnector dynamoDBConnector;
    private final S3Connector s3Connector;
    private static final String USER_ID_ATTRIBUTE_VALUE = "userID";
    private static final String ORDER_DETAILS_SECONDARY_KEY = "userID-index";

    @Inject OrderHistoryProcessor(DynamoDBConnector dynamoDBConnector,
            S3Connector s3Connector) {
        this.dynamoDBConnector = dynamoDBConnector;
        this.s3Connector = s3Connector;
    }

    public OrderHistoryResponse process(OrderHistoryRequest orderHistoryRequest) {
        String userId = orderHistoryRequest.getUserId();

        System.out.println(userId);
        List<OrderDetailsDDBRecord> orderDetailsDDBRecords = dynamoDBConnector
                .getAllItemUsingGSI(OrderDetailsDDBRecord.class,
                        TABLE_NAME_ORDER_DETAILS,
                        ORDER_DETAILS_SECONDARY_KEY,
                        USER_ID_ATTRIBUTE_VALUE,
                        userId
                );
        List<OrderMetaData> orderMetaDataList = new ArrayList<>();
        for (OrderDetailsDDBRecord orderDetailsDDBRecord : orderDetailsDDBRecords) {
            String outletID = orderDetailsDDBRecord.getGetOutletId();
            OutletDDBRecord getOutletDetails = dynamoDBConnector.getItem(OutletDDBRecord.class, TABLE_NAME_OUTLET_DETAILS, OutletDDBRecord.OUTLET_ID, outletID);
            List<String> iconTemplate = s3Connector.getImageURIList(
                    S3_BUCKET_NAME_FOR_OUTLETS,
                    String.format(S3_PREFIX_NAME_FOR_OUTLETS_ICON_TEMPLATE, outletID));
            OrderMetaData orderMetaData = OrderMetaData.builder()
                    .orderID(orderDetailsDDBRecord.getGetOrderId())
                    .orderAmount(orderDetailsDDBRecord.getGetPrice())
                    .orderTime(orderDetailsDDBRecord.getGetCreateOrderTime())
                    .outletImageLogo(iconTemplate.get(0))
                    .orderedMenuItemList(orderDetailsDDBRecord.getItemMapList())
                    .outletName(getOutletDetails.getName())
                    .outletID(outletID)
                    .orderStatus(Enum.valueOf(OrderStatus.class, orderDetailsDDBRecord.getOrderStatus()))
                    .build();
            orderMetaDataList.add(orderMetaData);
        }
        return OrderHistoryResponse.builder().orderMetaDataList(orderMetaDataList).build();
    }
}
