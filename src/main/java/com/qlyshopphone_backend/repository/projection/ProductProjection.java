package com.qlyshopphone_backend.repository.projection;

public interface ProductProjection {
    Long getId();

    String getName();

    GroupProductInfo getGroupProduct();

    TrademarkInfo getTrademark();

    CategoryInfo getCategory();

    UnitInfo getUnit();

    interface GroupProductInfo {
        Long getId();

        String getName();
    }

    interface TrademarkInfo {
        Long getId();

        String getName();
    }

    interface CategoryInfo {
        Long getId();

        String getName();
    }

    interface UnitInfo {
        Long getId();

        String getName();
    }
}
