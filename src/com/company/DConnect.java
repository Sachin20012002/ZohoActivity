package com.company;

import java.sql.*;

public class DConnect {
    String DataBaseURL="jdbc:mysql://localhost:3306/zohoProject";
    String Username="root";
    String Password="password";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    private void openConnection(){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DataBaseURL, Username, Password);
            statement=connection.createStatement();
        }catch(SQLException e){
            System.out.println("Cannot connect to dataBase");
        }
    }
    ResultSet executeQuery(String query)
    {
        try
        {
            openConnection();
            resultSet=statement.executeQuery(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return resultSet;
    }
    ResultSet executeQuery(PreparedStatement preparedStatement) {
        try {
            openConnection();
            resultSet = preparedStatement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    void executeUpdate(String query){
        try
        {
            openConnection();
            statement.executeUpdate(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        closeConnection();
    }
    void executeUpdate(PreparedStatement preparedStatement) {
        try {
            openConnection();
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }
    PreparedStatement getPreparedStatement(String query) throws SQLException {
        openConnection();
        return connection.prepareStatement(query);
    }

    void closeConnection()
    {
        try
        {
            statement.close();
            connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }



}


