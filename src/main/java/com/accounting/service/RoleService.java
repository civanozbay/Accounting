package com.accounting.service;

import com.accounting.dto.RoleDto;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    RoleDto findRoleById(Long id);
}
