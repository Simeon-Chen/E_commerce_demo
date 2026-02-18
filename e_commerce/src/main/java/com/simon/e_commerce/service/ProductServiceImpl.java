package com.simon.e_commerce.service;

import com.simon.e_commerce.dao.ProductDao;
import com.simon.e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    public Product getProductById(int id){
       return productDao.getProductById(id);
    }
}
