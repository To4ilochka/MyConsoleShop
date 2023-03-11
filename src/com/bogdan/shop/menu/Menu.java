package com.bogdan.shop.menu;

import com.bogdan.shop.Constants;
import com.bogdan.shop.exception.IncorrectInputTextException;
import com.bogdan.shop.menu.basket.Basket;
import com.bogdan.shop.money.Money;
import com.bogdan.shop.product.ProductList;

import java.util.Scanner;

public class Menu {
    private final Money money;
    private final ProductList productsList;
    private final Basket basket;
    private final Scanner scanner;

    public Menu(Money money, ProductList productsList, Basket basket, Scanner scanner) {
        this.money = money;
        this.productsList = productsList;
        this.basket = basket;
        this.scanner = scanner;
    }

    public void menu() throws IncorrectInputTextException{
        while (true) {
            System.out.printf("""
                    1. Buy products.
                    2. Check my basket.
                    3. Exit.
                    You have: %.2f$
                    """, money.getMoney());
            String scannerString = scanner.next();
            switch (scannerString) {
                case Constants.ONE:
                    productsList.showProducts();
                    basket.addProductsInBasket();
                    this.menu();
                    break;
                case Constants.TWO:
                    basket.showBasket();
                    if (basket.getTotalPrice() != 0) {
                        basket.basketBuyingChoice();
                    }
                    break;
                case Constants.Three:
                    return;
                default:
                    throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
            }
        }
    }
}
