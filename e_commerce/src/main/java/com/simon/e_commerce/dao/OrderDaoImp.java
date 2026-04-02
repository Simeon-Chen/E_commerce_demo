package com.simon.e_commerce.dao;

import com.simon.e_commerce.dto.OrderQueryParams;
import com.simon.e_commerce.model.Order;
import com.simon.e_commerce.model.OrderItem;
import com.simon.e_commerce.rowMapper.OrderItemMapper;
import com.simon.e_commerce.rowMapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImp implements OrderDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1 ";

        Map<String, Object> params = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, params, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE 1=1 ";

        Map<String, Object> params = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, params, orderQueryParams);

        // sql排序
        sql = sql + " ORDER BY created_date DESC ";

        // 分頁
        sql = sql + "Limit :limit offset :offset";
        params.put("limit", orderQueryParams.getLimit());
        params.put("offset", orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, params, new OrderRowMapper());

        return orderList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "select order_id, user_id, total_amount, created_date, last_modified_date from `order` where order_id = :orderId";

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        List<Order> list = namedParameterJdbcTemplate.query(sql, params, new OrderRowMapper());

        if (list != null && list.size() > 0) {
            return (Order) list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "select oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, p.product_name, p.image_url " +
                " from order_item as oi " +
                " LEFT JOIN product as p ON oi.product_id = p.product_id " +
                " WHERE oi.order_id = :orderId";

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, params, new OrderItemMapper());

        return orderItemList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "insert into `order`(user_id, total_amount, created_date, last_modified_date) " +
        "values(:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("totalAmount", totalAmount);

        Date now = new Date();
        params.put("createdDate", now);
        params.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES(:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] params = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            params[i] = new MapSqlParameterSource();
            params[i].addValue("orderId", orderId);
            params[i].addValue("productId", orderItem.getProductId());
            params[i].addValue("quantity", orderItem.getQuantity());
            params[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        if (orderQueryParams.getUserId() != null) {
            sql += " and user_id = :userId ";
            map.put("userId", orderQueryParams.getUserId());
        }

        return sql;
    }
}
