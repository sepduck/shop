package com.qlyshopphone_backend.repository.projection;

import com.qlyshopphone_backend.model.enums.Status;

public interface SupplierProjection {
    Long getId();

    String getName();

    String getPhoneNumber();

    AddressInfo getAddress();

    String getEmail();

    String getCompany();

    String getTaxCode();

    Status getStatus();

    GroupSupplierInfo getGroupSupplier();

    interface GroupSupplierInfo {
        Long getId();

        String getName();
    }

    interface AddressInfo {
        Long getId();

        String getStreet();

        WardInfo getWard();

        CityInfo getCity();

        CountryInfo getCountry();

        interface WardInfo {
            Long getId();

            String getName();
        }

        interface CityInfo {
            Long getId();

            String getName();
        }

        interface CountryInfo {
            Long getId();

            String getName();
        }
    }
}
