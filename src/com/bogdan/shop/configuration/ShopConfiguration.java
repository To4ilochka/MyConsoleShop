package com.bogdan.shop.configuration;

import com.bogdan.shop.service.BasketService;
import com.bogdan.shop.model.Products;
import com.bogdan.shop.model.Money;
import com.bogdan.shop.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class ShopConfiguration {
    private Money money;
    private Scanner scanner;
    private ProductService productManagement;
    private BasketService basket;
    private List<Products> productList;

    public Money getMoney() {
        if (money == null) {
            money = new Money();
        }
        return money;
    }

    public List<Products> getProductList() {
        if (productList == null) {
            // TODO change it to productList = List.of()
            productList = List.of(new Products("Potato", 1.70, true),
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
                    new Products("Rise", 0.067, false));
        }
        return productList;
    }

    public Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    public ProductService getProductManagement() {
        if (productManagement == null) {
            productManagement = new ProductService(getProductList(), getBasket(), getScanner());
        }
        return productManagement;
    }

    public BasketService getBasket() {
        if (basket == null) {
            basket = new BasketService(getMoney(), getScanner());
        }
        return basket;
    }
}
