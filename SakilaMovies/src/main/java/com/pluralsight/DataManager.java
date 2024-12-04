package com.pluralsight;

import com.pluralsight.Models.Actor;
import com.pluralsight.Models.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final BasicDataSource dataSource;

    public DataManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> findActorsByLastName(String lastName){
        ArrayList<Actor> actors = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT actor_id, first_name, last_name FROM sakila.actor Where last_name = ?;")){
                preparedStatement.setString(1, lastName);

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        actors.add(new Actor(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        ));
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return actors;
    }

    public List<Film> findMoviesByActorId(int actorId){
        ArrayList<Film> films = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT film.film_id, title, description, release_year, length FROM film\n" +
                        "JOIN film_actor ON film.film_id = film_actor.film_id\n" +
                        "WHERE actor_id = ?")
            ){
                preparedStatement.setInt(1, actorId);

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        films.add( new Film(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        ));
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }
}
