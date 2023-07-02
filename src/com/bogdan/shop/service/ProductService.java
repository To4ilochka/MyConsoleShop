package com.bogdan.shop.service;

import com.bogdan.shop.model.Product;
import com.bogdan.shop.util.Constants;
import com.bogdan.shop.exception.IncorrectInputTextException;

import java.util.*;

public class ProductService {
    private final List<Product> productList;
    private final BasketService basket;
    private final Scanner scanner;

    public ProductService(List<Product> productList, BasketService basket, Scanner scanner) {
        this.productList = new ArrayList<>(productList);
        this.basket = basket;
        this.scanner = scanner;
    }

    public void buyProducts() throws IncorrectInputTextException {
        showProducts();
        takeProducts();
    }

    public void showProducts() {
        int counter = 1;
        for (Product product : productList) {
            System.out.printf("%s. %s - %s$;\n", counter, product.getName(), product.getPriceForOne());
            counter++;
        }
    }

    private void margeMaps(Map<Product, Integer> mainMap, Map<Product, Integer> secondMap) {
        for (Map.Entry<Product, Integer> entrySecondMap : secondMap.entrySet()) {
            if (mainMap.containsKey(entrySecondMap.getKey())) {
                mainMap.put(entrySecondMap.getKey(), mainMap.get(entrySecondMap.getKey()) + entrySecondMap.getValue());
            } else {
                mainMap.put(entrySecondMap.getKey(), entrySecondMap.getValue());
            }
        }
    }

    private void takeProducts() throws IncorrectInputTextException {
        showBuyingWarning();
        String inputString;
        String[] strings;
        Map<Product, Integer> mergeBasketOfProducts = new HashMap<>();
        while (true) {
            inputString = scanner.next();
            strings = inputString.split("-");
            if (strings.length == 2) {
                findAndAddProduct(strings, mergeBasketOfProducts);
            } else if (Constants.TAKE.equals(inputString)) {
                if (!mergeBasketOfProducts.isEmpty()) {
                    margeMaps(basket.getBasketOfProducts(), mergeBasketOfProducts);
                }
                return;
            } else if (Constants.STOP.equals(inputString)) {
                return;
            } else {
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
            }
        }
    }

    private void findAndAddProduct(String[] strings, Map<Product, Integer> mergeBasketOfProducts) {
        int counter = 1;
        for (Product product : productList) {
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
                Example of how to enter product correctly:
                Name or number-amount or grams.
                For example: Potato-2 or 3-2 or 10-500.
                Uncountable product are measured in grams.
                If you write Take, your product will be added to the basket.
                If you write Stop, choice of product will be stopped and you lose your product!!!\u001B[0m""");
    }
}
