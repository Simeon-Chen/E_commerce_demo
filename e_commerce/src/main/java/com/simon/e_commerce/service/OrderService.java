package com.simon.e_commerce.service;

import com.simon.e_commerce.dto.CreateOrderRequest;
import com.simon.e_commerce.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
