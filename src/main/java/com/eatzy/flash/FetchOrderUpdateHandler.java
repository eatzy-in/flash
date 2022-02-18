package com.eatzy.flash;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.eatzy.flash.module.AWSModule;
import com.eatzy.flash.processor.FetchOrderUpdateProcessor;
import com.eatzy.flash.request.FetchOrderUpdateRequest;
import com.eatzy.flash.response.APIResponse;
import com.eatzy.flash.response.order.FetchOrderUpdateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Base64;

public class FetchOrderUpdateHandler implements RequestStreamHandler {
    private String CHARSET_NAME = "UTF-8";
    private String REQUEST_BODY = "body";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private JSONObject jsonObject = new JSONObject();
    private JSONParser jsonParser = new JSONParser();
    private final Injector injector;
    private final FetchOrderUpdateProcessor fetchOrderUpdateProcessor;
    private final ObjectMapper objectMapper;

    public FetchOrderUpdateHandler() {
        this.injector = Guice.createInjector(new AWSModule());
        this.fetchOrderUpdateProcessor = this.injector.getInstance(FetchOrderUpdateProcessor.class);
        this.objectMapper = this.injector.getInstance(ObjectMapper.class);
    }

    @Override public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        try {
            jsonObject = (JSONObject) jsonParser.parse(
                    new InputStreamReader(inputStream, CHARSET_NAME));
            byte[] decodedBytes = Base64.getDecoder().decode(jsonObject.get(REQUEST_BODY).toString());
            FetchOrderUpdateRequest fetchOrderUpdateRequest = objectMapper
                    .readValue(decodedBytes, FetchOrderUpdateRequest.class);
            FetchOrderUpdateResponse fetchOrderUpdateResponse = fetchOrderUpdateProcessor
                    .process(fetchOrderUpdateRequest);
            APIResponse apiResponse = APIResponse.builder().statusCode(HttpStatus.SC_OK)
                    .body(objectMapper.writeValueAsString(fetchOrderUpdateResponse)).build();
            objectMapper.writeValue(outputStream, apiResponse);
        } catch (Exception exception) {
            System.out.println(exception);

        }
    }

}
