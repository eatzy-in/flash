package com.eatzy.flash.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {
    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("body")
    private String body;
}
