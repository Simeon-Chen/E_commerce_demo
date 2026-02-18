package com.simon.e_commerce.dao;

import com.simon.e_commerce.model.Product;
import com.simon.e_commerce.rowMapper.ProductRowMapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Product getProductById(int id) {
        String sql = "select product_id, product_name, category, image_url, price, " +
                "stock, description, created_date, " +
                "last_modified_date from product where product_id = :product_id";

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", id);

        List<Product> queryResult = jdbcTemplate.query(sql, map,new ProductRowMapper());

        if(!queryResult.isEmpty() && queryResult.get(0) != null){
            return queryResult.get(0);
        } else {
            return null;
        }
    }
}
