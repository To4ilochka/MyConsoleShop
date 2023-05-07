package com.bogdan.shop;

import com.bogdan.shop.exception.IncorrectInputTextException;


public class Main {
    public static void main(String[] args) throws IncorrectInputTextException {
        ShopConfiguration shopConfiguration = new ShopConfiguration();
        while (true) {
            System.out.printf("""
                    1. Buy products.
                    2. Check my basket.
                    3. Exit.
                    You have: %.2f$
                    """, shopConfiguration.getMoney().getMoney());
            String scannerString = shopConfiguration.getScanner().next();
            switch (scannerString) {
                case Constants.ONE:
                    shopConfiguration.getProductManagement().buyProducts();
                    break;
                case Constants.TWO:
                    shopConfiguration.getBasket().basketMenu();
                    break;
                case Constants.THREE:
                    return;
                default:
                    throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
            }
        }
    }
}
