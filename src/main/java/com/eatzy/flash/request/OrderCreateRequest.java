package com.eatzy.flash.request;

import com.eatzy.flash.response.order.OrderedMenuItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateRequest {
    @JsonProperty("userID")
    String userID;
    @JsonProperty("orderedMenuItemList")
    List<OrderedMenuItem> orderedMenuItemList;
    @JsonProperty("orderAmount")
    String orderAmount;
    @JsonProperty("outletID")
    String outletID;
    @JsonProperty("orderID")
    String orderId;
}
