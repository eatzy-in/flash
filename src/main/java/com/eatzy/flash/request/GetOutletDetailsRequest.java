package com.eatzy.flash.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetOutletDetailsRequest {
    @JsonProperty("outletID")
    String outletID;
    @JsonProperty("userID")
    String userID;
}
