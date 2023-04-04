package org.lhind.repository.impl;

import org.lhind.mapper.EmployeeMapper;
import org.lhind.model.entity.Employee;
import org.lhind.util.JdbcConnection;
import org.lhind.util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends BaseRepository<Employee, Integer> {

    public EmployeeRepository() {
        super(new EmployeeMapper());
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_ALL_EMPLOYEES)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Employee employee = getMapper().map(result);
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Error1");
        }
        return employees;
    }

    @Override
    public Employee findById(Integer id) {
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_EMPLOYEE_BY_ID)) {
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

    @Override
    public Boolean exists(Integer id) {
        // TODO: Implement a method which checks if an employee with the given id exists in the employees table
        try {
            Employee e = this.findById(id);
            return e != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean save(Employee employee) {
        /*
         * TODO: Implement a method which adds an employee to the employees table
         *  If the employee exists then the method should instead update the employee
         *
         */
        if (this.exists(employee.getId())) {
            update(employee);
            return true;
        } else {
            int rows = 0;
            try (Connection connection = JdbcConnection.connect();
                 PreparedStatement statement = connection.prepareStatement(Queries.ADD_EMPLOYEE)) {
                if (employee.getId() > this.getMaxId())
                    statement.setInt(1, employee.getId());
                else
                    statement.setInt(1, this.getMaxId() + 1);
                statement.setString(2, employee.getLastName());
                statement.setString(3, employee.getFirstName());
                statement.setString(4, employee.getExtension());
                statement.setString(5, employee.getEmail());
                statement.setString(6, employee.getOfficeCode());
                if (this.exists(employee.getReportsTo())) {
                    statement.setInt(7, employee.getReportsTo());
                } else {
                    System.out.println("Couldnt add employee. Invalid manager");
                    return false;
                }
                statement.setString(8, employee.getJobTitle());
                rows = statement.executeUpdate();
                if (rows == 1) {
                    System.out.println("New employee added to the employees table");
                }
                System.out.println("Rows updated: " + rows);
            } catch (SQLException e) {
                System.err.println("ErrorAdd");
            }
            return rows >= 1;
        }
    }

    public Integer getMaxId() {
        Integer maxId = 2001;
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.ID_MAX)) {
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
    public Integer update(Employee employee) {
        /*
         * TODO: Implement a method which updates an employee with the given Employee instance
         *  The method should then return the number of updated records
         */
        Integer rows = 0;
        if (this.exists(employee.getId())) {
            try (Connection connection = JdbcConnection.connect();
                 PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_EMPLOYEE)) {
                statement.setString(1, employee.getLastName());
                statement.setString(2, employee.getFirstName());
                statement.setString(3, employee.getExtension());
                statement.setString(4, employee.getEmail());
                statement.setString(5, employee.getOfficeCode());
                statement.setString(6, employee.getJobTitle());
                if (this.exists(employee.getReportsTo())) {
                    statement.setInt(7, employee.getReportsTo());
                } else {
                    System.out.println("Couldnt update employee. Invalid manager");
                    return null;
                }
                statement.setInt(8, employee.getId());
                rows = statement.executeUpdate();
                if (rows == 1) {
                    System.out.println("Employee with ID: " + employee.getId() + " is updated");
                }
                System.out.println("Rows updated: " + rows);
            } catch (SQLException e) {
                System.err.println("ErrorUpdate");
            }
        } else {
            System.out.println("Couldnt update. Employee not found\nRows affected: " + rows);
        }
        return rows;
    }

}