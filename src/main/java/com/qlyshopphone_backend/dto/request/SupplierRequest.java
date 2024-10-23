package com.qlyshopphone_backend.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private String company;
    private String taxCode;
    private String status;
    private Long groupSupplierId;

    private String street;
    private Long cityId;
    private Long countryId;
    private Long wardId;
}
