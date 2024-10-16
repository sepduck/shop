package com.qlyshopphone_backend.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupSupplierRequest {
    private String groupSupplierName;
    private String note;
}
