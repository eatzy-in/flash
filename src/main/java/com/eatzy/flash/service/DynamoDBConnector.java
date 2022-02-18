package com.eatzy.flash.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.eatzy.flash.model.order.OrderDetailsDDBRecord;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public <T> List<T> getScanResult(Class<T> clazz, String tableName) {
        try {
            List<T> clazzResult = new ArrayList<>();
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName);

            ScanResult result = amazonDynamoDB.scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()) {
                clazzResult.add(mapper.marshallIntoObject(clazz, item));
            }
            return clazzResult;
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    public <T> void updateItem(Class<T> clazz, String tableName, String attributeKeyName, String keyValue,
            String updateKey,
            String updateValue) {
        try {
            Table table = getTable(tableName);
            UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                    .withPrimaryKey(attributeKeyName, keyValue)
                    .withUpdateExpression("set " + updateKey + "= :val")
                    .withValueMap(new ValueMap()
                            .withString(":val", updateValue));
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public <T> List<T> getAllItemUsingGSI(Class<T> clazz, String tableName, String secondaryIndexName,
            String attributeKeyName, String keyValue) {

        try {
            List<T> results = new ArrayList<>();
            Table table = dynamoDB.getTable(tableName);
            Index index = table.getIndex(secondaryIndexName);
            System.out.println(table);
            System.out.println(index);
            QuerySpec spec = new QuerySpec()
                    .withKeyConditionExpression(attributeKeyName + " = :id")
                    .withValueMap(new ValueMap()
                            .withString(":id", keyValue));
            System.out.println(spec);
            System.out.println("spec1");
            ItemCollection<QueryOutcome> items = index.query(spec);
            System.out.println(items);
            System.out.println(items.getMaxResultSize());
            for (Item item : items) {
                results.add(mapper.marshallIntoObject(clazz, ItemUtils.toAttributeValues(item)));
                System.out.println(item.toJSONPretty());
            }
            return results;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<OrderDetailsDDBRecord> filterNewOrders(String secondaryIndexName, String attributeKeyValue) {
        try {
            Map<String, AttributeValue> keyValueMap = new HashMap<>();
            //  filterValueMap.put(":orderStatus", new AttributeValue().withS(filterValue));
            keyValueMap.put(":outletID", new AttributeValue().withS(attributeKeyValue));
            keyValueMap.put(":orderStatus1", new AttributeValue().withS("ORDER_PENDING"));
            keyValueMap.put(":orderStatus2", new AttributeValue().withS("ORDER_ACCEPTED"));
            keyValueMap.put(":orderStatus3", new AttributeValue().withS("ORDER_COMPLETED"));
            keyValueMap.put(":orderStatus4", new AttributeValue().withS("ORDER_RECEIVED"));
            keyValueMap.put(":orderStatus5", new AttributeValue().withS("ORDER_FAILED"));

            DynamoDBQueryExpression t = new DynamoDBQueryExpression()
                    .withIndexName(secondaryIndexName)
                    .withKeyConditionExpression("outletID = :outletID")
                    .withFilterExpression("orderStatus = :orderStatus1 or orderStatus = :orderStatus2 or orderStatus = :orderStatus3 or orderStatus = :orderStatus4 or orderStatus = :orderStatus5")
                    .withExpressionAttributeValues(keyValueMap)
                    .withConsistentRead(false);
            List<OrderDetailsDDBRecord> result = mapper.query(OrderDetailsDDBRecord.class, t);
            return result;
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return new ArrayList<>();
    }
}
