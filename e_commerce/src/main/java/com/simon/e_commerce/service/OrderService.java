package com.simon.e_commerce.service;

import com.simon.e_commerce.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
