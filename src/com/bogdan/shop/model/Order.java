package com.bogdan.shop.model;

import java.sql.Timestamp;

public class Order {
    private final int serialNumber;
    private final String customerEmail;
    private final Timestamp timeStamp;
    private final int productId;
    private final int quantity;

    public Order(int serialNumber, String customerEmail, Timestamp timeStamp, int productId, int quantity) {
        this.serialNumber = serialNumber;
        this.customerEmail = customerEmail;
        this.timeStamp = timeStamp;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

}
