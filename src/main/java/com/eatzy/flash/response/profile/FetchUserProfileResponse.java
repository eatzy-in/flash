package com.eatzy.flash.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchUserProfileResponse {
    @JsonProperty("userMetaData")
    UserMetaData userMetaData;
}
