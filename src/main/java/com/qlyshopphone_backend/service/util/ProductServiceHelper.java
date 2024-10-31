package com.qlyshopphone_backend.service.util;

import com.qlyshopphone_backend.dto.request.ProductAttributeRequest;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductServiceHelper {
    private final BasicMapper basicMapper;

    public <T> ProductAttributeResponse updateAttribute(
            Long id,
            ProductAttributeRequest request,
            Function<Long, Optional<T>> findByIdFunction,
            Consumer<T> saveFunction,
            Function<T, String> getNameFunction,
            BiConsumer<T, String> setNameFunction,
            String notFoundMessage) {

        return findByIdFunction.apply(id)
                .map(attribute -> {
                    // Kiểm tra xem tên mới có khác không
                    if (!getNameFunction.apply(attribute).equals(request.getName())) {
                        setNameFunction.accept(attribute, request.getName());
                        saveFunction.accept(attribute); // Lưu lại thực thể đã cập nhật
                    }
                    return basicMapper.convertToResponse(attribute, ProductAttributeResponse.class);
                })
                .orElseThrow(() -> new ApiRequestException(notFoundMessage, HttpStatus.NOT_FOUND));
    }
}
