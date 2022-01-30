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
public class CartItemDetails {
    @JsonProperty("menuItemId")
    String menuItemId;
    @JsonProperty("menuItemName")
    String menuIDName;
    @JsonProperty("menuImageURL")
    String menuImageURL;
    @JsonProperty("quantity")
    int quantity;
    @JsonProperty("price")
    double price;
}
