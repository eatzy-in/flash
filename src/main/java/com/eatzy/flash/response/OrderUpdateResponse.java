package com.eatzy.flash.response;

import com.eatzy.flash.model.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderUpdateResponse {
    @JsonProperty("orderStatus")
    OrderStatus orderStatus;

}
