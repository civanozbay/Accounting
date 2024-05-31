package com.accounting.repository;

import com.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findUserById(Long id);

    List<User> findAll();

    List<User> findAllByCompany_TitleAndRole_Description (String companyTitle,String role);

    List<User> findAllByRole_Description(String role);

    List<User> findAllByCompany_Title(String companyTitle);

}
