package com.qlyshopphone_backend.controller.admin;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.ReceiptRequest;
import com.qlyshopphone_backend.model.Receipts;
import com.qlyshopphone_backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(API_V1_RECEIPT)
@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping()
    public ResponseEntity<Receipts> createReceipt(@RequestBody ReceiptRequest request) {
        return ResponseEntity.ok(receiptService.createReceipt(request));
    }
}
