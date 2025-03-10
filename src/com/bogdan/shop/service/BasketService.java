package com.bogdan.shop.service;

import com.bogdan.shop.model.Product;
import com.bogdan.shop.util.Constants;
import com.bogdan.shop.exception.AmountOfProductLessThanZeroException;
import com.bogdan.shop.exception.GramsOfProductLessThanZeroException;
import com.bogdan.shop.exception.IncorrectInputTextException;
import com.bogdan.shop.exception.NotEnoughMoneyException;

import java.util.*;

public class BasketService {

    private double totalPrice;
    private final Scanner scanner;
    private final OrderService orderService;
    private final AccountService accountService;
    private final Map<Product, Integer> basketOfProducts = new HashMap<>();

    public BasketService(AccountService accountService, OrderService orderService, Scanner scanner) {
        this.accountService = accountService;
        this.orderService = orderService;
        this.scanner = scanner;
    }

    public void basketMenu() throws IncorrectInputTextException {
        showBasket();
        if (totalPrice != 0) {
            basketBuyingChoice();
        }
    }

    public Map<Product, Integer> getBasketOfProducts() {
        return basketOfProducts;
    }

    private void basketBuyingChoice() throws IncorrectInputTextException {
        System.out.println(Constants.LAY_OUT_CHOICE);
        String inputString = scanner.next();
        switch (inputString) {
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
            case Constants.THREE:
                return;
            default:
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }
    }

    private void layOutTheProduct() throws IncorrectInputTextException {
        showBasket();
        showLayOutWarning();
        String inputString;
        String[] splitString;
        double layOutPrice = 0;
        Map<Product, Integer> basketOfLayOutProducts = new HashMap<>();
        while (true) {
            inputString = scanner.next();
            try {
                splitString = splitInputStr(inputString);
                if (inputString.equals("Lay_out")) {
                    totalPrice -= layOutPrice;
                    return;
                } else if (Constants.STOP.equals(inputString)) {
                    return;
                } else if (isCorrectInputStr(splitString)) {
                    addLayOutProducts(splitString, layOutPrice);
                } else {
                    throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
                }
            } catch (AmountOfProductLessThanZeroException | GramsOfProductLessThanZeroException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isCorrectInputStr(String[] splitString) {
        return splitString.length <= 2;
    }

    private String[] splitInputStr(String inputString) {
        return inputString.split("-");
    }

    private void addLayOutProducts(String[] splitInputStr, double layOutPrice) throws AmountOfProductLessThanZeroException, GramsOfProductLessThanZeroException {
        int counter = 1;
        for (Map.Entry<Product, Integer> entry : basketOfProducts.entrySet()) {
            if (splitInputStr.length == 2) {
                if (splitInputStr[0].equals(Integer.toString(counter)) || splitInputStr[0].equals(entry.getKey().getName())) {
                    if (entry.getKey().isCountable()) {
                        if (Integer.parseInt(splitInputStr[1]) < 0 || Integer.parseInt(splitInputStr[1]) > entry.getValue()) {
                            throw new AmountOfProductLessThanZeroException();
                        }
                    } else {
                        if (Integer.parseInt(splitInputStr[1]) < 0 || Integer.parseInt(splitInputStr[1]) > entry.getValue()) {
                            throw new GramsOfProductLessThanZeroException();
                        }
                    }
                    if (Integer.parseInt(splitInputStr[1]) == entry.getValue() || Integer.parseInt(splitInputStr[1]) == 0) {
                        basketOfProducts.remove(entry.getKey());
                    } else {
                        basketOfProducts.put(entry.getKey(), Integer.parseInt(splitInputStr[1]));
                    }
                    layOutPrice += entry.getValue() * entry.getKey().getPriceForOne();
                    return;
                }
            } else {
                if (splitInputStr[0].equals(Integer.toString(counter)) || splitInputStr[0].equals(entry.getKey().getName())) {
                    basketOfProducts.remove(entry.getKey());
                    layOutPrice += entry.getValue() * entry.getKey().getPriceForOne();
                    return;
                }
            }
            counter++;
        }
    }

    private void showBasket() throws IncorrectInputTextException {
        StringBuilder stringBuilder = new StringBuilder();
        totalPrice = 0;
        int counter = 1;
        for (Map.Entry<Product, Integer> entry : basketOfProducts.entrySet()) {
            if (entry.getKey().isCountable()) {
                stringBuilder.append(String.format(Constants.COUNTABLE_PRODUCT_INF, counter, entry.getKey().getName(),
                        entry.getValue(), entry.getKey().getPriceForOne() * entry.getValue())
                );
            } else {
                stringBuilder.append(String.format(Constants.UNCOUNTABLE_PRODUCT_INF, counter, entry.getKey().getName(),
                        entry.getValue(), entry.getKey().getPriceForOne() * entry.getValue())
                );
            }
            totalPrice += entry.getKey().getPriceForOne() * entry.getValue();
            counter++;
        }
        if (totalPrice == 0) {
            System.out.println("You haven't got any products.\n");
        } else {
            stringBuilder.append(String.format("\nTotal price: %.2f$\nYou have: %.2f$\n", totalPrice, accountService.getCustomer().getMoney()));
            System.out.println(stringBuilder);
        }
    }

    private void buyBasket() throws NotEnoughMoneyException, IncorrectInputTextException {
        if (totalPrice > accountService.getCustomer().getMoney()) {
            throw new NotEnoughMoneyException("You are have less money, than total price!!!");
        }
        accountService.spendMoney(totalPrice);
        orderService.makeOrders(basketOfProducts);
//        ���� �� ���������� �������
        basketOfProducts.clear();
        System.out.printf("Operation was successful;)\nYou have: %.2f$\n", accountService.getCustomer().getMoney());
    }

    private void showLayOutWarning() {
        System.out.println("""
                \u001B[33mWarning!!!
                Example of how to enter product correctly:
                Name or number, or name or number-amount or grams.
                If you write name or number your product will de removed from basket,
                if you write name or number-amount or grams, amount or grams of product will be replaced with your values.
                For example: Potato or 3, or Potato-2 .
                If you write Lay_out, your product will be lay out from the basket.
                If you write Stop, choice of product will be stopped and you lose your lay out product!!!\u001B[0m""");
    }
}