package com.eatzy.flash.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderMetaData {
    @JsonProperty("orderID")
    String orderID;
    @JsonProperty("outletName")
    String outletName;
    @JsonProperty("outletImageLogo")
    String outletImageLogo;
    @JsonProperty("orderAmount")
    String orderAmount;
    @JsonProperty("orderTime")
    Date orderTime;
    @JsonProperty("menuNameToPriceMap")
    HashMap<String, Double> menuNameToPriceMap;
}
