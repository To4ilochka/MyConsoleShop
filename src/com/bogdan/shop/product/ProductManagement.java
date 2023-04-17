package com.bogdan.shop.product;

import com.bogdan.shop.Basket;
import com.bogdan.shop.Constants;
import com.bogdan.shop.exception.IncorrectInputTextException;
import com.bogdan.shop.money.Money;

import java.util.HashMap;
import java.util.Scanner;

public class ProductManagement {
    ProductList productList;
    Money money;
    Basket basket;
    Scanner scanner;

    public ProductManagement(ProductList productList, Money money, Basket basket, Scanner scanner) {
        this.productList = productList;
        this.money = money;
        this.basket = basket;
        this.scanner = scanner;
    }

    public void showProducts() {
        int counter = 1;
        for (Products product : productList.getProductsList()) {
            System.out.printf("%s. %s - %s$;\n", counter, product.getName(), product.getPriceForOne());
            counter++;
        }
        System.out.printf("You have: %.2f$\n", money.getMoney());
    }

    public void addProductsInBasket() throws IncorrectInputTextException {
        showBuyingWarning();
        String scannerString;
        String[] strings;
        HashMap<Products, Integer> oldBasketOfProducts = new HashMap<>(basket.getBasketOfProducts());
        while (true) {
            scannerString = scanner.next();
            strings = scannerString.split("-");
            if (strings.length == 2) {
                findAndAddProduct(strings);
            } else if (scannerString.equals(Constants.TAKE)) {
                return;
            } else if (scannerString.equals(Constants.STOP)) {
                basket.setBasketOfProducts(oldBasketOfProducts);
                return;
            } else {
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
            }
        }
    }

    private void findAndAddProduct(String[] strings) {
        int counter = 1;
        for (Products product : productList.getProductsList()) {
            if (strings[0].equals(Integer.toString(counter)) || strings[0].equals(product.getName())) {
                if (basket.getBasketOfProducts().get(product) == null) {
                    basket.getBasketOfProducts().put(product, Integer.valueOf(strings[1]));
                } else {
                    basket.getBasketOfProducts().put(product, Integer.parseInt(strings[1]) + basket.getBasketOfProducts().get(product));
                }
                return;
            }
            counter++;
        }
    }

    private void showBuyingWarning() {
        System.out.println("""
                \u001B[33mWarning!!!
                Example of how to enter products correctly:
                Name or number-amount or grams.
                For example: Potato-2 or 3-2 or 10-500.
                Uncountable products are measured in grams.
                If you write Take, your products will be added to the basket.
                If you write Stop, choice of products will be stopped and you lose your products!!!\u001B[0m""");
    }
}
