package com.congthuc.rabbitmqdemo.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * Author: pct
 * 5/9/2019
 */

public class Order implements Serializable {

    private String id;
    private String productId;
    private String productDescription;
    private double amount;

    public Order() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
