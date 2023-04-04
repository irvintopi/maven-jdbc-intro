package org.lhind.repository.impl;
import org.lhind.mapper.*;
import org.lhind.model.entity.*;
import org.lhind.util.JdbcConnection;
import org.lhind.util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CustomerRepository extends BaseRepository<Customer, Integer>{

    public CustomerRepository() {
        super(new CustomerMapper());
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_ALL_CUSTOMERS)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Customer customer = getMapper().map(result);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id) {
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_CUSTOMER_BY_ID)) {
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
             PreparedStatement statement = connection.prepareStatement(Queries.ID_MAX_C)) {
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
            Customer c = this.findById(id);
            return c != null;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean save(Customer customer) {
        if (this.exists(customer.getCustomerNumber())){
            update(customer);
            return true;
        }
        else {
            int rows=0;
            try(Connection connection = JdbcConnection.connect();
                PreparedStatement statement = connection.prepareStatement(Queries.ADD_CUSTOMER)){
                if (customer.getCustomerNumber()>this.getMaxId())
                    statement.setInt(1,customer.getCustomerNumber());
                else
                    statement.setInt(1,this.getMaxId()+1);
                statement.setString(2, customer.getCustomerName());
                statement.setString(3, customer.getContactLastName());
                statement.setString(4, customer.getContactFirstName());
                statement.setString(5, customer.getPhone());
                statement.setString(6, customer.getAddressLine1());
                statement.setString(7, customer.getAddressLine2());
                statement.setString(8, customer.getCity());
                statement.setString(9, customer.getState());
                statement.setString(10, customer.getPostalCode());
                statement.setString(11, customer.getCountry());
                EmployeeRepository employees = new EmployeeRepository();

                if (employees.exists(customer.getSalesRepEmployeeNumber())){
                    statement.setInt(12, customer.getSalesRepEmployeeNumber());
                }
                else
                {
                    System.out.println("Couldnt add customer. Invalid employee");
                    return null;
                }
                statement.setDouble(13, customer.getCreditLimit());
                rows = statement.executeUpdate();
                if (rows == 1){
                    System.out.println("New customer added to the customers table");
                }
                System.out.println("Rows updated: "+rows);
            }
            catch (SQLException e) {
                System.err.println("ErrorAdd");
            }
            return rows>=1;
        }
    }

    @Override
    public Integer update(Customer customer) {
        Integer rows = 0;
        if (this.exists(customer.getCustomerNumber())){
            try (Connection connection = JdbcConnection.connect();
                 PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_CUSTOMER)) {
                statement.setString(1, customer.getCustomerName());
                statement.setString(2, customer.getContactLastName());
                statement.setString(3, customer.getContactFirstName());
                statement.setString(4, customer.getPhone());
                statement.setString(5, customer.getAddressLine1());
                statement.setString(6, customer.getAddressLine2());
                statement.setString(7, customer.getCity());
                statement.setString(8, customer.getState());
                statement.setString(9, customer.getPostalCode());
                statement.setString(10, customer.getCountry());
                EmployeeRepository employees = new EmployeeRepository();
                if (employees.exists(customer.getSalesRepEmployeeNumber())){
                    statement.setInt(11, customer.getSalesRepEmployeeNumber());
                }
                else
                {
                    System.out.println("Couldnt update customer. Invalid employee");
                    return null;
                }
                statement.setDouble(12, customer.getCreditLimit());
                statement.setInt(13, customer.getCustomerNumber());
                rows = statement.executeUpdate();
                if (rows == 1){
                    System.out.println("Customer with ID: " +customer.getCustomerNumber() + " is updated");
                }
                System.out.println("Rows updated: "+rows);
            } catch (SQLException e) {
                System.err.println("ErrorUpdate");
            }
        }
        else {
            System.out.println("Couldnt update. Customer not found\nRows affected: "+rows);
        }
        return rows;
    }
    public Boolean join(){
        List<Customer> customerss = new ArrayList<>();
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.JOIN)) {
            ResultSet result = statement.executeQuery();
            while (result.next()){
                String cname = result.getString("customerName");
                int oNr = result.getInt("orderNumber");
                Customer c = new Customer();
                Order o = new Order();
                o.setOrderNumber(oNr);
                int check = 0;
                for (Customer cust : customerss) {
                    if(cust.getCustomerName().equalsIgnoreCase(cname)){
                        check = 1;
                        break;
                    }
                }
                if (check == 1){
                    System.out.println(o.getOrderNumber());
                }
                else {
                    c.setCustomerName(cname);
                    customerss.add(c);
                    System.out.println("----------------------------------");
                    System.out.println("Customer " + c.getCustomerName());
                    System.out.println("Orders:\n"+o.getOrderNumber());
                }
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        return true;
    }
}