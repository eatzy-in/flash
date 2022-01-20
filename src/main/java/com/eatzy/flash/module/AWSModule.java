package com.eatzy.flash.module;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.inject.Named;

public class AWSModule extends AbstractModule {
    public void configure() {
    }

    @Provides
    @Singleton
    private ObjectMapper provideObjectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @Provides
    @Singleton
    @Named("defaultCredentialProvider")
    public AWSCredentialsProvider provideAWSCredentialProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

    @Provides
    @Singleton
    @Named("amazonDynamoDBClient")
    public AmazonDynamoDB provideAWSDynamoDBClient(@Named("defaultCredentialProvider") AWSCredentialsProvider
            credentialsProvider) {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    @Provides
    @Singleton
    @Named("amazonS3Client")
    public AmazonS3 provideAWSS3Client(@Named("defaultCredentialProvider") AWSCredentialsProvider
            credentialsProvider) {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(credentialsProvider)
                .build();
    }

}

