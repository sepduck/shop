package com.qlyshopphone_backend.constant;

public class PathConstant {
    public static final String API = "/api";
    public static final String V1 = "/v1";
    public static final String API_V1 = API + V1;
    public static final String ADMIN = "/admin";
    public static final String API_V1_ADMIN = API_V1 + ADMIN;


    public static final String API_V1_AUTH = API_V1 + "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String LOGOUT = "/logout";


    public static final String VERIFY = "/verify";
    public static final String RESEND_VERIFICATION = "/resend-verification";
    public static final String FORGOT_PASSWORD = "/forgot-password";

    public static final String CART = "/cart";
    public static final String API_V1_CART = API_V1 + CART;
    public static final String ADD_PRODUCT_ID = "/add/{productId}";
    public static final String LIST_CART = "/list-cart";
    public static final String DELETE_CART_ID = "/delete/{cartId}";public static final String SELLS = "/sells";
    public static final String SALE = "/sale";
    public static final String CART_VIEW = "/view-cart";
    public static final String TODAY_PURCHASES = "/today-purchases";
    public static final String ADMIN_LAST_30_DAYS_PURCHASES = ADMIN + "/last-30-days-purchases";
    public static final String ADMIN_DAILY_SALES_TOTAL_PRICE_LAST_30_DAYS = ADMIN + "/daily-sales-total-price-last-30-days";
    public static final String ADMIN_SALES_PERCENTAGE_CHANGE = ADMIN + "/sales-percentage-change";
    public static final String ADMIN_SALES_MONTH_PERCENTAGE_CHANGE = ADMIN + "/sales-month-percentage-change";
    public static final String CART_SESSION = CART + "/session";
    public static final String CUSTOMER_INFO = CART + "/customer-info";
    public static final String CUSTOMER_INFO_ID = CUSTOMER_INFO + "/{customerInfoId}";

    public static final String NOTIFICATION = "/notification";
    public static final String API_V1_NOTIFICATION = API_V1 + NOTIFICATION;
    public static final String ADMIN_NOTIFICATION = ADMIN + "/notification";

    public static final String PRODUCT = "/product";
    public static final String API_V1_PRODUCT = API_V1 + PRODUCT;
    public static final String ID = "/{id}";
    public static final String PRODUCT_ID = PRODUCT + ID;
    public static final String SEARCH = "/search";
    public static final String ADMIN_PRODUCT = ADMIN + "/product";
    public static final String ADMIN_PRODUCT_SEARCH = ADMIN_PRODUCT + SEARCH;
    public static final String SEARCH_NAME = SEARCH + "/{name}";
    public static final String SEARCH_ID = SEARCH + "/{id}";
    public static final String SEARCH_INVENTORY = SEARCH + "/inventory/{number}";
    public static final String SEARCH_ACTIVE = SEARCH + "/active/{number}";
    public static final String SEARCH_GROUP_PRODUCT_ID = SEARCH + "/group-product/{id}";
    public static final String SEARCH_LOCATION_ID = SEARCH + "/location/{id}";
    public static final String SEARCH_CATEGORY = SEARCH + "/category/{number}";
    public static final String ADMIN_PRODUCT_SEARCH_DIRECT_SALES_NUMBER = ADMIN_PRODUCT_SEARCH + "/direct-sales/{number}";

    public static final String GROUP_PRODUCT = "/group-product";
    public static final String GROUP_PRODUCT_ID = GROUP_PRODUCT + "/{id}";

    public static final String TRADEMARK = "/trademark";
    public static final String TRADEMARK_ID = TRADEMARK + "/{id}";

    public static final String LOCATION = "/location";
    public static final String LOCATION_ID = LOCATION + "/{id}";

    public static final String PROPERTIES = "/properties";
    public static final String PROPERTIES_ID = PROPERTIES + "/{id}";

    public static final String UNIT = "/unit";
    public static final String UNIT_ID = UNIT + "/{id}";

    public static final String CATEGORY = "/category";
    public static final String CATEGORY_ID = CATEGORY + "{id}";

    public static final String SUPPLIER = "/supplier";
    public static final String API_V1_SUPPLIER = API_V1 + SUPPLIER;
    public static final String SUPPLIERS = "/suppliers";
    public static final String NAME_ID = "/name/{id}";
    public static final String PHONE_ID = "/phone/{id}";
    public static final String EMAIL_ID = "/email/{id}";
    public static final String COMPANY = "/company/{id}";
    public static final String TAX_CODE = "/tax-code/{id}";
    public static final String GROUP_IN_SUPPLIER = "/group-in-supplier/{id}";
    public static final String ADDRESS_ID = "/address/{id}";
    public static final String ADMIN_SUPPLIERS_SEARCH = ADMIN + "/search";
    public static final String SEARCH_PHONE_NUMBER = SEARCH + "/phone/{number}";
    public static final String SEARCH_TAX_CODE_NUMBER = SEARCH + "/tax-code/{number}";
    public static final String SEARCH_SUPPLIER_NAME = SEARCH + SUPPLIER + "/{name}";
    public static final String SEARCH_GROUP_SUPPLIER_NUMBER = SEARCH + "/group-supplier/{number}";
    public static final String SEARCH_SUPPLIER_ACTIVE_NUMBER = SEARCH + "/supplier-active/{number}";

    public static final String GROUP_SUPPLIER = "/group-supplier";
    public static final String GROUP_SUPPLIER_ID = GROUP_SUPPLIER + "/{id}";

    public static final String USER = "/user";
    public static final String API_V1_ADMIN_USER = API_V1 + ADMIN + USER;
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String INFO = "/info";
    public static final String EMPLOYEE = "/employee";
    public static final String ASSIGN_EMPLOYEE = "/assign/{userId}";
    public static final String EMPLOYEE_SEARCH_ID = EMPLOYEE + SEARCH + "/id/{id}";
    public static final String EMPLOYEE_SEARCH_NAME = EMPLOYEE + SEARCH + "/name/{name}";
    public static final String EMPLOYEE_SEARCH_PHONE = EMPLOYEE + SEARCH + "/phone/{number}";
    public static final String EMPLOYEE_SEARCH_STATUS = EMPLOYEE + SEARCH + "/status/{status}";
    public static final String CUSTOMER = "/customer";
    public static final String CUSTOMER_SEARCH_ID = CUSTOMER + SEARCH + "/id/{id}";
    public static final String CUSTOMER_SEARCH_NAME = CUSTOMER + SEARCH + "/name/{name}";
    public static final String CUSTOMER_SEARCH_PHONE_NUMBER = CUSTOMER + SEARCH + "/phone/{number}";
    public static final String CUSTOMER_SEARCH_EMAIL = CUSTOMER + SEARCH + "/email/{email}";
    public static final String CUSTOMER_SEARCH_STATUS = CUSTOMER + SEARCH + "/status/{status}";
    public static final String CUSTOMER_SEARCH_GENDER = CUSTOMER + SEARCH + "/gender/{gender}";
    public static final String UPLOAD = "/upload";

    public static final String UPDATE_AVATAR = "/update-avatar";
    public static final String UPDATE_FIRSTNAME = "/update-firstname";
    public static final String UPDATE_LASTNAME = "/update-lastname";
    public static final String UPDATE_ADDRESS = "/update-address";
    public static final String UPDATE_BIRTHDAY = "/update-birthday";
    public static final String UPDATE_FACEBOOK = "/update-facebook";
    public static final String UPDATE_GENDER = "/update-gender";
    public static final String UPDATE_PHONE = "/update-phone";

    public static final String RECEIPT = "/receipt";
}
