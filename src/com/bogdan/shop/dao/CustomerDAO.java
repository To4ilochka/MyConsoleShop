package com.bogdan.shop.dao;

import com.bogdan.shop.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends AbstractDAO {


    public boolean insert(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!isExistingEmail(customer.getEmail(), connection)) {
                preparedStatement = connection.prepareStatement("INSERT INTO customers (email, name, surname, money) " +
                        "VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, customer.getEmail());
                preparedStatement.setString(2, customer.getName());
                preparedStatement.setString(3, customer.getSurname());
                preparedStatement.setDouble(4, customer.getMoney());
                preparedStatement.execute("SELECT * FROM customers");
                preparedStatement.executeUpdate();
            } else {
                super.close(preparedStatement);
                super.close(connection);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
        return true;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("email"),
                                resultSet.getString("name"),
                                resultSet.getString("surname"),
                                resultSet.getDouble("money")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(statement);
            super.close(connection);
        }
        return customers;
    }

    public Customer getByEmail(String email) {
        Customer customer = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM customers WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customer = new Customer(
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getDouble("money")
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
        return customer;
    }

    public void updateMoney(double money, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE customers " +
                    "SET money = ? " +
                    "WHERE email = ?");
            preparedStatement.setDouble(1, money);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
    }

    public Customer getByNameSurname(String name, String surname) {
        Customer customer = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM customers WHERE name = ? AND surname = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customer = new Customer(
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getDouble("money")
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            super.close(preparedStatement);
            super.close(connection);
        }
        return customer;
    }

    private boolean isExistingEmail(String email, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT email FROM customers WHERE email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
