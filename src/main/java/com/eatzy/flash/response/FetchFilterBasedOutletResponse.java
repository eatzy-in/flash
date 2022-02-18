package com.eatzy.flash.response;

import com.eatzy.flash.model.outlet.OutletFilterType;
import com.eatzy.flash.response.cart.OutletDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data@Builder
public class FetchFilterBasedOutletResponse {
    @JsonProperty("filterTypeOutletDetailsMap")
    Map<OutletFilterType, List<OutletDetails>> filterTypeOutletDetailsMap;
}
