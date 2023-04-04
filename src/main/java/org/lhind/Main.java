package org.lhind;


import org.lhind.model.entity.Customer;
import org.lhind.model.entity.Employee;
import org.lhind.model.entity.Office;
import org.lhind.model.entity.Order;
import org.lhind.repository.impl.CustomerRepository;
import org.lhind.repository.impl.EmployeeRepository;
import org.lhind.repository.impl.OfficeRepository;
import org.lhind.repository.impl.OrderRepository;

import java.lang.reflect.Array;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();

        boolean exists1 = repository.exists(1002);
        System.out.println("Employee with given id exists: " + exists1);

        OrderRepository orders = new OrderRepository();
        List<Order> ords = orders.findAll();
        System.out.println("\nAll orders\n");
        for (Order o : ords) {
            System.out.println(o.toString());
        }
        CustomerRepository customers = new CustomerRepository();
        List<Customer> custs = customers.findAll();
        System.out.println("\nAll customers\n");
        for (Customer c : custs) {
            System.out.println(c.toString());

        }
    }
}