package com.bogdan.shop;

import com.bogdan.shop.menu.Menu;
import com.bogdan.shop.menu.basket.Basket;
import com.bogdan.shop.money.Money;
import com.bogdan.shop.product.ProductList;

import java.util.Scanner;

public class ShopConfiguration {
    private Money money;
    private ProductList productsList;
    private Scanner scanner;
    private Basket basket;
    private Menu menu;

    public Money getMoney() {
        if (money == null) {
            money = new Money();
        }
        return money;
    }

    public ProductList getProductsList() {
        if (productsList == null) {
            productsList = new ProductList(getMoney());
        }
        return productsList;
    }

    public Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public Basket getBasket() {
        if (basket == null) {
            basket = new Basket(getProductsList(), getMoney(), getScanner());
        }
        return basket;
    }

    public Menu getMenu() {
        if (menu == null) {
            menu = new Menu(getMoney(), getProductsList(), getBasket(), getScanner());
        }
        return menu;
    }
}
