package com.eatzy.flash.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfileDataRequest {
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userID")
    private String userID;

    @JsonProperty("emailID")
    private String emailID;

    @JsonProperty("mobileNumber")
    private String mobileNumber;
}
