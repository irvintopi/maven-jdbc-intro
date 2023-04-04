package org.lhind.mapper;

import org.lhind.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
public class OrderMapper extends Mapper<Order>{
    @Override
    public Order map(ResultSet result) throws SQLException {
        Order order = new Order();
        order.setOrderNumber(result.getInt("orderNumber"));
        order.setOrderDate(result.getDate("orderDate"));
        order.setRequiredDate(result.getDate("requiredDate"));
        order.setShippedDate(result.getDate("shippedDate"));
        order.setStatus(result.getString("status"));
        order.setComments(result.getString("comments"));
        order.setCustomerNumber(result.getInt("customerNumber"));
        return order;
    }
}