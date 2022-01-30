package com.eatzy.flash.processor;

import com.eatzy.flash.model.outlet.OutletDDBRecord;
import com.eatzy.flash.request.OutletCreateRequest;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;

public class OutletProcessor {
    private final DynamoDBConnector dynamoDBConnector;

    @Inject OutletProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public void process(OutletCreateRequest outletCreateRequest) {
        try {
            OutletDDBRecord outletDDBRecord = OutletDDBRecord.builder().outletID(outletCreateRequest.getId())
                    .name(outletCreateRequest.getName())
                    .address(outletCreateRequest.getAddress()).build();
            dynamoDBConnector.save(outletDDBRecord);
        } catch (Exception exception) {
            System.out.println("exception while raising : " + exception);
        }
    }

}
