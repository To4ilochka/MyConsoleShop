package com.bogdan.shop.product;

import com.bogdan.shop.Basket;
import com.bogdan.shop.Constants;
import com.bogdan.shop.exception.IncorrectInputTextException;

import java.util.*;

public class ProductManagement {
     private final List<Products> productList;
    private final Basket basket;
    private final Scanner scanner;

    public ProductManagement(List<Products> productsList, Basket basket, Scanner scanner) {
        this.productList = new ArrayList<>(productsList);
        this.basket = basket;
        this.scanner = scanner;
    }

    public void buyProducts() throws IncorrectInputTextException {
        showProducts();
        takeProducts();
    }

    public void showProducts() {
        int counter = 1;
        for (Products product : productList) {
            System.out.printf("%s. %s - %s$;\n", counter, product.getName(), product.getPriceForOne());
            counter++;
        }
    }

    private void margeMaps(Map<Products, Integer> mainMap, Map<Products, Integer> secondMap) {
        for (Map.Entry<Products, Integer> entrySecondMap : secondMap.entrySet()) {
            if (mainMap.containsKey(entrySecondMap.getKey())) {
                mainMap.put(entrySecondMap.getKey(), mainMap.get(entrySecondMap.getKey()) + entrySecondMap.getValue());
            } else {
                mainMap.put(entrySecondMap.getKey(), entrySecondMap.getValue());
            }
        }
    }

    private void takeProducts() throws IncorrectInputTextException {
        showBuyingWarning();
        String scannerString;
        String[] strings;
        Map<Products, Integer> mergeBasketOfProducts = new HashMap<>();
        while (true) {
            scannerString = scanner.next();
            strings = scannerString.split("-");
            if (strings.length == 2) {
                findAndAddProduct(strings, mergeBasketOfProducts);
            } else if (Constants.TAKE.equals(scannerString)) {
                if (!mergeBasketOfProducts.isEmpty()) {
                    margeMaps(basket.getBasketOfProducts(), mergeBasketOfProducts);
                }
                return;
            } else if (Constants.STOP.equals(scannerString)) {
                return;
            } else {
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
            }
        }
    }

    private void findAndAddProduct(String[] strings, Map<Products, Integer> mergeBasketOfProducts) {
        int counter = 1;
        for (Products product : productList) {
            if (strings[0].equals(Integer.toString(counter)) || strings[0].equals(product.getName())) {
                if (!mergeBasketOfProducts.containsKey(product)) {
                    mergeBasketOfProducts.put(product, Integer.valueOf(strings[1]));
                } else {
                    mergeBasketOfProducts.put(product, Integer.parseInt(strings[1]) + mergeBasketOfProducts.get(product));
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
