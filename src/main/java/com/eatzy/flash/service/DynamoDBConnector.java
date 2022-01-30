package com.eatzy.flash.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

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

    public void save(Object object) {
        try {
            mapper.save(object);
        } catch (Exception e) {
            System.out.println(e);
            log.error("Cannot create the table: {}", e.getMessage());
            throw e;
        }
    }

    public Table getTable(String tableName) {
        try {
            return dynamoDB.getTable(tableName);
        } catch (Exception e) {
            System.out.println(e);
            log.error("Cannot create the table: {}", e.getMessage());
            throw e;
        }
    }

    public <T> T getItem(Class<T> clazz, String tableName, String attributeKeyName, String keyValue) {
        try {
            Table table = getTable(tableName);
            System.out.println(table);
            Item item = table.getItem(attributeKeyName, keyValue);
            System.out.println(item);
            return mapper.marshallIntoObject(clazz, ItemUtils.toAttributeValues(item));
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    public <T> List<T> getAllItemUsingGSI(Class<T> clazz, String tableName, String secondaryIndexName,
            String attributeKeyName, String keyValue) {

       try {
           List<T> results = new ArrayList<>();
           Table table = dynamoDB.getTable(tableName);
           Index index = table.getIndex("menuID-index");
           System.out.println(table);
           System.out.println(index);
           QuerySpec spec = new QuerySpec()
                   .withKeyConditionExpression(attributeKeyName + " = :id")
                   .withValueMap(new ValueMap()
                           .withString(":id", keyValue));
           System.out.println(spec);
           System.out.println("spec");
           ItemCollection<QueryOutcome> items = index.query(spec);
           System.out.println(items.getMaxResultSize());
           for (Item item : items) {
               results.add(mapper.marshallIntoObject(clazz, ItemUtils.toAttributeValues(item)));
               System.out.println(item.toJSONPretty());
           }
           return results;
       } catch (Exception e){
           System.out.println(e);
       }
       return null;
    }

}
