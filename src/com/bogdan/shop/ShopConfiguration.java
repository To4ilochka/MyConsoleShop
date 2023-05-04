package com.bogdan.shop;

import com.bogdan.shop.money.Money;
import com.bogdan.shop.product.ProductManagement;
import com.bogdan.shop.product.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopConfiguration {
    private Money money;
    private Scanner scanner;
    private ProductManagement productManagement;
    private Basket basket;
    private final List<Products> productList = new ArrayList<>();

    public Money getMoney() {
        if (money == null) {
            money = new Money();
        }
        return money;
    }

    public List<Products> getProductList() {
        if (productList.isEmpty()) {
            productList.addAll(List.of(new Products("Potato", 1.70, true),
                    new Products("Cherry", 0.50, true),
                    new Products("Onion", 4, true),
                    new Products("Egg", 7, true),
                    new Products("Sausage", 12.44, true),
                    new Products("Bun", 8.56, true),
                    new Products("Orange", 16, true),
                    new Products("NEmik", 20.3, true),
                    new Products("Sugar", 0.032, false),
                    new Products("Meat", 0.14, false),
                    new Products("Flour", 0.019, false),
                    new Products("Salt", 0.026, false),
                    new Products("Rise", 0.067, false)));
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
            productManagement = new ProductManagement(getProductList(), getBasket(), getScanner());
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
