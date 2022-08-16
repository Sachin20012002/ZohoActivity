package com.company;

import java.sql.*;


//SINGLETON CLASS
public class JDBC {
    private static JDBC jdbc;
    private JDBC(){}
    public static JDBC getInstance(){
        if (jdbc==null)
        {
            jdbc=new  JDBC();
        }
        return jdbc;
    }

    String DataBaseURL="jdbc:mysql://localhost:3306/zohoproject";
    String Username="root";
    String Password="password";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    private Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DataBaseURL, Username, Password);
            statement=connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
            return connection;
    }
    ResultSet executeQuery(String query){
        try
        {
            connection=this.getConnection();
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
            connection=this.getConnection();
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
            connection=getConnection();
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
            connection=getConnection();
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
        connection=getConnection();
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


