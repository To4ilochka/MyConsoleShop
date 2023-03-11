package com.bogdan.shop.product;

import java.util.Objects;

public class Products {
    private final String name;
    private final double price;
    private final boolean isCountable;
    public Products(String name, double price, boolean isCountable) {
        this.name = name;
        this.price = price;
        this.isCountable = isCountable;
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

//  Наверное надо туСтринг

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return name.equals(products.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
