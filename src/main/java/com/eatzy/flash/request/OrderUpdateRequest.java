package com.eatzy.flash.request;

import com.eatzy.flash.model.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderUpdateRequest {
    @JsonProperty("orderID")
    String orderId;
    @JsonProperty("orderStatus")
    OrderStatus orderStatus;
}
