package com.eatzy.flash.processor;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.eatzy.flash.model.menu.MenuDDBRecord;
import com.eatzy.flash.model.menu.MenuItem;
import com.eatzy.flash.model.profile.UserProfileDDBRecord;
import com.eatzy.flash.request.FetchUserProfileRequest;
import com.eatzy.flash.request.MenuCardCreateRequest;
import com.eatzy.flash.response.MenuCreateResponse;
import com.eatzy.flash.response.profile.FetchUserProfileResponse;
import com.eatzy.flash.response.profile.UserMetaData;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static com.eatzy.flash.model.Constants.TABLE_NAME_USER_PROFILE;

public class FetchUserProfileProcessor implements Processor<FetchUserProfileRequest, FetchUserProfileResponse>{
    private final DynamoDBConnector dynamoDBConnector;

    @Inject FetchUserProfileProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public FetchUserProfileResponse process(FetchUserProfileRequest fetchUserProfileRequest) {
            String userID = fetchUserProfileRequest.getUserID();
            UserProfileDDBRecord userProfileDDBRecord = dynamoDBConnector.getItem(UserProfileDDBRecord.class, TABLE_NAME_USER_PROFILE,
                    UserProfileDDBRecord.USER_ID, userID);
        UserMetaData userMetaData = UserMetaData.builder()
                .userID(userID)
                .userEmail(userProfileDDBRecord.getGetUserEmail())
                .userMobile(userProfileDDBRecord.getGetUserMobile())
                .userName(userProfileDDBRecord.getGetUserName())
                .build();
            return  FetchUserProfileResponse.builder().userMetaData(userMetaData).build();
    }

}
