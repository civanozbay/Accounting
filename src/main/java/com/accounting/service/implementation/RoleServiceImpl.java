package com.accounting.service.implementation;

import com.accounting.dto.RoleDto;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.RoleRepository;
import com.accounting.service.RoleService;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public RoleDto findRoleById(Long id) {
        return mapperUtil.convert(roleRepository.findRoleById(id),new RoleDto());
    }
}
