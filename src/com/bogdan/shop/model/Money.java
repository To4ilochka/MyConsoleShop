package com.bogdan.shop.model;

public class Money {
    private double money = 5 + Math.random() * 60;

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }
}
