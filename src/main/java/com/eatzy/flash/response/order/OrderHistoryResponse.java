package com.eatzy.flash.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderHistoryResponse {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("orderMetaDataList")
    private List<OrderMetaData> orderMetaDataList;
}
