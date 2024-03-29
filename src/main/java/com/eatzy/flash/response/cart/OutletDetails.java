package com.eatzy.flash.response.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutletDetails {
    @JsonProperty("outletID")
    String outletID;
    @JsonProperty("outletName")
    String outletName;
    @JsonProperty("outletImageLogo")
    String outletImageLogo;
    @JsonProperty("address")
    String address;
}
