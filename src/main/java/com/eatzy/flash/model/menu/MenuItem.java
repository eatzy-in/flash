package com.eatzy.flash.model.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuItem {
    @JsonProperty("id")
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("description")
    String description;
    @JsonProperty("price")
    String price;
    @JsonProperty("imageURI")
    String imageURI;
    @JsonProperty("menuItemCategory")
    MenuItemCategoryByCourses menuItemCategory;
    @JsonProperty("menuItemCategoryByRegion")
    MenuItemCategoryByRegion menuItemCategoryByRegion;
    @JsonProperty("menuItemCategoryByMeal")
    MenuItemCategoryByMeal menuItemCategoryByMeal;
    @JsonProperty("menuItemCategoryByETA")
    MenuItemCategoryByETA menuItemCategoryByETA;
}

