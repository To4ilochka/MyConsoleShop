package com.bogdan.shop.product;

import com.bogdan.shop.money.Money;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private final List<Products> productsList = new ArrayList<>();
    private final Money money;
    public ProductList(Money money) {
        this.money = money;
        productsList.add(new Products("Potato", 1.70, true));
        productsList.add(new Products("Cherry", 0.50, true));
        productsList.add(new Products("Onion", 4, true));
        productsList.add(new Products("Egg", 7, true));
        productsList.add(new Products("Sausage", 12.44, true));
        productsList.add(new Products("Bun", 8.56, true));
        productsList.add(new Products("Orange", 16, true));
        productsList.add(new Products("NEmik", 20.3, true));
        productsList.add(new Products("Sugar", 0.032, false));
        productsList.add(new Products("Meat", 0.14, false));
        productsList.add(new Products("Flour", 0.019, false));
        productsList.add(new Products("Salt", 0.026, false));
        productsList.add(new Products("Rise", 0.067, false));
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void showProducts() {
        int counter = 1;
        for (Products product : productsList) {
            System.out.printf("%s. %s - %s$;\n", counter, product.getName(), product.getPriceForOne());
            counter++;
        }
        System.out.printf("You have: %.2f$\n", money.getMoney());
    }
}
