package com.eatzy.flash.response.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchCartDetailResponse {
    @JsonProperty("outletDetails")
    OutletDetails outletDetails;
    @JsonProperty("cartItemDetailsList")
    List<CartItemDetails> cartItemDetailsList;
    @JsonProperty("billInfos")
    List<BillInfo> billInfos;
}
