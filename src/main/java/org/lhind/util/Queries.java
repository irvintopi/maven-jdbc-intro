package org.lhind.util;

public final class Queries {

    private Queries() {}

    public static final String FIND_ALL_EMPLOYEES = "SELECT * FROM employees;";

    public static final String FIND_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE employeeNumber = ?;";

    public static final String ADD_EMPLOYEE = "INSERT INTO employees VALUES(?,?,?,?,?,?,?,?);";

    public static final String UPDATE_EMPLOYEE ="UPDATE employees SET lastName = ?, firstName = ?, extension=?, email = ?, officeCode = ?, jobTitle = ?, reportsTo = ? WHERE employeeNumber = ?;";

    public static final String ID_MAX ="SELECT MAX(employeeNumber) from employees;";

    public static final String ID_MAX_O ="SELECT MAX(orderNumber) from orders;";

    public static final String ID_MAX_C ="SELECT MAX(customerNumber) from customers;";

    public static final String ID_MAX_OFF ="SELECT MAX(officeCode) from offices;";

    public static final String FIND_ALL_ORDERS = "SELECT * FROM orders;";

    public static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE orderNumber = ?;";

    public static final String ADD_ORDER = "INSERT INTO orders VALUES(?,?,?,?,?,?,?);";

    public static final String FIND_ALL_CUSTOMERS = "SELECT * FROM customers;";

    public static final String FIND_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE customerNumber = ?;";

    public static final String ADD_CUSTOMER = "INSERT INTO customers VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";

    public static final String UPDATE_ORDER ="UPDATE orders SET orderDate = ?, requiredDate = ?, shippedDate=?, status = ?, comments = ?, customerNumber = ? WHERE orderNumber = ?;";

    public static final String UPDATE_CUSTOMER ="UPDATE customers SET customerName = ?, contactLastName = ?, contactFirstName=?, phone = ?, addressLine1 = ?, addressLine2 = ?, city = ?, state = ?, postalCode = ?, country = ?, salesRepEmployeeNumber = ?, creditLimit = ? WHERE customerNumber = ?;";

    public static final String FIND_ALL_OFFICES = "SELECT * FROM offices;";

    public static final String FIND_OFFICE_BY_ID = "SELECT * FROM offices WHERE officeCode = ?;";

    public static final String ADD_OFFICE = "INSERT INTO offices VALUES(?,?,?,?,?,?,?,?,?);";

    public static final String UPDATE_OFFICE ="UPDATE offices SET city = ?, phone = ?, addressLine1=?, addressLine2 = ?, state = ?, country = ?, postalCode = ?, territory = ? WHERE officeCode = ?;";

    public static final String JOIN = "SELECT * FROM customers LEFT JOIN orders USING(customerNumber);";
}