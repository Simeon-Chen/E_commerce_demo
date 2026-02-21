package com.simon.e_commerce.service;

import com.simon.e_commerce.constant.ProductCategory;
import com.simon.e_commerce.dto.ProductQueryParams;
import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(int id);

    Integer createProduct(ProductRequest productRq);

    void updateProduct(Integer productId, ProductRequest productRq);

    void deleteProductById(int productId);
}
