package com.eatzy.flash.response.order;

import com.eatzy.flash.model.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUpdateMetaData {
    @JsonProperty("orderID")
    String orderID;
    @JsonProperty("orderStatus")
    OrderStatus orderStatus;
}
