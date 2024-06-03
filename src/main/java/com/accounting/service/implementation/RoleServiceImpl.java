package com.accounting.service.implementation;

import com.accounting.dto.RoleDto;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.RoleRepository;
import com.accounting.service.RoleService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil,SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService=securityService;
    }


    @Override
    public RoleDto findRoleById(Long id) {
        return mapperUtil.convert(roleRepository.findRoleById(id),new RoleDto());
    }

    @Override
    public List<RoleDto> getFilteredRolesForCurrentUser() {
        if(securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("root user")){
            List<RoleDto> list = new ArrayList<>();
            list.add(mapperUtil.convert(roleRepository.findByDescription("Admin"),new RoleDto()));
            return list;
        }else{
            return roleRepository.findAll()
                    .stream().map(role -> mapperUtil.convert(role,new RoleDto()))
                    .collect(Collectors.toList());
        }
    }
}
