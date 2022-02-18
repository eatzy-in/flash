package com.eatzy.flash.model.profile;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.eatzy.flash.model.Constants.TABLE_NAME_USER_PROFILE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = TABLE_NAME_USER_PROFILE)
public class UserProfileDDBRecord {

    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_MOBILE= "mobile";

    @DynamoDBHashKey(attributeName = USER_ID)
    private String getUserId;

    @DynamoDBAttribute(attributeName = USER_NAME)
    private String getUserName;

    @DynamoDBAttribute(attributeName = USER_EMAIL)
    private String getUserEmail;

    @DynamoDBAttribute(attributeName = USER_MOBILE)
    private String getUserMobile;
}


