package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class DataManager {
    private final BasicDataSource dataSource;

    public DataManager(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }

    public void insertIntoWithGeneratedKeys(){
        String companyName = Console.PromptForString("Enter Company Name: ");
        String phone = Console.PromptForString("Enter Company Phone: ");
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?);",
                        PreparedStatement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, companyName);
            preparedStatement.setString(2, phone);

            int row = preparedStatement.executeUpdate();
            System.out.printf("Rows updated %d\n", row);

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                while(resultSet.next()){
                    System.out.printf("%d key was added\n", resultSet.getLong(1));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void displayAllShippers(){
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM northwind.Shippers;");
                ResultSet resultSet = preparedStatement.executeQuery();
        ){
            System.out.println("All Shippers: ");
            while(resultSet.next()){
                int shipperId = resultSet.getInt("ShipperId");
                String companyName = resultSet.getString("CompanyName");
                String phone = resultSet.getString("Phone");

                System.out.println(shipperId + ": " + companyName + " " + phone);
                System.out.println("-------------------------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateShipperInfo(){
        int shipperId = Console.PromptForInt("Enter Shipper ID: ");
        String shipperName = Console.PromptForString("Enter Shipper Name: ");
        String phone = Console.PromptForString("Enter Shipper phone: ");

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Shippers SET CompanyName = ?,  Phone = ? WHERE ShipperID = ?");
        ){
            preparedStatement.setString(1, shipperName);
            preparedStatement.setString(2, phone);
            preparedStatement.setInt(3, shipperId);

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rowsUpdated);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
