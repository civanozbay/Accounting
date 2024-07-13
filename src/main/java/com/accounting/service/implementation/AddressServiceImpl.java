package com.accounting.service.implementation;

import com.accounting.dto.addressApi.Country;
import com.accounting.dto.addressApi.TokenDto;
import com.accounting.service.AddressService;
import com.accounting.service.feignClients.AddressFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressFeignClient addressFeignClient;
    public AddressServiceImpl(AddressFeignClient addressFeignClient) {
        this.addressFeignClient = addressFeignClient;
    }
    @Value("baha@cybertekschool.com")
    private String email;

    @Value("ANDF0EPKs6ZLHTUan_QFtx1H82498iy2XrIUd-R8fY4r7DPo0WoVTZpKg5hzkAoQrZ0")
    private String userToken;

    private String getBearerToken() {
        TokenDto bearerToken = addressFeignClient.auth(this.email, this.userToken);
        log.info("token retrieved for address api: " + bearerToken.getAuthToken());
        return "Bearer " + bearerToken.getAuthToken();
    }
    @Override
    public List<String> getCountryList() {
        List<Country> countryList = addressFeignClient.getCountryList(getBearerToken());
        log.info("Total Country size is :" + countryList.size());
        return countryList.stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());

    }
}
