package com.eatzy.flash.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateRequest {
    @JsonProperty("userID")
    String userID;
    @JsonProperty("itemMap")
    HashMap<String, Integer> itemMap;
    @JsonProperty("orderAmount")
    String orderAmount;
    @JsonProperty("outletID")
    String outletID;
}
