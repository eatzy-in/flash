package com.eatzy.flash.request;

import com.eatzy.flash.model.menu.MenuItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuCardCreateRequest {
    @JsonProperty("outletID")
    private String outletID;

    @JsonProperty("menuItem")
    private List<MenuItem> menuItem;
}
