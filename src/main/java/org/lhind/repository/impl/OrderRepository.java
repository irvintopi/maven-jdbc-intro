package org.lhind.repository.impl;

import org.lhind.mapper.OrderMapper;
import org.lhind.model.entity.Order;
import org.lhind.util.JdbcConnection;
import org.lhind.util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class OrderRepository extends BaseRepository<Order, Integer>{
    public OrderRepository() {
        super(new OrderMapper());
    }
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_ALL_ORDERS)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = getMapper().map(result);
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return orders;
    }

    @Override
    public Order findById(Integer id) {
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_ORDER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return getMapper().map(result);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return null;
    }

    public Integer getMaxId(){
        Integer maxId = 2001;
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.ID_MAX_O)) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                maxId = result.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ErrorMaxID");
        }
        return maxId;
    }
    @Override
    public Boolean exists(Integer id) {
        try {
            Order o = this.findById(id);
            return o != null;
        }
        catch (NullPointerException e){
            return false;
        }
    }

    @Override
    public Boolean save(Order order) {
        if (this.exists(order.getOrderNumber())){
            update(order);
            return true;
        }
        else {
            int rows=0;
            try(Connection connection = JdbcConnection.connect();
                PreparedStatement statement = connection.prepareStatement(Queries.ADD_ORDER)){
                if (order.getOrderNumber()>this.getMaxId())
                {
                    statement.setInt(1,order.getOrderNumber());
                }
                else
                {
                    statement.setInt(1,this.getMaxId()+1);
                }
                statement.setDate(2, order.getOrderDate());
                statement.setDate(3, order.getRequiredDate());
                statement.setDate(4, order.getShippedDate());
                statement.setString(5, order.getStatus());
                statement.setString(6, order.getComments());
                CustomerRepository customers = new CustomerRepository();

                if (customers.exists(order.getCustomerNumber())){
                    statement.setInt(7,order.getCustomerNumber());
                }
                else
                {
                    System.out.println("Couldnt add order. Invalid customer");
                    return null;
                }
                rows = statement.executeUpdate();
                System.out.println("New order added to the orders table");
                System.out.println("Rows updated: "+rows);
            }
            catch (SQLException e) {
                System.err.println("ErrorAdd");
            }
            return rows>=1;
        }
    }

    @Override
    public Integer update(Order order) {
        Integer rows = 0;
        if (this.exists(order.getOrderNumber())){
            try (Connection connection = JdbcConnection.connect();
                 PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_ORDER)) {
                statement.setDate(1, order.getOrderDate());
                statement.setDate(2, order.getRequiredDate());
                statement.setDate(3, order.getShippedDate());
                statement.setString(4, order.getStatus());
                statement.setString(5, order.getComments());
                CustomerRepository customers = new CustomerRepository();
                if (customers.exists(order.getCustomerNumber())){
                    statement.setInt(6,order.getCustomerNumber());
                }
                else
                {
                    System.out.println("Couldnt update order. Invalid customer");
                    return null;
                }
                statement.setInt(7,order.getOrderNumber());
                rows = statement.executeUpdate();
                if (rows == 1){
                    System.out.println("Order with ID: " +order.getOrderNumber() + " is updated");
                }
                System.out.println("Rows updated: "+rows);
            } catch (SQLException e) {
                System.err.println("ErrorUpdate");
            }
        }
        else {
            System.out.println("Couldnt update. Order not found\nRows affected: "+rows);
        }
        return rows;
    }
}
