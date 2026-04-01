package com.simon.e_commerce.rowMapper;

import com.simon.e_commerce.model.Order;
import com.simon.e_commerce.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem OrderItem = new OrderItem();
        OrderItem.setOrderItemId(rs.getInt("order_item_id"));
        OrderItem.setOrderId(rs.getInt("order_id"));
        OrderItem.setProductId(rs.getInt("product_id"));
        OrderItem.setQuantity(rs.getInt("quantity"));

        OrderItem.setProductName(rs.getString("product_name"));
        OrderItem.setImageUrl(rs.getString("image_url"));

        return OrderItem;
    }
}
