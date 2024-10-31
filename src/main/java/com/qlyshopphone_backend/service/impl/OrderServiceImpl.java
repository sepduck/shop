package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.constant.ErrorMessage;
import com.qlyshopphone_backend.dto.request.CheckoutData;
import com.qlyshopphone_backend.dto.request.OrderItemRequest;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.OrderStatus;
import com.qlyshopphone_backend.model.enums.PaymentMethod;
import com.qlyshopphone_backend.model.enums.Role;
import com.qlyshopphone_backend.repository.InventoryRepository;
import com.qlyshopphone_backend.repository.OrderItemRepository;
import com.qlyshopphone_backend.repository.OrderRepository;
import com.qlyshopphone_backend.repository.ProductVariantRepository;
import com.qlyshopphone_backend.service.OrderService;
import com.qlyshopphone_backend.service.util.EntityFinder;
import com.qlyshopphone_backend.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final EntityFinder entityFinder;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserServiceHelper userServiceHelper;

    @Override
    public boolean placeOrder(CheckoutData data) {
        Users currentUser = entityFinder.getCurrentAuthenticatedUser();
        CustomerInfo customerInfo = entityFinder.findCustomerInfoByUserIdAndId(currentUser.getId(), data.getCustomerInfoId());

        if (data.getItems() == null || data.getItems().isEmpty()) {
            throw new ApiRequestException(NO_PRODUCTS_HAVE_BEEN_SELECTED_TO_ORDER, HttpStatus.BAD_REQUEST);
        }

        Orders order = new Orders();
        order.setUser(currentUser);
        order.setCustomerInfo(customerInfo);
        order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
        order.setStatus(OrderStatus.PENDING_CONFIRMATION);

        if (data.getDiscountId() != null) {
            Discount discount = entityFinder.findDiscountById(data.getDiscountId());
            order.setDiscount(discount);
        }

        // Tính tổng số tiền
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : data.getItems()) {
            ProductVariants productVariant = entityFinder.findProductVariantById(itemRequest.getProductVariantId());
            Inventory inventory = entityFinder.findInventoryByProductId(productVariant.getProduct().getId());

            // Kiểm tra số lượng tồn kho
            if (productVariant.getStock() < itemRequest.getQuantity()) {
                throw new ApiRequestException(INSUFFICIENT_INVENTORY_FOR_THE_PRODUCT + productVariant.getProduct().getName(), HttpStatus.BAD_REQUEST);
            }

            // Trừ số lượng trong kho của ProductVariant
            productVariant.setStock(productVariant.getStock() - itemRequest.getQuantity());
            productVariantRepository.save(productVariant);

            // Giảm số lượng trong Inventory nếu tồn tại
            inventory.setQuantity(inventory.getQuantity() - itemRequest.getQuantity());
            inventoryRepository.save(inventory);

            BigDecimal itemTotal = productVariant.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            OrderItem orderItem = new OrderItem(productVariant, itemRequest.getQuantity(), order, productVariant.getPrice());
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return true;
    }

    @Override
    public boolean confirmOrder(Long orderId){
        userServiceHelper.checkAdminRole();
        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean dispatchOrder(Long orderId){
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.DISPATCHING);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean completeDispatchOrder(Long orderId){
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.DISPATCHED);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean markOrderInTransit(Long orderId){
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.IN_TRANSIT);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean markOrderOnDelivery(Long orderId) {
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.ON_DELIVERY);
        orderRepository.save(order);
        return true;    }

    @Override
    public boolean completeOrderDelivery(Long orderId) {
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean returnOrder(Long orderId) {
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.RETURNED);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean cancelledOrder(Long orderId) {
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.CANCELLED);

        // Tăng lại số lượng tồn kho cho từng sản phẩm trong đơn hàng
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            ProductVariants productVariant = item.getProductVariant();

            // Cộng lại số lượng tồn kho cho ProductVariant
            productVariant.setStock(productVariant.getStock() + item.getQuantity());
            productVariantRepository.save(productVariant); // Lưu lại số lượng tồn kho

            // Cộng lại số lượng tồn kho cho Inventory
            Inventory inventory = inventoryRepository.findByProductId(productVariant.getProduct().getId())
                    .orElseThrow(() -> new ApiRequestException(EXISTING_PRODUCT_WAREHOUSE_NOT_FOUND + productVariant.getProduct().getName(), HttpStatus.NOT_FOUND));
            inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
            inventoryRepository.save(inventory); // Lưu lại tồn kho
        }

        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean receivedOrder(Long orderId) {
        userServiceHelper.checkAdminRole();

        Orders order = entityFinder.findOrderByIdAndStatus(orderId, OrderStatus.PENDING_CONFIRMATION);
        order.setStatus(OrderStatus.RECEIVED);
        orderRepository.save(order);

        return true;
    }


}
