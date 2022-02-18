package com.eatzy.flash.processor;

import com.eatzy.flash.model.outlet.OutletDDBRecord;
import com.eatzy.flash.model.outlet.OutletFilterType;
import com.eatzy.flash.request.FetchCartInfoRequest;
import com.eatzy.flash.request.FetchFilterBasedOutletRequest;
import com.eatzy.flash.response.FetchFilterBasedOutletResponse;
import com.eatzy.flash.response.cart.FetchCartDetailResponse;
import com.eatzy.flash.response.cart.OutletDetails;
import com.eatzy.flash.service.DynamoDBConnector;
import com.eatzy.flash.service.S3Connector;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.eatzy.flash.model.Constants.S3Constants.*;
import static com.eatzy.flash.model.Constants.TABLE_NAME_OUTLET_DETAILS;

public class FetchFilterBasedOutletProcessor implements Processor<FetchFilterBasedOutletRequest, FetchFilterBasedOutletResponse> {

    private final DynamoDBConnector dynamoDBConnector;
    private final S3Connector s3Connector;

    @Inject FetchFilterBasedOutletProcessor(DynamoDBConnector dynamoDBConnector,
            S3Connector s3Connector) {
        this.dynamoDBConnector = dynamoDBConnector;
        this.s3Connector = s3Connector;
    }

    public FetchFilterBasedOutletResponse process(FetchFilterBasedOutletRequest request) {
        HashMap<OutletFilterType, List<OutletDetails>> outletDetailsHashMap = new HashMap<>();
        if (request.getOutletFilterType() == OutletFilterType.TOP) {
            System.out.println("1");
            List<OutletDetails> outletDetailsList = new ArrayList<>();
            List<OutletDDBRecord> outletDDBRecordList = dynamoDBConnector
                    .getScanResult(OutletDDBRecord.class, TABLE_NAME_OUTLET_DETAILS);
            for (OutletDDBRecord outletDDBRecord : outletDDBRecordList) {
                List<String> imageLOGO = s3Connector.getImageURIList(S3_BUCKET_NAME_FOR_OUTLETS,
                        String.format(S3_PREFIX_NAME_FOR_OUTLETS_ICON_TEMPLATE, outletDDBRecord.getOutletID()));
                System.out.println("2"+imageLOGO.size()+ outletDDBRecord.getOutletID());

                List<String> galleryURLS = s3Connector.getImageURIList(S3_BUCKET_NAME_FOR_OUTLETS,
                        String.format(S3_PREFIX_NAME_FOR_OUTLETS_GALLERY_TEMPLATE, outletDDBRecord.getOutletID()));

                System.out.println("3"+galleryURLS.size()+ outletDDBRecord.getOutletID());


                OutletDetails outletDetails = OutletDetails.builder()
                        .outletID(outletDDBRecord.getOutletID())
                        .outletName(outletDDBRecord.getName())
                        .outletImageLogo(imageLOGO.get(0))
                        .address(outletDDBRecord.getAddress())
                        .galleryURL(galleryURLS)
                        .build();
                outletDetailsList.add(outletDetails);
            }
            outletDetailsHashMap.put(OutletFilterType.TOP, outletDetailsList);
        }
        return FetchFilterBasedOutletResponse.builder()
                .filterTypeOutletDetailsMap(outletDetailsHashMap)
                .build();
    }

}
