package com.pluralsight;

import java.sql.*;


public class Main {
    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }
        String username = args[0];
        String password = args[1];

        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);


            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products;");

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                String productId = results.getString("ProductID");
                String productName = results.getString("ProductName");
                String unitPrice = results.getString("UnitPrice");
                String unitInStock = results.getString("UnitsInStock");
                System.out.println("Product Id: " + productId);
                System.out.println("Name:     " + productName );
                System.out.println("Price:     " + unitPrice );
                System.out.println("Stock:     " + unitInStock );
                System.out.println("---------------------------");
            }
            results.close();
            preparedStatement.close();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}