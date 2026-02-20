package com.simon.e_commerce.controller;

import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;
import com.simon.e_commerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId){
       Product product = productService.getProductById(productId);
        System.out.println("收到請求，ID 為：" + productId);
       if(product != null){
           return ResponseEntity.status(HttpStatus.OK).body(product);
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductRequest productRq){
        Integer productId = productService.createProduct(productRq);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody @Valid ProductRequest productRq){

        Product product = productService.getProductById(productId);
        if(product != null){
            productService.updateProduct(productId, productRq);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
