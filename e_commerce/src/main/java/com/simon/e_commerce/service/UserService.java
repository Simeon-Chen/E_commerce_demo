package com.simon.e_commerce.service;

import com.simon.e_commerce.dto.UserLoginRequest;
import com.simon.e_commerce.dto.UserRegisterRequest;
import com.simon.e_commerce.model.Users;

public interface UserService {

    Users getUserById(Integer id);

    Integer register(UserRegisterRequest userRegisterRequest);

    Users login(UserLoginRequest userLoginRequest);
}
