package com.eatzy.flash.handler;

import com.eatzy.flash.model.OutletDDBRecord;
import com.eatzy.flash.request.CreateOutletRequest;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;

public class OutletHandler {
    private final DynamoDBConnector dynamoDBConnector;

    @Inject OutletHandler(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public void process(CreateOutletRequest createOutletRequest) {
        try {
            OutletDDBRecord outletDDBRecord = OutletDDBRecord.builder().outletID(createOutletRequest.getId())
                    .name(createOutletRequest.getName())
                    .address(createOutletRequest.getAddress()).build();
            dynamoDBConnector.save(outletDDBRecord);
        } catch (Exception exception) {
            System.out.println("exception while raising : " + exception);
        }
    }

}
