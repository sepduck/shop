package com.qlyshopphone_backend.constant;

public class PathConstant {
    public static final String API = "/api";
    public static final String V1 = "/v1";
    public static final String API_V1 = API + V1;
    public static final String ADMIN = "/admin";
    public static final String API_V1_ADMIN = API_V1 + ADMIN;

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String LOGOUT = "/logout";
    public static final String API_V1_LOGIN = API_V1 + LOGIN;
    public static final String API_V1_REGISTER = API_V1 + REGISTER;

    public static final String CART = "/cart";
    public static final String CART_ADD_PRODUCT_ID = "/cart/add/{productId}";
    public static final String LIST_CART = "/list-cart";
    public static final String DELETE_CART_CART_ID = "/delete/cart/{cartId}";
    public static final String CART_SELL_CART_ID_CUSTOMER_INFO_ID = "/cart/sell/{cardId}/{customerInfoId}";
    public static final String CART_SELLS = "/cart/sells";
    public static final String SALE = "/sale";
    public static final String CART_VIEW = "/view-cart";
    public static final String ADMIN_TODAY_PURCHASES = ADMIN + "/today-purchases";
    public static final String ADMIN_LAST_30_DAYS_PURCHASES = ADMIN + "/last-30-days-purchases";
    public static final String ADMIN_DAILY_SALES_TOTAL_PRICE_LAST_30_DAYS = ADMIN + "/daily-sales-total-price-last-30-days";
    public static final String ADMIN_SALES_PERCENTAGE_CHANGE = ADMIN + "/sales-percentage-change";
    public static final String ADMIN_SALES_MONTH_PERCENTAGE_CHANGE = ADMIN + "/sales-month-percentage-change";
    public static final String CART_SESSION = CART + "/session";
    public static final String CUSTOMER_INFO = CART + "/customer-info";
    public static final String CUSTOMER_INFO_ID = CUSTOMER_INFO + "/{customerInfoId}";

    public static final String ADMIN_NOTIFICATION = ADMIN + "/notification";

    public static final String PRODUCT = "/product";
    public static final String SEARCH = "/search";
    public static final String ADMIN_PRODUCT = ADMIN + "/product";
    public static final String PRODUCT_SEARCH = PRODUCT + SEARCH;
    public static final String ADMIN_PRODUCT_SEARCH = ADMIN_PRODUCT + SEARCH;
    public static final String ADMIN_PRODUCT_PRODUCT_ID = ADMIN_PRODUCT + "/{productId}";
    public static final String PRODUCT_SEARCH_NAME = PRODUCT_SEARCH + "/{name}";
    public static final String PRODUCT_SEARCH_ID = PRODUCT_SEARCH + "/{id}";
    public static final String ADMIN_PRODUCT_SEARCH_INVENTORY_NUMBER = ADMIN_PRODUCT_SEARCH + "/inventory/{number}";
    public static final String ADMIN_PRODUCT_SEARCH_ACTIVE_NUMBER = ADMIN_PRODUCT_SEARCH + "/active/{number}";
    public static final String PRODUCT_SEARCH_GROUP_PRODUCT_ID = PRODUCT_SEARCH + "/group-product/{id}";
    public static final String PRODUCT_SEARCH_LOCATION_ID = PRODUCT_SEARCH + "/location/{id}";
    public static final String PRODUCT_SEARCH_CATEGORY_NUMBER = PRODUCT_SEARCH + "/category/{number}";
    public static final String ADMIN_PRODUCT_SEARCH_DIRECT_SALES_NUMBER = ADMIN_PRODUCT_SEARCH + "/direct-sales/{number}";
    public static final String GROUP_PRODUCT = "/group-product";
    public static final String ADMIN_GROUP_PRODUCT = ADMIN + GROUP_PRODUCT;
    public static final String ADMIN_GROUP_PRODUCT_ID = ADMIN_GROUP_PRODUCT + "/{id}";
    public static final String TRADEMARK = "/trademark";
    public static final String ADMIN_TRADEMARK = ADMIN + TRADEMARK;
    public static final String ADMIN_TRADEMARK_ID = ADMIN_TRADEMARK + "/{id}";
    public static final String LOCATION = "/location";
    public static final String ADMIN_LOCATION = ADMIN + "/location";
    public static final String ADMIN_LOCATION_ID = ADMIN_LOCATION + "/{id}";
    public static final String PROPERTIES = "/properties";
    public static final String ADMIN_PROPERTIES = ADMIN + "/properties";
    public static final String ADMIN_PROPERTIES_ID = ADMIN_PROPERTIES + "/{id}";
    public static final String UNIT = "/unit";
    public static final String ADMIN_UNIT = ADMIN + "/unit";
    public static final String ADMIN_UNIT_ID = ADMIN_UNIT + "/{id}";
    public static final String CATEGORY = "/category";
    public static final String ADMIN_CATEGORY = ADMIN + "/category";
    public static final String ADMIN_CATEGORY_ID = ADMIN_CATEGORY + "{id}";

    public static final String ADMIN_SUPPLIER = ADMIN + "/supplier";
    public static final String ADMIN_SUPPLIERS = ADMIN + "/suppliers";
    public static final String ADMIN_SUPPLIER_ID = ADMIN_SUPPLIER + "/{id}";
    public static final String ADMIN_SUPPLIERS_SEARCH = ADMIN + "/search";
    public static final String ADMIN_SUPPLIERS_SEARCH_PHONE_NUMBER = ADMIN_SUPPLIERS_SEARCH + "/phone/{number}";
    public static final String ADMIN_SUPPLIERS_SEARCH_TAX_CODE_NUMBER = ADMIN_SUPPLIERS_SEARCH + "/tax-code/{number}";
    public static final String ADMIN_SUPPLIERS_SEARCH_NAME = ADMIN_SUPPLIERS_SEARCH + "/{name}";
    public static final String ADMIN_SUPPLIERS_SEARCH_GROUP_SUPPLIER_NUMBER = ADMIN_SUPPLIERS_SEARCH + "/group-supplier/{number}";
    public static final String ADMIN_SUPPLIERS_SEARCH_SUPPLIER_ACTIVE_NUMBER = ADMIN_SUPPLIERS_SEARCH + "/supplier-active/{number}";
    public static final String ADMIN_GROUP_SUPPLIER = ADMIN + "/group-supplier";
    public static final String ADMIN_GROUP_SUPPLIER_ID = ADMIN_GROUP_SUPPLIER + "/{id}";

    public static final String USERS = "/users";
    public static final String ADMIN_USERS = ADMIN + "/users";
    public static final String GENDER = "/gender";
    public static final String PASSWORD = "/password";
    public static final String INFO = "/info";
    public static final String EMPLOYEE = "/employee";
    public static final String USERS_INFO = USERS + INFO;
    public static final String USERS_INFO_FILE = USERS_INFO + "/file";
    public static final String ADMIN_USERS_ID = ADMIN_USERS + "/{id}";
    public static final String ADMIN_EMPLOYEE = ADMIN + EMPLOYEE;
    public static final String ADMIN_EMPLOYEE_ROLE_USER_ID = ADMIN_EMPLOYEE + "/role/{userId}";
    public static final String ADMIN_EMPLOYEE_ID = ADMIN_EMPLOYEE + "/{id}";
    public static final String ADMIN_EMPLOYEE_SEARCH = ADMIN_EMPLOYEE + SEARCH;
    public static final String ADMIN_EMPLOYEE_SEARCH_ID = ADMIN_EMPLOYEE_SEARCH + "id/{id}";
    public static final String ADMIN_EMPLOYEE_SEARCH_NAME = ADMIN_EMPLOYEE_SEARCH + "name/{name}";
    public static final String ADMIN_EMPLOYEE_SEARCH_PHONE_NUMBER = ADMIN_EMPLOYEE_SEARCH + "phone/{number}";
    public static final String ADMIN_EMPLOYEE_SEARCH_ACTIVE_NUMBER = ADMIN_EMPLOYEE_SEARCH + "active/{number}";
    public static final String CUSTOMER = "/customer";
    public static final String ADMIN_CUSTOMER = ADMIN + CUSTOMER;
    public static final String ADMIN_CUSTOMER_ID = ADMIN_CUSTOMER + "/{id}";
    public static final String ADMIN_CUSTOMER_SEARCH = ADMIN_CUSTOMER + SEARCH;
    public static final String ADMIN_CUSTOMER_SEARCH_ID = ADMIN_CUSTOMER_SEARCH + "/id/{id}";
    public static final String ADMIN_CUSTOMER_SEARCH_NAME = ADMIN_CUSTOMER_SEARCH + "/name/{name}";
    public static final String ADMIN_CUSTOMER_SEARCH_PHONE_NUMBER = ADMIN_CUSTOMER_SEARCH + "/phone/{number}";
    public static final String ADMIN_CUSTOMER_SEARCH_EMAIL = ADMIN_CUSTOMER_SEARCH + "/email/{email}";
    public static final String ADMIN_CUSTOMER_SEARCH_ADDRESS = ADMIN_CUSTOMER_SEARCH + "/address/{address}";
    public static final String ADMIN_CUSTOMER_SEARCH_ACTIVE_NUMBER = ADMIN_CUSTOMER_SEARCH + "/active/{number}";
    public static final String ADMIN_CUSTOMER_SEARCH_GENDER_NUMBER = ADMIN_CUSTOMER_SEARCH + "/gender/{number}";
}
