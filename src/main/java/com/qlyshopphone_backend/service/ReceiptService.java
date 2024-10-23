package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.event.InventoryEvent;
import com.qlyshopphone_backend.dto.request.ReceiptRequest;
import com.qlyshopphone_backend.model.Receipts;

public interface ReceiptService {
    Receipts createReceipt(ReceiptRequest request);

    void createAndUpdateInventory(InventoryEvent event);
}
