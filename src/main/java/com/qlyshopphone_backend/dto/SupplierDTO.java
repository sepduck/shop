package com.qlyshopphone_backend.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDTO {
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
