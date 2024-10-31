package com.qlyshopphone_backend.repository.projection;

public interface CustomerInfoProjection {
     Long getId();

     String getName();

     String getPhone();

     AddressInfo getAddress();

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
