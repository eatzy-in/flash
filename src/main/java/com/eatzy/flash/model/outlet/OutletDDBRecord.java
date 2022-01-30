package com.eatzy.flash.model.outlet;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.eatzy.flash.model.Constants.TABLE_NAME_OUTLET_DETAILS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = TABLE_NAME_OUTLET_DETAILS)
public class OutletDDBRecord {

    public static final String OUTLET_ID = "outletID";
    public static final String MENU_ID = "menuID";
    public static final String OUTLET_NAME = "name";
    public static final String OUTLET_ADDRESS = "address";

    @DynamoDBHashKey(attributeName = OUTLET_ID)
    private String outletID;

    @DynamoDBAttribute(attributeName = OUTLET_NAME)
    private String name;

    @DynamoDBAttribute(attributeName = OUTLET_ADDRESS)
    private String address;

    @DynamoDBAttribute(attributeName = MENU_ID)
    private String menuID;
}


