package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("Application needs two arguments to run: Username and Password!");
            System.exit(1);
        }

        String userName = args[0];
        String password = args[1];

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        basicDataSource.setUsername(userName);
        basicDataSource.setPassword(password);

        int userInput = 0;
        do{
            System.out.println("What do you want to do?");
            System.out.println("[1] - Find Actor's by Last Name");
            System.out.println("[2] - Find list of Movies for a Actor");
            System.out.println("[99] - Exit");
            System.out.print("Select from the options: ");
            userInput = scanner.nextInt();
            scanner.nextLine();

            if(userInput == 1){
                findActorsByLastName(basicDataSource);
            }else if(userInput == 2){
                findMoviesByActorName(basicDataSource);
            }
        }while(userInput != 99);
    }

    private static void findActorsByLastName(BasicDataSource basicDataSource) {
        System.out.print("Enter a Actor's last name: ");
        String actorLastName = scanner.nextLine();

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT first_name, last_name FROM sakila.actor Where last_name = ?;");
        ){
            preparedStatement.setString(1, actorLastName);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    System.out.println("\n-------------------------------");
                    System.out.println(firstName + " " + lastName);
                    System.out.println("-------------------------------");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void findMoviesByActorName(BasicDataSource basicDataSource) {
        System.out.print("Enter Actor First Name: ");
        String actorFirstName = scanner.nextLine();
        System.out.print("Enter Actor Last Name: ");
        String actorLatName = scanner.nextLine();

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT film.title \n" +
                        "FROM sakila.film\n" +
                        "JOIN film_actor ON film_actor.film_id = film.film_id\n" +
                        "JOIN actor ON actor.actor_id = film_actor.actor_id\n" +
                        "WHERE actor.first_name = ? AND actor.last_name = ?;");
        ){
            preparedStatement.setString(1, actorFirstName);
            preparedStatement.setString(2, actorLatName);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    System.out.println("\n-------------------------------------");
                    do{
                        String movieTitle = resultSet.getString("title");
                        System.out.println(movieTitle);
                    }while(resultSet.next());
                    System.out.println("\n-------------------------------------");
                }else{
                    System.out.println("No matches!");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}