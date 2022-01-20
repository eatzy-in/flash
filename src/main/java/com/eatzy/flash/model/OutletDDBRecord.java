package com.eatzy.flash.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "OUTLET_DETAILS")
public class OutletDDBRecord {

    public static final String OUTLET_ID = "outletID";
    public static final String OUTLET_NAME = "name";
    public static final String OUTLET_ADDRESS = "address";
    public static final String OUTLET_MENU_URI = "menuURI";

    @DynamoDBHashKey(attributeName = OUTLET_ID)
    private String outletID;

    @DynamoDBAttribute(attributeName = OUTLET_NAME)
    private String name;
    @DynamoDBAttribute(attributeName = OUTLET_ADDRESS)
    private String address;

    @DynamoDBAttribute(attributeName = OUTLET_MENU_URI)
    private String menuURI;
}


