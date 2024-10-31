package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.repository.projection.CartProjection;
import com.qlyshopphone_backend.service.CartService;
import com.qlyshopphone_backend.service.util.EntityFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final EntityFinder entityFinder;

    @Override
    public boolean addProductToCart(AddToCartRequest request) {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();
        ProductVariants productVariant = entityFinder.findProductVariantById(request.getProductVariantId());
        Optional<Carts> cartOptional = entityFinder.findByUserAndProductVariant(authUser.getId(), productVariant.getId());

        Carts cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
        } else {
            cart = new Carts(productVariant, authUser, request.getProductVariantId(), false, Status.ACTIVE);
            cartRepository.save(cart);
        }
        cartRepository.save(cart);
        return true;
    }

    @Override
    public List<CartProjection> getUserActiveCarts() {
        Users users = entityFinder.getCurrentAuthenticatedUser();
        return cartRepository.findUserActiveCarts(users.getId(), Status.ACTIVE);
    }


    @Override
    public boolean removeProductVariantFromCart(CartIdsRequest request) {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();
        List<Carts> cartsList = cartRepository.findAllByUserIdAndCartIdIn(authUser.getId(), request.getCartIds());

        for (Carts carts : cartsList) {
            carts.setStatus(Status.DELETED);
        }
        cartRepository.saveAll(cartsList);
        return true;
    }




}
