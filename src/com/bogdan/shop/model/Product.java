package com.bogdan.shop.model;

import java.util.Objects;

public class Product {
    private final int id;
    private final String name;
    private final double price;
//    переделать логику для высчитывания цены для uncountable, теперь у них цена
    private final boolean isCountable;
    public Product(int id, String name, double price, boolean isCountable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isCountable = isCountable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPriceForOne() {
        return price;
    }

    public boolean isCountable() {
        return isCountable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
