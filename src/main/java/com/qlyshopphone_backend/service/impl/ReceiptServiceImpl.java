package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.broker.producer.KafkaEventProducer;
import com.qlyshopphone_backend.dto.event.InventoryEvent;
import com.qlyshopphone_backend.dto.request.ReceiptDetailRequest;
import com.qlyshopphone_backend.dto.request.ReceiptRequest;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.ChangeReason;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final AuthenticationService authenticationService;
    private final ReceiptRepository receiptRepository;
    private final ReceiptDetailRepository receiptDetailRepository;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final InventoryRepository inventoryRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final KafkaEventProducer kafkaEventProducer;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    @Transactional
    @Override
    public Receipts createReceipt(ReceiptRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        Suppliers supplier = supplierService.findSupplierById(request.getSupplierId());
        Locations location = productService.findLocationById(request.getLocationId());

        Receipts receipts = new Receipts(supplier, location, Status.ACTIVE, authUser.getId());

        Receipts saveReceipt = receiptRepository.save(receipts);

        BigDecimal totalValue = BigDecimal.ZERO;

        List<ReceiptDetails> receiptDetailsList = new ArrayList<>();
        for (ReceiptDetailRequest detail : request.getDetail()) {
            Products product = productService.findProductById(detail.getProductId());

            // Tính tổng giá trị của sản phẩm (purchasePrice * quantity)
            BigDecimal quantity = BigDecimal.valueOf(detail.getQuantity());
            BigDecimal total = detail.getPurchasePrice().multiply(quantity);

            ReceiptDetails receiptDetail = new ReceiptDetails(
                    saveReceipt, product, detail.getQuantity(),
                    detail.getPurchasePrice(), total
            );

            // Cộng tổng giá trị của từng sản phẩm vào tổng giá trị của phiếu nhập
            totalValue = totalValue.add(total);

            receiptDetailsList.add(receiptDetail);

            kafkaEventProducer.sendCreateOrUpdateInventory(authUser.getId(), product.getId(), detail.getQuantity());

        }
        receiptDetailRepository.saveAll(receiptDetailsList);
        saveReceipt.setTotalValue(totalValue);
        receiptRepository.save(saveReceipt);

        return saveReceipt;
    }

    @Transactional
    @Override
    public void createAndUpdateInventory(InventoryEvent event){
        Users authUser = userService.findUserById(event.getUserId());
        logger.info("Receipt Service: {}", authUser.getId());
        Products product = productService.findProductById(event.getProductId());
        Inventory inventory = inventoryRepository.findByProductId(event.getProductId());
        Long previousQuantity = inventory != null ? inventory.getQuantity() : 0L;

        if (inventory == null) {
            inventory = new Inventory(product, event.getQuantity());
            inventoryRepository.save(inventory);

        } else {
            // Nếu tồn kho đã có, cập nhật số lượng
            inventory.setQuantity(inventory.getQuantity() + event.getQuantity());
            inventoryRepository.save(inventory);
        }

        InventoryHistory history = new InventoryHistory(
                product,
                authUser,
                previousQuantity,
                inventory.getQuantity(),
                ChangeReason.IMPORT);
        inventoryHistoryRepository.save(history);
    }

}
