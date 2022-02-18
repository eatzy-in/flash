package com.eatzy.flash.request;

import com.eatzy.flash.model.outlet.OutletFilterType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FetchFilterBasedOutletRequest {
    @JsonProperty("outletFilterType")
    OutletFilterType outletFilterType;
}
