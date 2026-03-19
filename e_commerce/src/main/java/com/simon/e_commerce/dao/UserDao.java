package com.simon.e_commerce.dao;

import com.simon.e_commerce.dto.UserRegisterRequest;
import com.simon.e_commerce.model.User;

public interface UserDao {

    User getUserById(Integer id);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
