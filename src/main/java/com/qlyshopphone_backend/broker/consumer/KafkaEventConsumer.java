package com.qlyshopphone_backend.broker.consumer;

import static com.qlyshopphone_backend.constant.KafkaTopicConstant.*;
import com.qlyshopphone_backend.dto.event.InventoryEvent;
import com.qlyshopphone_backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventConsumer {
    private final ReceiptService receiptService;
    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @KafkaListener(topics = CREATE_INVENTORY_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void createAndUpdateReceipt(InventoryEvent event){
        log.info("consumer: {}", event.getProductId());
        receiptService.createAndUpdateInventory(event);
    }
}
