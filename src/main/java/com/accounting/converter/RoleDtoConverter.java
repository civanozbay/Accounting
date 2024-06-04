package com.accounting.converter;

import com.accounting.dto.RoleDto;
import com.accounting.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter  implements Converter<String, RoleDto> {

    private final RoleService roleService;

    public RoleDtoConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDto convert(String id) {
        if(id.isBlank() || id==null){
            return null;
        }
        return roleService.findRoleById(Long.parseLong(id));
    }
}
