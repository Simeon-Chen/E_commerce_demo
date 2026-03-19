package com.simon.e_commerce.service;

import com.simon.e_commerce.dto.UserRegisterRequest;
import com.simon.e_commerce.model.User;

public interface UserService {

    User getUserById(Integer id);

    Integer register(UserRegisterRequest userRegisterRequest);
}
