package com.accounting.service.feignClients;

import com.accounting.dto.addressApi.Country;
import com.accounting.dto.addressApi.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "address-api", url = "${address.api.url}")
public interface AddressFeignClient {

    @GetMapping(value = "/getaccesstoken",consumes = MediaType.APPLICATION_JSON_VALUE)
    TokenDto auth(@RequestHeader("user-email")String email,@RequestHeader("api-token")String apiToken);

    @GetMapping(value = "/countries", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Country> getCountryList(@RequestHeader("Authorization") String bearerToken);
}
