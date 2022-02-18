package com.eatzy.flash.response.order;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamoDBDocument
public class OrderedMenuItem {
    @JsonProperty("menuID")
    String menuID;
    @JsonProperty("menuName")
    String menuName;
    @JsonProperty("price")
    String price;
    @JsonProperty("quantity")
    int quantity;
    @JsonProperty("servingType")
    int servingType;
}
