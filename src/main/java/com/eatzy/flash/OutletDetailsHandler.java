package com.eatzy.flash;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.eatzy.flash.module.AWSModule;
import com.eatzy.flash.processor.OutletDetailsProcessor;
import com.eatzy.flash.processor.OutletProcessor;
import com.eatzy.flash.request.GetOutletDetailsRequest;
import com.eatzy.flash.request.OutletCreateRequest;
import com.eatzy.flash.response.APIResponse;
import com.eatzy.flash.response.OutletDetailsResponse;
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

public class OutletDetailsHandler implements RequestStreamHandler {

    private String CHARSET_NAME = "UTF-8";
    private String REQUEST_BODY = "body";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private JSONObject jsonObject = new JSONObject();
    private JSONParser jsonParser = new JSONParser();
    private final Injector injector;
    private final OutletDetailsProcessor outletDetailsProcessor;
    private final ObjectMapper objectMapper;

    public OutletDetailsHandler() {
        this.injector = Guice.createInjector(new AWSModule());
        this.outletDetailsProcessor = this.injector.getInstance(OutletDetailsProcessor.class);
        this.objectMapper = this.injector.getInstance(ObjectMapper.class);
    }

    @Override public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        try {
            jsonObject = (JSONObject) jsonParser.parse(
                    new InputStreamReader(inputStream, CHARSET_NAME));
            byte[] decodedBytes = Base64.getDecoder().decode(jsonObject.get(REQUEST_BODY).toString());
            GetOutletDetailsRequest outletDetailsRequest = objectMapper.readValue(decodedBytes, GetOutletDetailsRequest.class);
            OutletDetailsResponse outletDetailsResponse = outletDetailsProcessor.process(outletDetailsRequest);
            APIResponse apiResponse = APIResponse.builder().statusCode(HttpStatus.SC_OK).
                    body(objectMapper.writeValueAsString(outletDetailsResponse)).build();
            objectMapper.writeValue(outputStream, apiResponse);
        } catch (Exception exception) {
            System.out.println(exception);
            APIResponse apiResponse = APIResponse.builder().statusCode(HttpStatus.SC_BAD_GATEWAY).
                    body("Failed").build();
            objectMapper.writeValue(outputStream, apiResponse);
        }
    }
}
