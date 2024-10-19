package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.PurchaseRequest;
import com.qlyshopphone_backend.model.Purchases;
import lombok.Data;

@Data
public class PurchaseMapper {
    public static PurchaseRequest toDto(Purchases purchases) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setPurchaseId(purchases.getId());
        purchaseRequest.setUserId(purchaseRequest.getUserId());
        purchaseRequest.setCustomerInfoId(purchaseRequest.getCustomerInfoId());
//        purchaseRequest.setTotalPrice(purchase.getTotalPrice());
        purchaseRequest.setTotalAmount(purchases.getTotalAmount());
        purchaseRequest.setPurchaseDate(purchases.getPurchaseDate());
        return purchaseRequest;
    }
}
