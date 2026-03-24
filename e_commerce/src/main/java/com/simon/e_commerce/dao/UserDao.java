package com.simon.e_commerce.dao;

import com.simon.e_commerce.dto.UserRegisterRequest;
import com.simon.e_commerce.model.Users;

public interface UserDao {

    Users getUserById(Integer id);

    Users getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
