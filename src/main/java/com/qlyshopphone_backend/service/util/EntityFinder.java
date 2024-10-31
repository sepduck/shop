package com.qlyshopphone_backend.service.util;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.OrderStatus;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntityFinder {
    private final SizeAttributeRepository sizeAttributeRepository;
    private final ColorAttributeRepository colorAttributeRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final SupplierRepository supplierRepository;
    private final GroupProductRepository groupProductRepository;
    private final CategoryRepository categoryRepository;
    private final UnitRepository unitRepository;
    private final TrademarkRepository trademarkRepository;
    private final CartRepository cartRepository;
    private final CustomerInfoRepository customerInfoRepository;
    private final AddressRepository addressRepository;
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public Users getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByUsername(authentication.getName());
    }

    public SizeAttributes findSizeById(Long sizeId){
        return sizeAttributeRepository.findById(sizeId)
                .orElseThrow(() -> new ApiRequestException(SIZE_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public ColorAttributes findColorById(Long colorId){
        return colorAttributeRepository.findById(colorId)
                .orElseThrow(() -> new ApiRequestException(COLOR_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Products findProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public ProductVariants findProductVariantByPidAndSidAndCid(Long productId, Long sizeId, Long colorId){
        return productVariantRepository
                .findByPidAndSidAndCid(productId, sizeId, colorId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_VARIANT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public ProductVariants findProductVariantById(Long productVariantId){
        return productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_VARIANT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Users findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Locations findLocationById(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ApiRequestException(LOCATION_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Suppliers findSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(SUPPLIER_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public GroupProducts findGroupProductById(Long groupProductId) {
        return groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new ApiRequestException(GROUP_PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Categories findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Units findUnitById(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> new ApiRequestException(UNIT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Trademarks findTrademarkById(Long trademarkId) {
        return trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new ApiRequestException(TRADEMARK_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Optional<Carts> findByUserAndProductVariant(Long userId, Long productVariantId) {
        return cartRepository.findByUserIdAndProductVariantId(userId, productVariantId, Status.ACTIVE);
    }

    public Carts findByUserAndId(Long userId, Long cartId) {
        return cartRepository.findByUserAndId(userId, cartId, Status.ACTIVE)
                .orElseThrow(() -> new ApiRequestException(CART_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public CustomerInfo findCustomerInfoByUserIdAndId(Long userId, Long customerInfoId) {
        return customerInfoRepository.findCustomerInfoByUserIdAndId(userId, customerInfoId, Status.ACTIVE)
                .orElseThrow(() -> new ApiRequestException(CUSTOMER_INFO_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ApiRequestException(ADDRESS_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Discount findDiscountById(Long discountId){
        return discountRepository.findById(discountId)
                .orElse(null);
    }

    public Orders findOrderByIdAndStatus(Long orderId, OrderStatus status){
        return orderRepository.findOrderByIdAnhStatus(orderId, status)
                .orElseThrow(() -> new ApiRequestException(ORDER_WAITING_FOR_CONFIRMATION_IS_NOT_EXISTING, HttpStatus.BAD_REQUEST));
    }

    public Inventory findInventoryByProductId(Long productId){
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ApiRequestException(INVENTORY_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }



}
