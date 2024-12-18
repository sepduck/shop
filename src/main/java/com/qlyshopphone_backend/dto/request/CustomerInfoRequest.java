package com.qlyshopphone_backend.dto.request;

import lombok.Data;

@Data
public class CustomerInfoRequest {
    private String name;
    private String phone;

    private String street;
    private Long cityId;
    private Long countryId;
    private Long wardId;
}
