package com.bogdan.shop;

import com.bogdan.shop.exception.AmountOfProductLessThanZeroException;
import com.bogdan.shop.exception.GramsOfProductLessThanZeroException;
import com.bogdan.shop.exception.IncorrectInputTextException;
import com.bogdan.shop.exception.NotEnoughMoneyException;
import com.bogdan.shop.money.Money;
import com.bogdan.shop.product.Products;
import com.bogdan.shop.product.ProductList;

import java.util.*;


public class Basket {

    private HashMap<Products, Integer> basketOfProducts = new HashMap<>();
    private double totalPrice;
    private final Money money;
    private final Scanner scanner;

    public Basket(Money money, Scanner scanner) {
        this.money = money;
        this.scanner = scanner;
    }

    public void showBasket() throws IncorrectInputTextException {
        StringBuilder stringBuilder = new StringBuilder();
        totalPrice = 0;
        int counter = 1;
        for (Map.Entry<Products, Integer> entry : basketOfProducts.entrySet()) {
            if (entry.getKey().isCountable()) {
                stringBuilder.append(String.format("%s. %s: amount-%s, prise-%s$;\n", counter, entry.getKey().getName(), entry.getValue(), entry.getKey().getPriceForOne() * entry.getValue()));
            } else {
                stringBuilder.append(String.format("%s. %s: grams-%s, prise-%s$;\n", counter, entry.getKey().getName(), entry.getValue(), entry.getKey().getPriceForOne() * entry.getValue()));
            }
            totalPrice += entry.getKey().getPriceForOne() * entry.getValue();
            counter++;
        }
        if (totalPrice == 0) {
            System.out.println("You haven't got any products.\n");
            throwMenuChoice();
        } else {
            stringBuilder.append(String.format("\nTotal price: %.2f$\nYou have: %.2f$\n", totalPrice, money.getMoney()));
            System.out.println(stringBuilder);
        }
    }

    public void layOutTheProduct() throws IncorrectInputTextException {
        showBasket();
        showLayOutWarning();
        String scannerString;
        double layOutPrice = 0;
        HashMap<Products, Integer> oldBasketOfProducts = new HashMap<>(basketOfProducts);
        while (true) {
            scannerString = scanner.next();
            try {
                if (addLayOutProducts(scannerString, layOutPrice)) {

                } else if (scannerString.equals("Lay_out")) {
                    totalPrice -= layOutPrice;
                    return;
                } else if (scannerString.equals(Constants.STOP)) {
                    basketOfProducts = oldBasketOfProducts;
                    return;
                } else {
                    throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
                }
            } catch (AmountOfProductLessThanZeroException | GramsOfProductLessThanZeroException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void basketBuyingChoice() throws IncorrectInputTextException {
        System.out.println("1. Buy\n2. Lay out the products\n3. Escape to menu\n");
        String scannerString = scanner.next();
        switch (scannerString) {
            case Constants.ONE:
                try {
                    buyBasket();
                } catch (NotEnoughMoneyException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case Constants.TWO:
                layOutTheProduct();
                break;
            case Constants.Three:
                return;
            default:
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private boolean addLayOutProducts(String scannerString, double layOutPrice) throws AmountOfProductLessThanZeroException, GramsOfProductLessThanZeroException {
        int counter = 1;
        String[] strings = scannerString.split("-");
        for (Map.Entry<Products, Integer> entry : basketOfProducts.entrySet()) {
            if (strings.length == 2) {
                if (strings[0].equals(Integer.toString(counter)) || strings[0].equals(entry.getKey().getName())) {
                    if (entry.getKey().isCountable()) {
                        if (Integer.parseInt(strings[1]) < 0 || Integer.parseInt(strings[1]) > entry.getValue()) {
                            throw new AmountOfProductLessThanZeroException();
                        }
                    } else {
                        if (Integer.parseInt(strings[1]) < 0 || Integer.parseInt(strings[1]) > entry.getValue()) {
                            throw new GramsOfProductLessThanZeroException();
                        }
                    }
                    if (Integer.parseInt(strings[1]) == entry.getValue() || Integer.parseInt(strings[1]) == 0) {
                        basketOfProducts.remove(entry.getKey());
                    } else {
                        basketOfProducts.put(entry.getKey(), Integer.parseInt(strings[1]));
                    }
                    layOutPrice += entry.getValue() * entry.getKey().getPriceForOne();
                    return true;
                }
            } else {
                if (strings[0].equals(Integer.toString(counter)) || strings[0].equals(entry.getKey().getName())) {
                    basketOfProducts.remove(entry.getKey());
                    layOutPrice += entry.getValue() * entry.getKey().getPriceForOne();
                    return true;
                }
            }
            counter++;
        }
        return false;
    }

    private void throwMenuChoice() throws IncorrectInputTextException {
        System.out.println("1. Escape to menu\n");
        String scannerString = scanner.next();
        if (scannerString.equals(Constants.ONE)) {
            return;
        } else {
            throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }
    }

    private void buyBasket() throws NotEnoughMoneyException, IncorrectInputTextException {
        if (totalPrice > money.getMoney()) {
            throw new NotEnoughMoneyException("You are have less money, than total price!!!");
        }
        money.setMoney(money.getMoney() - totalPrice);
        basketOfProducts.clear();
        System.out.printf("Operation was successful;)\nYou have: %.2f$\n1. Escape to menu\n", money.getMoney());
        String scannerString = scanner.next();
        if (scannerString.equals(Constants.ONE)) {
            return;
        } else {
            throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }
    }


    private void showLayOutWarning() {
        System.out.println("""
                \u001B[33mWarning!!!
                Example of how to enter products correctly:
                Name or number, or name or number-amount or grams.
                If you write name or number your product will de removed from basket,
                if you write name or number-amount or grams, amount or grams of product will be replaced with your values.
                For example: Potato or 3, or Potato-2 .
                If you write Lay_out, your products will be lay out from the basket.
                If you write Stop, choice of products will be stopped and you lose your lay out products!!!\u001B[0m""");
    }

    public HashMap<Products, Integer> getBasketOfProducts() {
        return basketOfProducts;
    }

    public void setBasketOfProducts(HashMap<Products, Integer> basketOfProducts) {
        this.basketOfProducts = basketOfProducts;
    }
}