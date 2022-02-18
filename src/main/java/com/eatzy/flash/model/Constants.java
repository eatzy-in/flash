package com.eatzy.flash.model;

public class Constants {
    public static final String TABLE_NAME_OUTLET_DETAILS = "OUTLET_DETAILS";
    public static final String TABLE_NAME_MENU_DETAILS = "MENU_DETAILS";
    public static final String TABLE_NAME_ORDER_DETAILS = "ORDER_DETAILS";
    public static final String TABLE_NAME_USER_PROFILE = "USER_PROFILE";
    static public class S3Constants {
        public static final String S3_BUCKET_NAME_FOR_OUTLETS = "eatzy-outlets";
        public static final String S3_PREFIX_NAME_FOR_OUTLETS_ICON_TEMPLATE = "%s/icon/";
        public static final String S3_PREFIX_NAME_FOR_MENU_ITEM_TEMPLATE = "%s/menu_item/%s/";
        public static final String S3_PREFIX_NAME_FOR_OUTLETS_GALLERY_TEMPLATE = "%s/gallery/";
    }
}


