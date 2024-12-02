package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        if(args.length != 2){
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }
        String username = args[0];
        String password = args[1];

        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        do{
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            userInput = scanner.nextInt();

            if(userInput == 1){
                allProduct(username, password);
            }else if(userInput == 2){
                allCustomer(username, password);
            }
        }while(userInput != 0);
    }

    public static void allProduct(String username, String password) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            preparedStatement = connection.prepareStatement("SELECT * FROM products;");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String productId = resultSet.getString("ProductID");
                String productName = resultSet.getString("ProductName");
                String unitPrice = resultSet.getString("UnitPrice");
                String unitInStock = resultSet.getString("UnitsInStock");
                System.out.println("Product Id: " + productId);
                System.out.println("Name:     " + productName );
                System.out.println("Price:     " + unitPrice );
                System.out.println("Stock:     " + unitInStock );
                System.out.println("---------------------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void allCustomer(String username, String password){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            preparedStatement = connection.prepareStatement("SELECT * FROM Customers;");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String contactName = resultSet.getString("ContactName");
                String companyName = resultSet.getString("CompanyName");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");
                String phone = resultSet.getString("Phone");
                System.out.println("Contact Name:     " + contactName);
                System.out.println("Company Name:     " + companyName);
                System.out.println("City:             " + city);
                System.out.println("Country:          " + country);
                System.out.println("Phone:            " + phone);
                System.out.println("---------------------------");
            }
            resultSet.close();
            preparedStatement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}