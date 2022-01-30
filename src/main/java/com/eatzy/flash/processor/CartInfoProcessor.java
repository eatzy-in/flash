package com.eatzy.flash.processor;

import com.eatzy.flash.model.menu.MenuDDBRecord;
import com.eatzy.flash.model.outlet.OutletDDBRecord;
import com.eatzy.flash.request.FetchCartInfoRequest;
import com.eatzy.flash.response.cart.AmountOperation;
import com.eatzy.flash.response.cart.BillAmountType;
import com.eatzy.flash.response.cart.BillInfo;
import com.eatzy.flash.response.cart.CartItemDetails;
import com.eatzy.flash.response.cart.FetchCartDetailResponse;
import com.eatzy.flash.response.cart.OutletDetails;
import com.eatzy.flash.service.DynamoDBConnector;
import com.eatzy.flash.service.S3Connector;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.eatzy.flash.model.Constants.TABLE_NAME_MENU_DETAILS;
import static com.eatzy.flash.model.Constants.TABLE_NAME_OUTLET_DETAILS;

public class CartInfoProcessor {
    private static final String S3_BUCKET_NAME_FOR_OUTLETS = "eatzy-outlets";
    private static final String S3_PREFIX_NAME_FOR_OUTLETS_ICON_TEMPLATE = "%s/icon/";
    private static final String S3_PREFIX_NAME_FOR_MENU_ITEM_TEMPLATE = "%s/menu_item/%s/";

    private final DynamoDBConnector dynamoDBConnector;
    private final S3Connector s3Connector;

    @Inject
    public CartInfoProcessor(DynamoDBConnector dynamoDBConnector,
            S3Connector s3Connector) {
        this.dynamoDBConnector  = dynamoDBConnector;
        this.s3Connector = s3Connector;
    }

    public FetchCartDetailResponse process(FetchCartInfoRequest fetchCartInfoRequest) {
        try {
            System.out.println("Fetch Detail Response" + new ObjectMapper().writeValueAsString(fetchCartInfoRequest));
        } catch (Exception e) {
            System.out.println("Fetch Detail Response Failed");
        }
        String outletId  = fetchCartInfoRequest.getOutletID();
        OutletDDBRecord getOutletDetails = dynamoDBConnector.getItem(OutletDDBRecord.class,
                TABLE_NAME_OUTLET_DETAILS, OutletDDBRecord.OUTLET_ID, outletId);
        double totalAmount =0;

        List<String> iconTemplate = s3Connector.getImageURIList(S3_BUCKET_NAME_FOR_OUTLETS,
                String.format(S3_PREFIX_NAME_FOR_OUTLETS_ICON_TEMPLATE, outletId));
        OutletDetails outletDetails = OutletDetails.builder()
                .outletID(outletId)
                .outletName(getOutletDetails.getName())
                .outletImageLogo(iconTemplate.get(1))
                .address(getOutletDetails.getAddress()).build();
        List<CartItemDetails> cartItemDetailsList = new ArrayList<>();

        for(Map.Entry<String, Integer> entry:  fetchCartInfoRequest.getMenuItemToQuantityMap().entrySet()){
            String menuItemID = entry.getKey();
           int quantity =  entry.getValue();
            MenuDDBRecord menuDDBRecord = dynamoDBConnector.getItem(MenuDDBRecord.class,
                    TABLE_NAME_MENU_DETAILS, MenuDDBRecord.ID, menuItemID);
            double priceOfOneItem =  Double.parseDouble(menuDDBRecord.getGetMenuPrice());
            double aggItemAmount = priceOfOneItem * quantity;
            totalAmount += aggItemAmount;
            List<String> menuItemImageURL = s3Connector.getImageURIList(S3_BUCKET_NAME_FOR_OUTLETS,
                    String.format(S3_PREFIX_NAME_FOR_MENU_ITEM_TEMPLATE, outletId, menuItemID));
            CartItemDetails cartItemDetails = CartItemDetails.builder()
                    .menuItemId(menuItemID)
                    .menuIDName(menuDDBRecord.getGetMenuName())
                    .menuImageURL(menuItemImageURL.get(1))
                    .price(priceOfOneItem)
                    .quantity(quantity)
                    .build();
            cartItemDetailsList.add(cartItemDetails);
        }

        List<BillInfo> billInfos = new ArrayList<>();
        BillInfo totalBill = BillInfo.builder().billAmountType(BillAmountType.PRICE)
                .operation(AmountOperation.SUM).factor(1).build();
        BillInfo taxesBill = BillInfo.builder().billAmountType(BillAmountType.TAXES)
                .operation(AmountOperation.SUM).factor(0.05).build();
        BillInfo discount = BillInfo.builder().billAmountType(BillAmountType.DISCOUNT_TYPE)
                .operation(AmountOperation.SUBTRACT).factor(0).build();
        billInfos.add(totalBill);
        billInfos.add(taxesBill);
        billInfos.add(discount);
        FetchCartDetailResponse fetchCartDetailResponse = FetchCartDetailResponse.builder()
                .cartItemDetailsList(cartItemDetailsList)
                .billInfos(billInfos)
                .outletDetails(outletDetails)
                .build();
        try {
            System.out
                    .println("Fetch Detail Response" + new ObjectMapper().writeValueAsString(fetchCartDetailResponse));
        } catch (Exception e) {
            System.out.println("Fetch Detail Response Failed");
        }
        return fetchCartDetailResponse;
    }
}
