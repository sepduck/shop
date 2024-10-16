package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.PurchaseRequest;
import com.qlyshopphone_backend.model.Purchase;
import lombok.Data;

@Data
public class PurchaseMapper {
    public static PurchaseRequest toDto(Purchase purchase) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setPurchaseId(purchase.getPurchaseId());
        purchaseRequest.setUserId(purchaseRequest.getUserId());
        purchaseRequest.setCustomerInfoId(purchaseRequest.getCustomerInfoId());
        purchaseRequest.setTotalPrice(purchase.getTotalPrice());
        purchaseRequest.setTotalAmount(purchase.getTotalAmount());
        purchaseRequest.setPurchaseDate(purchase.getPurchaseDate());
        return purchaseRequest;
    }
}
