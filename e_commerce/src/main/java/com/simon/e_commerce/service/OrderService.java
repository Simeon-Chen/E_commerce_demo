package com.simon.e_commerce.service;

import com.simon.e_commerce.dto.CreateOrderRequest;
import com.simon.e_commerce.dto.OrderQueryParams;
import com.simon.e_commerce.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
