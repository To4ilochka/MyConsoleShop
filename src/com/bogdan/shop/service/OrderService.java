package com.bogdan.shop.service;

import com.bogdan.shop.dao.OrderDAO;
import com.bogdan.shop.dao.ProductDAO;
import com.bogdan.shop.model.Order;
import com.bogdan.shop.model.Product;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderDAO orderDAO;
    private final AccountService accountService;
    private final ProductDAO productDAO;

    public OrderService(OrderDAO orderDAO, AccountService accountService, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.accountService = accountService;
        this.productDAO = productDAO;
    }

    public void makeOrders(Map<Product, Integer> basketOfProducts) {
        List<Order> orders = new LinkedList<>();
        int serialNumber = getIncrementSerialNumber();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        for (Map.Entry<Product, Integer> entry : basketOfProducts.entrySet()) {
            orders.add(
                    new Order(
                            serialNumber,
                            accountService.getCustomer().getEmail(),
                            currentTime,
                            entry.getKey().getId(),
                            entry.getValue()
                    )
            );
        }
        orderDAO.insertOrders(orders);
    }

    public void showHistory() {
        List<Order> accountOrders = orderDAO.getByEmail(accountService.getCustomer().getEmail());
        if (accountOrders.isEmpty()) {
            System.out.println("You haven't got any orders.");
            return;
        }
        List<Product> purchasedProducts = productDAO.getById(accountOrders);
        drawHistory(accountOrders, purchasedProducts);
    }

    private void drawHistory(List<Order> accountOrders, List<Product> purchasedProducts) {
        System.out.println("TimeStamp | Product | Quantity | Price");
        int counter = 0;
        for (Order order : accountOrders) {
            Product purchasedProduct = purchasedProducts.get(counter);
            System.out.printf("%s | %s | %o | %.2f$ \n", order.getTimeStamp().toString(), purchasedProduct, order.getQuantity(),
                    order.getQuantity() * purchasedProduct.getPriceForOne()
            );
            counter++;
        }
    }

    private int getIncrementSerialNumber() {
        return orderDAO.getMaxSerialNumber() + 1;
    }
}