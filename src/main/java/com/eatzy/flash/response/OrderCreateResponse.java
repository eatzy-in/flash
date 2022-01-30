package com.eatzy.flash.response;

import com.eatzy.flash.model.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateResponse {
    @JsonProperty("orderID")
    String orderID;
    @JsonProperty("orderStatus")
    OrderStatus orderStatus;
}
