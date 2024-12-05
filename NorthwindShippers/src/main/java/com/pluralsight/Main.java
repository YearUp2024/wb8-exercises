package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please select from the options: ");
        System.out.println("[1] - Add a new Shipper");
        System.out.println("[2] - Display all Shippers");
        System.out.println("[3] - Update Shipper");
        System.out.println("[4] - Delete Shipper");
        int userOption = Console.PromptForInt("Enter your choice: ");

        try(BasicDataSource basicDataSource = dataSource(args)){
            DataManager dataManager = new DataManager(basicDataSource);

            if(userOption == 1){
                dataManager.insertIntoWithGeneratedKeys();
            }else if(userOption == 2){
                dataManager.displayAllShippers();
            }else if(userOption == 3){
                dataManager.updateShipperInfo();
            }else if(userOption == 4){
                dataManager.deleateShipper();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static BasicDataSource dataSource(String[] args){
        String sqlServerURL = "jdbc:mysql://localhost:3306/northwind";
        String userName = args[0];
        String password = args[1];

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(sqlServerURL);
        basicDataSource.setUsername(userName);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}