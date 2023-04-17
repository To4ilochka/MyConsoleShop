package com.bogdan.shop;

import com.bogdan.shop.money.Money;
import com.bogdan.shop.product.ProductList;
import com.bogdan.shop.product.ProductManagement;

import java.util.Scanner;

public class ShopConfiguration {
    private Money money;
    private ProductList productList;
    private Scanner scanner;
    private ProductManagement productManagement;
    private Basket basket;

    public Money getMoney() {
        if (money == null) {
            money = new Money();
        }
        return money;
    }

    public ProductList getProductList() {
        if (productList == null) {
            productList = new ProductList(getMoney());
        }
        return productList;
    }

    public Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    public ProductManagement getProductManagement() {
        if (productManagement == null) {
            productManagement = new ProductManagement(getProductList(), getMoney(), getBasket(), getScanner());
        }
        return productManagement;
    }

    public Basket getBasket() {
        if (basket == null) {
            basket = new Basket(getMoney(), getScanner());
        }
        return basket;
    }
}
