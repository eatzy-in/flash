package com.eatzy.flash.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MasterRouterRequest {
    @JsonProperty("fetchCartInfoRequest")
    FetchCartInfoRequest fetchCartInfoRequest;
    @JsonProperty("fetchFilterBasedOutletRequest")
    FetchFilterBasedOutletRequest fetchFilterBasedOutletRequest;
    @JsonProperty("fetchNewOrderRequest")
    FetchNewOrderRequest fetchNewOrderRequest;
    @JsonProperty("fetchOrderUpdateRequest")
    FetchOrderUpdateRequest fetchOrderUpdateRequest;
    @JsonProperty("getOutletDetailsRequest")
    GetOutletDetailsRequest getOutletDetailsRequest;
    @JsonProperty("menuCardCreateRequest")
    MenuCardCreateRequest menuCardCreateRequest;
    @JsonProperty("orderCreateRequest")
    OrderCreateRequest orderCreateRequest;
    @JsonProperty("orderHistoryRequest")
    OrderHistoryRequest orderHistoryRequest;
    @JsonProperty("orderUpdateRequest")
    OrderUpdateRequest orderUpdateRequest;
    @JsonProperty("outletCreateRequest")
    OutletCreateRequest outletCreateRequest;
    @JsonProperty("updateProfileDataRequest")
    UpdateProfileDataRequest updateProfileDataRequest;
    @JsonProperty("fetchUserProfileRequest")
    FetchUserProfileRequest fetchUserProfileRequest;
}
