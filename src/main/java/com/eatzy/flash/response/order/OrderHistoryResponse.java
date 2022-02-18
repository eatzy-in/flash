package com.eatzy.flash.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHistoryResponse {
    @JsonProperty("orderMetaDataList")
    private List<OrderMetaData> orderMetaDataList;
}
