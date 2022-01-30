package com.eatzy.flash.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FetchCartInfoRequest {
    @JsonProperty("outletID")
    String outletID;

    @JsonProperty("menuItemToQuantityMap")
    HashMap<String, Integer> menuItemToQuantityMap;

    @JsonProperty("userID")
    String userID;
}
