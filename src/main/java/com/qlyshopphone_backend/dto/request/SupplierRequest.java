package com.qlyshopphone_backend.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierRequest {
    private String supplierName;
    private String phoneNumber;
    private String address;
    private String email;
    private String company;
    private String taxCode;
    private boolean deleteProduct;
    private Long groupSupplierId;
    private Long productId;
}
