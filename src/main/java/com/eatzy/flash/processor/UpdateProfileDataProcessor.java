package com.eatzy.flash.processor;

import com.eatzy.flash.model.profile.UserProfileDDBRecord;
import com.eatzy.flash.request.UpdateProfileDataRequest;
import com.eatzy.flash.response.UpdateProfileDataResponse;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;

public class UpdateProfileDataProcessor implements Processor<UpdateProfileDataRequest,
        UpdateProfileDataResponse> {

    private final DynamoDBConnector dynamoDBConnector;

    @Inject UpdateProfileDataProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public UpdateProfileDataResponse process(UpdateProfileDataRequest profileDataRequest) {
        try {
            System.out.println(profileDataRequest);
            UserProfileDDBRecord userProfileDDBRecord = UserProfileDDBRecord.builder()
                    .getUserId(profileDataRequest.getUserID())
                    .getUserEmail(profileDataRequest.getEmailID())
                    .getUserMobile(profileDataRequest.getMobileNumber())
                    .getUserName(profileDataRequest.getUserName())
                    .build();
            dynamoDBConnector.save(userProfileDDBRecord);
            return UpdateProfileDataResponse.builder().userID(profileDataRequest.getUserID()).build();
        } catch (Exception exception) {
            System.out.println("exception while creating order : " + exception);
            throw exception;
        }
    }
}

