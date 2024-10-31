package com.qlyshopphone_backend.dto.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEvent {
    private Long userId;
    private Long productId;
    private Long quantity;
}
