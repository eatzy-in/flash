package com.eatzy.flash.processor;

import com.amazonaws.util.CollectionUtils;
import com.eatzy.flash.model.menu.MenuDDBRecord;
import com.eatzy.flash.model.menu.MenuItem;
import com.eatzy.flash.model.menu.MenuItemCategoryByCourses;
import com.eatzy.flash.model.menu.MenuItemCategoryByETA;
import com.eatzy.flash.model.menu.MenuItemCategoryByMeal;
import com.eatzy.flash.model.menu.MenuItemCategoryByRegion;
import com.eatzy.flash.model.outlet.OutletDDBRecord;
import com.eatzy.flash.request.GetOutletDetailsRequest;
import com.eatzy.flash.response.OutletDetailsResponse;
import com.eatzy.flash.service.DynamoDBConnector;
import com.eatzy.flash.service.S3Connector;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.eatzy.flash.model.Constants.S3Constants.*;
import static com.eatzy.flash.model.Constants.TABLE_NAME_MENU_DETAILS;
import static com.eatzy.flash.model.Constants.TABLE_NAME_OUTLET_DETAILS;

public class OutletDetailsProcessor implements Processor<GetOutletDetailsRequest, OutletDetailsResponse> {
    private final DynamoDBConnector dynamoDBConnector;
    private final S3Connector s3Connector;

    @Inject OutletDetailsProcessor(DynamoDBConnector dynamoDBConnector,
            S3Connector s3Connector) {
        this.dynamoDBConnector = dynamoDBConnector;
        this.s3Connector = s3Connector;
    }

    public OutletDetailsResponse process(GetOutletDetailsRequest outletDetailsRequest) {
        OutletDDBRecord getOutletDetails = dynamoDBConnector.getItem(OutletDDBRecord.class, TABLE_NAME_OUTLET_DETAILS, OutletDDBRecord.OUTLET_ID, outletDetailsRequest.getOutletID());
        String menuID = getOutletDetails.getMenuID();

        List<String> galleryURLS = s3Connector.getImageURIList(S3_BUCKET_NAME_FOR_OUTLETS,
                String.format(S3_PREFIX_NAME_FOR_OUTLETS_GALLERY_TEMPLATE, outletDetailsRequest.getOutletID()));

        List<MenuDDBRecord> menuDDBRecordList = dynamoDBConnector
                .getAllItemUsingGSI(MenuDDBRecord.class, TABLE_NAME_MENU_DETAILS,
                        MenuDDBRecord.SECONDARY_INDEX_NAME,
                        MenuDDBRecord.MENU_ID, menuID);

        List<MenuItem> menuItems = new ArrayList<>();
        for (MenuDDBRecord menuDDBRecord : menuDDBRecordList) {
            String menuItemID = menuDDBRecord.getId();
            List<String> menuItemImageURL = s3Connector.getImageURIList(S3_BUCKET_NAME_FOR_OUTLETS,
                    String.format(S3_PREFIX_NAME_FOR_MENU_ITEM_TEMPLATE, outletDetailsRequest.getOutletID(),
                            menuItemID));
            if (!CollectionUtils.isNullOrEmpty(menuItemImageURL) && menuItemImageURL.size() > 1)
                menuDDBRecord.setGetMenuImageUri(menuItemImageURL.get(0));

            menuItems.add(convertMenuDDBToMenuItem(menuDDBRecord));
        }

        return OutletDetailsResponse.builder().
                outletID(getOutletDetails.getOutletID())
                .outletName(getOutletDetails.getName())
                .address(getOutletDetails.getAddress())
                .galleryURLs(galleryURLS)
                .menuItemCategoryByCoursesListHashMap( buildMenuItemByCategory(menuItems))
                .menuItemCategoryByETAListHashMap( buildMenuItemByETA(menuItems))
                .menuItemCategoryByMealListHashMap(  buildMenuItemByMeal(menuItems))
                .menuItemCategoryByRegionListHashMap(buildMenuItemByRegion(menuItems))
                .build();
    }

    private HashMap<MenuItemCategoryByCourses, List<MenuItem>> buildMenuItemByCategory(List<MenuItem> menuItemList) {
        HashMap<MenuItemCategoryByCourses, List<MenuItem>> results = new HashMap<>();
        for (MenuItem menuItem : menuItemList) {
            MenuItemCategoryByCourses key = menuItem.getMenuItemCategory();
            List<MenuItem> menuItems;
            if (results.containsKey(key)) {
                menuItems = results.get(key);
            } else {
                menuItems = new ArrayList<>();
            }
            menuItems.add(menuItem);
            results.put(key, menuItems);
        }
        return results;

    }

    private HashMap<MenuItemCategoryByETA, List<MenuItem>> buildMenuItemByETA(List<MenuItem> menuItemList) {
        HashMap<MenuItemCategoryByETA, List<MenuItem>> results = new HashMap<>();
        for (MenuItem menuItem : menuItemList) {
            MenuItemCategoryByETA key = menuItem.getMenuItemCategoryByETA();
            List<MenuItem> menuItems;
            if (results.containsKey(key)) {
                menuItems = results.get(key);
            } else {
                menuItems = new ArrayList<>();
            }
            menuItems.add(menuItem);
            results.put(key, menuItems);
        }
        return results;

    }

    private HashMap<MenuItemCategoryByMeal, List<MenuItem>> buildMenuItemByMeal(List<MenuItem> menuItemList) {
        HashMap<MenuItemCategoryByMeal, List<MenuItem>> results = new HashMap<>();
        for (MenuItem menuItem : menuItemList) {
            MenuItemCategoryByMeal key = menuItem.getMenuItemCategoryByMeal();
            List<MenuItem> menuItems;
            if (results.containsKey(key)) {
                menuItems = results.get(key);
            } else {
                menuItems = new ArrayList<>();
            }
            menuItems.add(menuItem);
            results.put(key, menuItems);
        }
        return results;

    }

    private HashMap<MenuItemCategoryByRegion, List<MenuItem>> buildMenuItemByRegion(List<MenuItem> menuItemList) {
        HashMap<MenuItemCategoryByRegion, List<MenuItem>> results = new HashMap<>();
        for (MenuItem menuItem : menuItemList) {
            MenuItemCategoryByRegion key = menuItem.getMenuItemCategoryByRegion();
            List<MenuItem> menuItems;
            if (results.containsKey(key)) {
                menuItems = results.get(key);
            } else {
                menuItems = new ArrayList<>();
            }
            menuItems.add(menuItem);
            results.put(key, menuItems);
        }
        return results;

    }

    private MenuItem convertMenuDDBToMenuItem(MenuDDBRecord menuDDBRecord) {
        return MenuItem.builder().name(menuDDBRecord.getGetMenuName())
                .id(menuDDBRecord.getId())
                .description(menuDDBRecord.getGetMenuDescription())
                .imageURI(menuDDBRecord.getGetMenuImageUri())
                .price(menuDDBRecord.getGetMenuPrice())
                .menuItemCategory(MenuItemCategoryByCourses.valueOf(menuDDBRecord.getGetMenuCategoryCourses()))
                .menuItemCategoryByMeal(MenuItemCategoryByMeal.valueOf(menuDDBRecord.getGetMenuCategoryMeal()))
                .menuItemCategoryByRegion(MenuItemCategoryByRegion.valueOf(menuDDBRecord.getGetMenuCategoryRegion()))
                .menuItemCategoryByETA(MenuItemCategoryByETA.valueOf(menuDDBRecord.getGetMenuCategoryEta()))
                .build();

    }
}

