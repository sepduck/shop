package com.qlyshopphone_backend.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BasicMapper {
    private final ModelMapper modelMapper;

    public <T, S> S convertToResponse(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

    public <T, S> List<S> convertToResponseList(List<T> lists, Class<S> type) {
        return lists.contains(null) ? new ArrayList<>() : lists.stream()
                .map(list -> convertToResponse(list, type))
                .toList();
    }

    public <T, S> S convertToRequest(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

    public <T, S> List<S> convertToRequestList(List<T> lists, Class<S> type) {
        return lists.contains(null) ? new ArrayList<>() : lists.stream()
                .map(list -> convertToRequest(list, type))
                .toList();
    }
}
