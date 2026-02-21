package com.simon.e_commerce.service;

import com.simon.e_commerce.constant.ProductCategory;
import com.simon.e_commerce.dao.ProductDao;
import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductCategory category, String search) {
        return productDao.getProducts(category, search);
    }

    public Product getProductById(int id){
       return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest productRq) {
        return productDao.createProduct(productRq);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRq) {
        productDao.updateProduct(productId, productRq);
    }

    @Override
    public void deleteProductById(int productId) {
        productDao.deleteProductById(productId);
    }
}
