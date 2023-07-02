package com.bogdan.shop.dao;

import com.bogdan.shop.model.Order;
import com.bogdan.shop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductDAO extends AbstractDAO {

    public void insert(Product product) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO products (name, price, countable) " +
                    "VALUES (?, ?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPriceForOne());
            preparedStatement.setBoolean(3, product.isCountable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                products.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("price"),
                                resultSet.getBoolean("countable")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(statement);
            super.close(connection);
        }
        return products;
    }

    public Product getById(int id) {
        Product product = null;
        Connection connection = null;
        try {
            product = getById(id, connection = DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(connection);
        }
        return product;
    }

    public List<Product> getById(List<Order> orders) {
        List<Product> products = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            for (Order order : orders) {
                products.add(getById(order.getProductId(), connection));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(connection);
        }
        return products;
    }

    private Product getById(int id, Connection connection) throws SQLException {
        Product product;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        product = new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getBoolean("countable")
        );
        super.close(preparedStatement);
        return product;
    }

    public Product getByName(String name) {
        Product product = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getBoolean("countable")
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
        return product;
    }
}
