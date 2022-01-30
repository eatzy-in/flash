package com.eatzy.flash.response;

import com.eatzy.flash.model.menu.MenuItem;
import com.eatzy.flash.model.menu.MenuItemCategoryByCourses;
import com.eatzy.flash.model.menu.MenuItemCategoryByETA;
import com.eatzy.flash.model.menu.MenuItemCategoryByMeal;
import com.eatzy.flash.model.menu.MenuItemCategoryByRegion;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OutletDetailsResponse {
    @JsonProperty("outletID")
    String outletID;
    @JsonProperty("outletName")
    String outletName;
    @JsonProperty("address")
    String address;
    @JsonProperty("galleryURLs")
    List<String> galleryURLs;
    @JsonProperty("menuItemCategoryByCoursesListHashMap")
    HashMap<MenuItemCategoryByCourses, List<MenuItem>> menuItemCategoryByCoursesListHashMap;
    @JsonProperty("menuItemCategoryByETAListHashMap")
    HashMap<MenuItemCategoryByETA, List<MenuItem>> menuItemCategoryByETAListHashMap;
    @JsonProperty("menuItemCategoryByRegionListHashMap")
    HashMap<MenuItemCategoryByRegion, List<MenuItem>> menuItemCategoryByRegionListHashMap;
    @JsonProperty("menuItemCategoryByMealListHashMap")
    HashMap<MenuItemCategoryByMeal, List<MenuItem>> menuItemCategoryByMealListHashMap;
}
