package com.simon.e_commerce.service;

import com.simon.e_commerce.dao.UserDao;
import com.simon.e_commerce.dto.UserLoginRequest;
import com.simon.e_commerce.dto.UserRegisterRequest;
import com.simon.e_commerce.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Users getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查email是否已註冊
        Users user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user != null) {
            log.warn("該email {}已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);
        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public Users login(UserLoginRequest userLoginRequest) {
        Users user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查user是否存在
        if (user == null) {
            log.warn("該emai[{}]尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 比較密碼
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }else{
            log.warn("email{}的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
