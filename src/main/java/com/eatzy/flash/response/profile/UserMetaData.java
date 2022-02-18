package com.eatzy.flash.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMetaData {
    @JsonProperty("userID")
    private String userID;
    @JsonProperty("userName")
    String userName;
    @JsonProperty("userMobile")
    String userMobile;
    @JsonProperty("userEmail")
    String userEmail;
}
