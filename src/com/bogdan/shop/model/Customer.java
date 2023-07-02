package com.bogdan.shop.model;

public class Customer {
    private String email;
    private String name;
    private String surname;
    private double money;

    public Customer(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.money = 5 + Math.random() * 60;
    }

    public Customer(String email, String name, String surname, double money) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
