package com.eatzy.flash.processor;

import com.eatzy.flash.request.MasterRouterRequest;

import javax.inject.Inject;

public class MasterRouterProcessor {
    CartInfoProcessor cartInfoProcessor;
    FetchFilterBasedOutletProcessor fetchFilterBasedOutletProcessor;
    FetchNewOrderProcessor fetchNewOrderProcessor;
    FetchOrderUpdateProcessor fetchOrderUpdateProcessor;
    MenuCardProcessor menuCardProcessor;
    OrderHistoryProcessor orderHistoryProcessor;
    OrderProcessor orderProcessor;
    OutletDetailsProcessor outletDetailsProcessor;
    OutletProcessor outletProcessor;
    PostOrderUpdateProcessor postOrderUpdateProcessor;
    UpdateProfileDataProcessor updateProfileDataProcessor;
    FetchUserProfileProcessor fetchUserProfileProcessor;

    @Inject MasterRouterProcessor(CartInfoProcessor cartInfoProcessor,
            FetchFilterBasedOutletProcessor fetchFilterBasedOutletProcessor,
            FetchNewOrderProcessor fetchNewOrderProcessor,
            FetchOrderUpdateProcessor fetchOrderUpdateProcessor,
            MenuCardProcessor menuCardProcessor,
            OrderHistoryProcessor orderHistoryProcessor,
            OrderProcessor orderProcessor,
            OutletDetailsProcessor outletDetailsProcessor,
            OutletProcessor outletProcessor,
            PostOrderUpdateProcessor postOrderUpdateProcessor,
            UpdateProfileDataProcessor updateProfileDataProcessor,
            FetchUserProfileProcessor userProfileProcessor) {

        this.cartInfoProcessor = cartInfoProcessor;
        this.fetchFilterBasedOutletProcessor = fetchFilterBasedOutletProcessor;
        this.menuCardProcessor = menuCardProcessor;
        this.orderHistoryProcessor = orderHistoryProcessor;
        this.fetchOrderUpdateProcessor = fetchOrderUpdateProcessor;
        this.orderProcessor = orderProcessor;
        this.outletDetailsProcessor = outletDetailsProcessor;
        this.outletProcessor = outletProcessor;
        this.postOrderUpdateProcessor = postOrderUpdateProcessor;
        this.fetchNewOrderProcessor =fetchNewOrderProcessor;
        this.updateProfileDataProcessor =updateProfileDataProcessor;
        this.fetchUserProfileProcessor = userProfileProcessor;
    }

    public Object process(MasterRouterRequest masterRouterRequest) {
        if (masterRouterRequest.getFetchCartInfoRequest() != null) {
            return cartInfoProcessor.process(masterRouterRequest.getFetchCartInfoRequest());
        } else if (masterRouterRequest.getFetchFilterBasedOutletRequest() != null) {
            return fetchFilterBasedOutletProcessor.process(masterRouterRequest.getFetchFilterBasedOutletRequest());
        } else if (masterRouterRequest.getFetchNewOrderRequest() != null) {
            return fetchNewOrderProcessor.process(masterRouterRequest.getFetchNewOrderRequest());
        } else if (masterRouterRequest.getFetchOrderUpdateRequest() != null) {
            return fetchOrderUpdateProcessor.process(masterRouterRequest.getFetchOrderUpdateRequest());
        } else if (masterRouterRequest.getMenuCardCreateRequest() != null) {
            return menuCardProcessor.process(masterRouterRequest.getMenuCardCreateRequest());
        } else if (masterRouterRequest.getOrderHistoryRequest() != null) {
            return orderHistoryProcessor.process(masterRouterRequest.getOrderHistoryRequest());
        } else if (masterRouterRequest.getOrderCreateRequest() != null) {
            return orderProcessor.process(masterRouterRequest.getOrderCreateRequest());
        } else if (masterRouterRequest.getGetOutletDetailsRequest() != null) {
            return outletDetailsProcessor.process(masterRouterRequest.getGetOutletDetailsRequest());
        } else if (masterRouterRequest.getOutletCreateRequest() != null) {
            return outletProcessor.process(masterRouterRequest.getOutletCreateRequest());
        } else if (masterRouterRequest.getOrderUpdateRequest() != null) {
            return postOrderUpdateProcessor.process(masterRouterRequest.getOrderUpdateRequest());
        } else if(masterRouterRequest.getUpdateProfileDataRequest()!=null) {
            return updateProfileDataProcessor.process(masterRouterRequest.getUpdateProfileDataRequest());
        } else if(masterRouterRequest.getFetchUserProfileRequest()!=null){
            return fetchUserProfileProcessor.process(masterRouterRequest.getFetchUserProfileRequest());
        }
        return null;
    }
}
