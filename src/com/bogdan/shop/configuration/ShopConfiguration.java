package com.bogdan.shop.configuration;

import com.bogdan.shop.dao.CustomerDAO;
import com.bogdan.shop.dao.OrderDAO;
import com.bogdan.shop.dao.ProductDAO;
import com.bogdan.shop.model.Product;
import com.bogdan.shop.service.AccountService;
import com.bogdan.shop.service.BasketService;
import com.bogdan.shop.service.OrderService;
import com.bogdan.shop.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class ShopConfiguration {
    private Scanner scanner;
    private ProductService productService;
    private BasketService basketService;
    private AccountService accountService;
    private OrderService orderService;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public List<Product> getProductList() {
        return getProductDAO().getAll();
    }

    public Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    public ProductService getProductService() {
        if (productService == null) {
            productService = new ProductService(getProductList(), getBasketService(), getScanner());
        }
        return productService;
    }

    public BasketService getBasketService() {
        if (basketService == null) {
            basketService = new BasketService(getAccountService(), getOrderService(), getScanner());
        }
        return basketService;
    }

    public AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(getScanner(), getCustomerDAO());
        }
        return accountService;
    }
    public OrderService getOrderService() {
        if (orderService == null) {
            orderService = new OrderService(getOrderDAO(), getAccountService(), getProductDAO());
        }
        return orderService;
    }
    private CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = new CustomerDAO();
        }
        return customerDAO;
    }
    private ProductDAO getProductDAO() {
        if (productDAO == null) {
            productDAO = new ProductDAO();
        }
        return productDAO;
    }
    private OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new OrderDAO();
        }
        return orderDAO;
    }
}
