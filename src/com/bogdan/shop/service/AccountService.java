package com.bogdan.shop.service;

import com.bogdan.shop.dao.CustomerDAO;
import com.bogdan.shop.exception.IncorrectInputTextException;
import com.bogdan.shop.model.Customer;
import com.bogdan.shop.util.Constants;

import java.util.Scanner;

public class AccountService {
    private Customer customer;
    private final Scanner scanner;
    private final CustomerDAO customerDAO;

    public AccountService(Scanner scanner, CustomerDAO customerDAO) {
        this.scanner = scanner;
        this.customerDAO = customerDAO;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void accountChoice() {
        if (customer == null) {
            unknownAccountChoice();
        } else {
            knownAccountChoice();
        }
    }

    public void spendMoney(double totalPrice) {
        customer.setMoney(customer.getMoney() - totalPrice);
        customerDAO.updateMoney(customer.getMoney(), customer.getEmail());
    }

    private Customer creatingCustomer() {
        System.out.println("\u001B[33mYou can write 1 and creating of account will be stopped\u001B[0m");
        String inputName = askName();
        if (inputName == null) {
            return null;
        }
        String inputSurname = askSurname();
        if (inputSurname == null) {
            return null;
        }
        String inputEmail = askEmail();
        if (inputEmail == null) {
            return null;
        }
        return new Customer(inputEmail, inputName, inputSurname);
    }

    private void signUpAccount(Customer newCustomer) {
        if (customerDAO.insert(newCustomer)) {
            setCustomer(newCustomer);
            System.out.println("You have successfully created your account");
        } else {
            System.out.println("\u001B[33mA user with this email does exist\u001B[0m");
        }
    }

    private void logInAccount() {
        String inputEmail = askEmail();
        Customer logInCustomer = customerDAO.getByEmail(inputEmail);
        if (logInCustomer == null) {
            System.out.println("\u001B[33mA user with this email does not exist\u001B[0m");
        } else {
            setCustomer(logInCustomer);
            System.out.println("You have successfully signed up");
        }
    }

    private void exitFromAccount() {
        setCustomer(null);
    }

    private void unknownAccountChoice() {
        System.out.println("""
                1. Log in
                2. Sign up
                3. Back
                """);
        String inputString = scanner.next();
        switch (inputString) {
            case Constants.ONE:
                logInAccount();
                break;
            case Constants.TWO:
                Customer newCustomer = creatingCustomer();
                if (newCustomer == null) {
                    System.out.println("Creating of account stopped");
                    return;
                }
                signUpAccount(newCustomer);
                break;
            case Constants.THREE:
                return;
            default:
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }
    }

    private void knownAccountChoice() {
        System.out.printf("""
                1. Log in
                2. Sign up
                3. Exit from account "%s"
                4. Add money "You have: %.2f$"
                5. Back
                """, customer.getEmail(), customer.getMoney());
        String inputString = scanner.next();
        switch (inputString) {
            case Constants.ONE:
                logInAccount();
                break;
            case Constants.TWO:
                Customer newCustomer = creatingCustomer();
                if (newCustomer == null) {
                    System.out.println("Creating of account stopped");
                    return;
                }
                signUpAccount(newCustomer);
                break;
            case Constants.THREE:
                exitFromAccount();
                break;
            case Constants.FOUR:
                addMoney(askMoney());
                break;
            case Constants.FIVE:
                return;
            default:
                throw new IncorrectInputTextException(Constants.DON_T_BE_DUMB);
        }
    }

    private void addMoney(double money) {
        if (money == -1) {
            System.out.println("Adding money to account stopped");
            return;
        }
        customer.setMoney(customer.getMoney() + money);
        customerDAO.updateMoney(customer.getMoney(), customer.getEmail());
    }

    private double askMoney() {
        System.out.println("How much money do you need?");
        System.out.println("If you write Stop, the action will be stopped");
        String input;
        while (true) {
            input = scanner.next();
            if (Constants.STOP.equals(input)) {
                return -1;
            }
            if (input.matches("^[+]?[0-9]+(?:\\.[0-9]{1,2})?$")) {
                return Double.parseDouble(input);
            }
            System.out.println("\u001B[33mInvalid input\u001B[0m");
        }
    }

    private String askName() {
        System.out.println("Please enter a your name");
        String inputName;
        while (true) {
            inputName = scanner.next();
            if (Constants.ONE.equals(inputName)) {
                return null;
            }
            if (inputName.matches("^[A-Za-zР-пр-џ]+$")) {
                return inputName;
            }
            System.out.println("\u001B[33mThe name is incorrect\u001B[0m");
        }
    }

    private String askSurname() {
        System.out.println("Please enter a your surname");
        String inputSurname;
        while (true) {
            inputSurname = scanner.next();
            if (Constants.ONE.equals(inputSurname)) {
                return null;
            }
            if (inputSurname.matches("^[A-Za-zР-пр-џ]+$")) {
                return inputSurname;
            }
            System.out.println("\u001B[33mThe surname is incorrect\u001B[0m");
        }
    }

    private String askEmail() {
        System.out.println("Please enter an email");
        String inputEmail;
        while (true) {
            inputEmail = scanner.next();
            if (Constants.ONE.equals(inputEmail)) {
                return null;
            }
            if (isNormalEmail(inputEmail)) {
                return inputEmail;
            }
            System.out.println("\u001B[33mThe email is incorrect\u001B[0m");
        }
    }

    private boolean isNormalEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}