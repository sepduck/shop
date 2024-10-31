package com.qlyshopphone_backend.dto.response;

import com.qlyshopphone_backend.model.GroupSuppliers;
import com.qlyshopphone_backend.model.Products;
import com.qlyshopphone_backend.model.enums.Status;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class SupplierResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String company;
    private String taxCode;
    private Status status;
    private GroupSuppliers groupSupplier;
    private Products product;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
