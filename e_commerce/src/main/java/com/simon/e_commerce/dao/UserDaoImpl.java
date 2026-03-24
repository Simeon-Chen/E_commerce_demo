package com.simon.e_commerce.dao;

import com.simon.e_commerce.dto.UserRegisterRequest;
import com.simon.e_commerce.model.Users;
import com.simon.e_commerce.rowMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Users getUserById(Integer id) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM users " +
                "WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);

        List<Users> userList = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Users getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM users " +
                "WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<Users> userList = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO users(email, password, created_date, last_modified_date)" +
                "VALUES (:email, :password, :created_date, :last_modified_date)";

        Map<String, Object> params = new HashMap<>();
        params.put("email", userRegisterRequest.getEmail());
        params.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        params.put("created_date", now);
        params.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }
}
