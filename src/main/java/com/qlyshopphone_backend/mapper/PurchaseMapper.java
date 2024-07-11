package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.model.Purchase;

public class PurchaseMapper {
    public static PurchaseDTO toDto(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(purchase.getPurchaseId());
        purchaseDTO.setUserId(purchaseDTO.getUserId());
        purchaseDTO.setCustomerInfoId(purchaseDTO.getCustomerInfoId());
        purchaseDTO.setTotalPrice(purchase.getTotalPrice());
        purchaseDTO.setTotalAmount(purchase.getTotalAmount());
        purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
        return purchaseDTO;
    }
}
