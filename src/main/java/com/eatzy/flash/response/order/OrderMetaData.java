package com.eatzy.flash.response.order;

import com.eatzy.flash.model.menu.MenuItem;
import com.eatzy.flash.model.order.ItemMap;
import com.eatzy.flash.model.order.OrderStatus;
import com.eatzy.flash.response.profile.UserMetaData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMetaData {
    @JsonProperty("orderID")
    String orderID;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("outletID")
    String outletID;
    @JsonProperty("outletName")
    String outletName;
    @JsonProperty("outletImageLogo")
    String outletImageLogo;
    @JsonProperty("orderAmount")
    String orderAmount;
    @JsonProperty("orderTime")
    String orderTime;
    @JsonProperty("orderedMenuItemList")
    List<OrderedMenuItem> orderedMenuItemList;
    @JsonProperty("orderStatus") OrderStatus orderStatus;

    @JsonProperty("userMetaData")
    UserMetaData userMetaData;

}
