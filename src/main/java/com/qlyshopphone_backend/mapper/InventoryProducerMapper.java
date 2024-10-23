package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.event.InventoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryProducerMapper {
    public InventoryEvent toInventoryEvent(Long userId, Long productId, Long quantity) {
        return InventoryEvent.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
