package com.eatzy.flash.model.order;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@DynamoDBTypeConverted(converter = ItemMapConverter.class)
public class ItemMapConverter implements DynamoDBTypeConverter<String, List<ItemMap>> {
    public ItemMapConverter(){}

    @Override public String convert(List<ItemMap> itemMaps) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(itemMaps);
        } catch (JsonProcessingException e) {
            //do something
        }
        return null;
    }

    @Override public List<ItemMap> unconvert(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(s, new TypeReference<List<ItemMap>>() {
            });
        } catch (JsonParseException e) {
            //do something
        } catch (JsonMappingException e) {
            //do something
        } catch (IOException e) {
            //do something
        }
        return null;
    }

}
