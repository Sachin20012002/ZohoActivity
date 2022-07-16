package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static void showDatabase() throws SQLException, ClassNotFoundException {
        Connection connection=Connect.ConnectDB();
        Statement statement=connection.createStatement();
        statement= connection.createStatement();
        String getUserDetails="select * from userdetails;";
        ResultSet resultSet=statement.executeQuery(getUserDetails);
        System.out.println("              USER DATABASE");
        System.out.println("---------------------------------------------");
        boolean isExist=false;
        while (resultSet.next()){
            isExist=true;
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String role=resultSet.getString("role");
            String userid=resultSet.getString("userid");
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            System.out.println("-----------------------------------------------------");
        }
        if(!isExist)
        {
            System.out.println("--------------------");
            System.out.println("DataBase is Empty");
            System.out.println("--------------------");
        }
    }

    public static boolean isExistEmail(String email,String role) throws SQLException {
        Connection connection=Connect.ConnectDB();
        Statement statement = connection.createStatement();
        String getUserDetailsQuery="select * from userdetails where email='"+email+"' and role='"+role+"';";
        ResultSet resultSet=statement.executeQuery(getUserDetailsQuery);
        return resultSet.next();
    }

    public static void printDetails(String role) throws SQLException, ClassNotFoundException {
        Connection connection=Connect.ConnectDB();
        Statement statement=connection.createStatement();
        String query="select * from userdetails where role='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
        boolean isExist=false;
        while (resultSet.next())
        {
            isExist=true;
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String userid=resultSet.getString("userid");
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            System.out.println("-----------------------------------------------------");
        }
        if(!isExist)
        {
            System.out.println("NO RECORDS FOUND");
        }
    }

    public static boolean isExistEmail(String email) throws SQLException {
        Connection connection=Connect.ConnectDB();
        Statement statement = connection.createStatement();
        String getUserDetailsQuery="select * from userdetails where email='"+email+"';";
        ResultSet resultSet=statement.executeQuery(getUserDetailsQuery);
        return resultSet.next();
    }
}
