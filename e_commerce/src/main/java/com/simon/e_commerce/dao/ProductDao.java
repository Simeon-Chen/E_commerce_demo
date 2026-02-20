package com.simon.e_commerce.dao;

import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;

public interface ProductDao {
    Product getProductById(int id);

    Integer createProduct(ProductRequest productRq);

    void updateProduct(Integer productId, ProductRequest productRq);
}
