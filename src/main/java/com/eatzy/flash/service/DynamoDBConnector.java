package com.eatzy.flash.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.eatzy.flash.model.OutletDDBRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
public class DynamoDBConnector {
    AmazonDynamoDB amazonDynamoDB;
    DynamoDB dynamoDB;
    DynamoDBMapper mapper;

    @Inject DynamoDBConnector(@Named("amazonDynamoDBClient") AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
        dynamoDB = new DynamoDB(amazonDynamoDB);
        mapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public void save(OutletDDBRecord object) {
        try {
            mapper.save(object);
        } catch (Exception e) {
            System.out.println(e);
           log.error("Cannot create the table: {}", e.getMessage());
           throw e;
        }
    }

}
