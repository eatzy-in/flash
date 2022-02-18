package com.eatzy.flash.processor;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.eatzy.flash.model.menu.MenuDDBRecord;
import com.eatzy.flash.model.menu.MenuItem;
import com.eatzy.flash.request.MenuCardCreateRequest;
import com.eatzy.flash.response.MenuCreateResponse;
import com.eatzy.flash.service.DynamoDBConnector;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class MenuCardProcessor implements Processor<MenuCardCreateRequest , MenuCreateResponse>{
    private final DynamoDBConnector dynamoDBConnector;

    @Inject MenuCardProcessor(DynamoDBConnector dynamoDBConnector) {
        this.dynamoDBConnector = dynamoDBConnector;
    }

    public MenuCreateResponse process(MenuCardCreateRequest menuCardCreateRequest) {
        String outlet = menuCardCreateRequest.getOutletID();
        String menuID = createMenuID();
        List<MenuItem> menuItems = menuCardCreateRequest.getMenuItem();
        for (MenuItem menuItem : menuItems) {
            MenuDDBRecord menuDDBRecord = MenuDDBRecord.builder().menuId(menuID)
                    .getMenuName(menuItem.getName())
                    .getMenuDescription(menuItem.getDescription())
                    .getMenuPrice(menuItem.getPrice())
                    .getMenuCategoryCourses(String.valueOf(menuItem.getMenuItemCategory()))
                    .getMenuCategoryEta(String.valueOf(menuItem.getMenuItemCategoryByETA()))
                    .getMenuCategoryMeal(String.valueOf(menuItem.getMenuItemCategoryByMeal()))
                    .getMenuCategoryRegion(String.valueOf(menuItem.getMenuItemCategoryByRegion()))
                    .build();
            dynamoDBConnector.save(menuDDBRecord);
        }
        System.out.println("2");
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("outletID", outlet)
                .withUpdateExpression("set menuID = :val")
                .withValueMap(new ValueMap()
                        .withString(":val", menuID));
        Table table = dynamoDBConnector.getTable("OUTLET_DETAILS");
        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome);

        } catch (Exception e) {
            System.err.println("Unable to update item: " + outlet + e + table);
            System.err.println(e);
        }
        return null;
    }

    private String createMenuID() {
        return UUID.randomUUID().toString();
    }

}
