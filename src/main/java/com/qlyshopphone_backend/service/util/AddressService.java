package com.qlyshopphone_backend.service.util;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;
import com.qlyshopphone_backend.dto.request.SupplierRequest;
import com.qlyshopphone_backend.dto.request.UserDetailRequest;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.AddressCityRepository;
import com.qlyshopphone_backend.repository.AddressCountryRepository;
import com.qlyshopphone_backend.repository.AddressWardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressCityRepository cityRepository;
    private final AddressCountryRepository countryRepository;
    private final AddressWardRepository wardRepository;

    public  <T extends Address> void setAddressDetails(T address, Long wardId, Long cityId,
                                                       Long countryId, String street) {
        AddressWards ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new ApiRequestException(WARD_NOT_FOUND, HttpStatus.BAD_REQUEST));
        AddressCities city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ApiRequestException(CITY_NOT_FOUND, HttpStatus.BAD_REQUEST));
        AddressCountries country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ApiRequestException(COUNTRY_NOT_FOUND, HttpStatus.BAD_REQUEST));

        address.setStreet(street);
        address.setWard(ward);
        address.setCity(city);
        address.setCountry(country);
    }
}
