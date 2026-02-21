package com.simon.e_commerce.dao;

import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;
import com.simon.e_commerce.rowMapper.ProductRowMapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts() {
        String sql = "select product_id, product_name, category, image_url, price, " +
                "stock, description, created_date, " +
                "last_modified_date from product";
        Map<String, Object> map = new HashMap<>();

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map,new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductById(int id) {
        String sql = "select product_id, product_name, category, image_url, price, " +
                "stock, description, created_date, " +
                "last_modified_date from product where product_id = :product_id";

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", id);

        List<Product> queryResult = namedParameterJdbcTemplate.query(sql, map,new ProductRowMapper());

        if(!queryResult.isEmpty() && queryResult.get(0) != null){
            return queryResult.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRq) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, " +
                "stock, description, created_date, " +
                "last_modified_date) VALUES (:product_name, :category, :image_url, :price, " +
                ":stock, :description, :created_date, :last_modified_date) ";

        Map<String, Object> params = new HashMap<>();
        params.put("product_name", productRq.getProductName());
        params.put("category", productRq.getCategory().name());
        params.put("image_url", productRq.getImageUrl());
        params.put("price", productRq.getPrice());
        params.put("stock", productRq.getStock());
        params.put("description", productRq.getDescription());

        Date date = new Date();
        params.put("created_date", date);
        params.put("last_modified_date", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRq) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, "
                + "price = :price, stock = :stock, description = :description, last_modified_date = :last_modified_date" +
                " WHERE product_id = :product_id";
        Map<String, Object> params = new HashMap<>();
        params.put("product_id", productId);
        params.put("productName", productRq.getProductName());
        params.put("category", productRq.getCategory().name());
        params.put("imageUrl", productRq.getImageUrl());
        params.put("price", productRq.getPrice());
        params.put("stock", productRq.getStock());
        params.put("description", productRq.getDescription());
        Date date = new Date();
        params.put("last_modified_date", date);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :product_id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("product_id", productId));

    }
}
