package com.eatzy.flash.response;

import com.eatzy.flash.model.order.OrderStatus;
import com.eatzy.flash.response.order.OrderMetaData;
import com.eatzy.flash.response.order.OrderedMenuItem;
import com.eatzy.flash.response.profile.UserMetaData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchNewOrderResponse {
    @JsonProperty("orderStatusListHashMap")
    HashMap<OrderStatus , List<OrderMetaData>> orderStatusListHashMap;
}
