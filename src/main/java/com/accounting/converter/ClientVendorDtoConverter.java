package com.accounting.converter;

import com.accounting.dto.ClientVendorDto;
import com.accounting.dto.UserDto;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDtoConverter implements Converter<String , ClientVendorDto> {

    @Override
    public ClientVendorDto convert(String source) {
        return null;
    }
}
