package com.bogdan.shop.dao;

import com.bogdan.shop.model.Customer;
import com.bogdan.shop.model.Order;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO extends AbstractDAO {
    public void insertOrders(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            for (Order order : orders) {
                preparedStatement = connection.prepareStatement("INSERT INTO orders (serial_number, customer_email, time_stamp, product_id, quantity) " +
                        "VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, order.getSerialNumber());
                preparedStatement.setString(2, order.getCustomerEmail());
                preparedStatement.setTimestamp(3, order.getTimeStamp());
                preparedStatement.setInt(4, order.getProductId());
                preparedStatement.setInt(5, order.getQuantity());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
    }

    public List<Order> getByEmail(String email) {
        List<Order> orders = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM orders WHERE customer_email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(
                        new Order(
                                resultSet.getInt("serial_number"),
                                resultSet.getString("customer_email"),
                                resultSet.getTimestamp("time_stamp"),
                                resultSet.getInt("product_id"),
                                resultSet.getInt("quantity")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
        return orders;
    }

    public int getMaxSerialNumber() {
        int maxSerialNumber = 0;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(serial_number) " +
                    "FROM orders");
            resultSet.next();
            maxSerialNumber = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(statement);
            super.close(connection);
        }
        return maxSerialNumber;
    }
}
