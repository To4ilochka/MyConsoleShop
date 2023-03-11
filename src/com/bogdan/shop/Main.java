package com.bogdan.shop;

import com.bogdan.shop.exception.IncorrectInputTextException;

public class Main {
    public static void main(String[] args) {
        ShopConfiguration shopConfiguration = new ShopConfiguration();
        try {
            shopConfiguration.getMenu().menu();
        } catch (IncorrectInputTextException e) {
            System.out.println(e.getMessage());
        }
    }
}
