package com.qlyshopphone_backend.broker.producer;

import static com.qlyshopphone_backend.constant.KafkaTopicConstant.*;

import com.qlyshopphone_backend.broker.consumer.KafkaEventConsumer;
import com.qlyshopphone_backend.dto.event.InventoryEvent;
import com.qlyshopphone_backend.mapper.InventoryProducerMapper;
import com.qlyshopphone_backend.model.Products;
import com.qlyshopphone_backend.model.Users;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaTemplate<String, InventoryEvent> inventoryEventKafkaTemplate;
    private final InventoryProducerMapper inventoryProducerMapper;
    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);

    public void sendCreateOrUpdateInventory(Long userId, Long productId, Long quantity) {
        InventoryEvent event = inventoryProducerMapper.toInventoryEvent(userId, productId, quantity);
        log.info("sendCreateOrUpdateInventory event: {}", event);
        inventoryEventKafkaTemplate.send(CREATE_INVENTORY_TOPIC, event);
    }
}
