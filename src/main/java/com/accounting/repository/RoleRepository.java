package com.accounting.repository;

import com.accounting.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findRoleById(Long id);

    Role findByDescription(String description);
}
