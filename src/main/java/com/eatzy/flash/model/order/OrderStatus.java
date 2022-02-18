package com.eatzy.flash.model.order;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

public enum OrderStatus {
        ORDER_CREATED,
        ORDER_RECEIVED,
        ORDER_PROCESS,
        ORDER_DELIVERED,
        ORDER_FAILED,
        ORDER_PENDING,
        ORDER_CANCELLED,
        ORDER_ACCEPTED,
        ORDER_PREPARING,
        ORDER_COMPLETED
        }
