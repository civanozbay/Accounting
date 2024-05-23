package com.accounting.repository;

import com.accounting.entity.User;

public interface UserRepository {

    User findByUsername(String username);


}
