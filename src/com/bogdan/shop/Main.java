package com.bogdan.shop;

import com.bogdan.shop.configuration.ShopConfiguration;
import com.bogdan.shop.exception.IncorrectInputTextException;
import com.bogdan.shop.util.Constants;


public class Main {
    public static void main(String[] args) {
        ShopConfiguration shopConfiguration = new ShopConfiguration();
        while (true) {
            if (shopConfiguration.getAccountService().getCustomer() != null) {
                accountedChoice(shopConfiguration);
            } else {
                anonymousChoice(shopConfiguration);
            }
        }
    }

    private static void accountedChoice(ShopConfiguration shopConfiguration) {
        System.out.printf("""
                1. Buy product.
                2. Check my basket.
                3. Account.
                4. Purchase history.
                5. Exit.
                You have: %.2f$
                """, shopConfiguration.getAccountService().getCustomer().getMoney());
        String inputString = shopConfiguration.getScanner().next();
        switch (inputString) {
            case Constants.ONE:
                shopConfiguration.getProductService().buyProducts();
                break;
            case Constants.TWO:
                shopConfiguration.getBasketService().basketMenu();
                break;
            case Constants.THREE:
                shopConfiguration.getAccountService().accountChoice();
                break;
            case Constants.FOUR:
                shopConfiguration.getOrderService().showHistory();
                break;
            case Constants.FIVE:
                System.exit(0);
            default:
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }

    }

    private static void anonymousChoice(ShopConfiguration shopConfiguration) {
        System.out.print("""
                1. Buy product.
                2. Check my basket.
                3. Account.
                4. Exit.
                """);
        String inputString = shopConfiguration.getScanner().next();
        switch (inputString) {
            case Constants.ONE:
            case Constants.TWO:
                System.out.println("\u001B[33mYou cannot do this action without logging in\u001B[0m");
                break;
            case Constants.THREE:
                shopConfiguration.getAccountService().accountChoice();
                break;
            case Constants.FOUR:
                System.exit(0);
            default:
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }

    }
}
