package com.eatzy.flash.model.order;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "ORDER_DETAILS")
public class OrderDetailsDDBRecord {

    public static final String ORDER_ID = "orderID";
    public static final String USER_ID = "userID";
    public static final String OUTLET_ID = "outletID";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String PRICE = "price";
    public static final String ITEM_LIST = "itemList";
    public static final String CREATE_ORDER_TIME = "createOrderTime";
    public static final String UPDATE_ORDER_TIME = "updateOrderTime";

    @DynamoDBHashKey(attributeName = ORDER_ID)
    private String getOrderId;

    @DynamoDBAttribute(attributeName = OUTLET_ID)
    private String getOutletId;

    @DynamoDBAttribute(attributeName = USER_ID)
    private String getUserId;

    @DynamoDBAttribute(attributeName = ORDER_STATUS)
    private String orderStatus;

    @DynamoDBAttribute(attributeName = PRICE)
    private String getPrice;

    @DynamoDBAttribute(attributeName = ITEM_LIST)
    @DynamoDBTypeConverted(converter = ItemMapConverter.class)
    private List<ItemMap> itemMapList;

    @DynamoDBRangeKey(attributeName = CREATE_ORDER_TIME)
    private String getCreateOrderTime;

    @DynamoDBAttribute(attributeName = UPDATE_ORDER_TIME)
    private String getUpdateOrderTime;

}


