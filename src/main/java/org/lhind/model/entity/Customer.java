package org.lhind.model.entity;

import java.util.ArrayList;
import java.util.List;

    public class Customer {
        private List<Order> orders = new ArrayList<>();
        private Integer customerNumber;
        private String customerName;
        private String contactLastName;
        private String contactFirstName;
        private String phone;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private Integer salesRepEmployeeNumber;
        private Double creditLimit;

        public void setState(String state) {
            this.state = state;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public void setContactFirstName(String contactFirstName) {
            this.contactFirstName = contactFirstName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setContactLastName(String contactLastName) {
            this.contactLastName = contactLastName;
        }

        public void setCreditLimit(Double creditLimit) {
            this.creditLimit = creditLimit;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public void setCustomerNumber(Integer customerNumber) {
            this.customerNumber = customerNumber;
        }

        public void setSalesRepEmployeeNumber(Integer salesRepEmployeeNumber) {
            this.salesRepEmployeeNumber = salesRepEmployeeNumber;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getState() {
            return state;
        }

        public String getPhone() {
            return phone;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public String getContactFirstName() {
            return contactFirstName;
        }

        public String getContactLastName() {
            return contactLastName;
        }

        public Double getCreditLimit() {
            return creditLimit;
        }

        public String getCustomerName() {
            return customerName;
        }

        public Integer getCustomerNumber() {
            return customerNumber;
        }

        public Integer getSalesRepEmployeeNumber() {
            return salesRepEmployeeNumber;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "customerNumber=" + customerNumber +
                    ", customerName='" + customerName + '\'' +
                    ", contactLastName='" + contactLastName + '\'' +
                    ", contactFirstName='" + contactFirstName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", addressLine1='" + addressLine1 + '\'' +
                    ", addressLine2='" + addressLine2 + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", postalCode='" + postalCode + '\'' +
                    ", country='" + country + '\'' +
                    ", salesRepEmployeeNumber=" + salesRepEmployeeNumber +
                    ", creditLimit=" + creditLimit +
                    '}';
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

        public List<Order> getOrders() {
            return orders;
        }
    }
