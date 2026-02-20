package com.simon.e_commerce.service;

import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;

public interface ProductService {
    Product getProductById(int id);

    Integer createProduct(ProductRequest productRq);

    void updateProduct(Integer productId, ProductRequest productRq);

    void deleteProductById(int productId);
}
