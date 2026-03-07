package com.simon.e_commerce.controller;

import com.simon.e_commerce.constant.ProductCategory;
import com.simon.e_commerce.dto.ProductQueryParams;
import com.simon.e_commerce.dto.ProductRequest;
import com.simon.e_commerce.model.Product;
import com.simon.e_commerce.service.ProductService;
import com.simon.e_commerce.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
            ) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得 product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得 product 總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId){
       Product product = productService.getProductById(productId);
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

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody @Valid ProductRequest productRq){

        Product product = productService.getProductById(productId);
        if(product != null){
            productService.updateProduct(productId, productRq);
            Product updatedProduct = productService.getProductById(productId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
