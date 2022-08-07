package com.company;

import java.sql.*;
import java.util.Scanner;

public class DataBase {
    static Connection connection;
    static Scanner scanner;
    static PreparedStatement preparedStatement;
    static String DataBaseURL="jdbc:mysql://localhost:3306/zohoProject";
    static String Username="root";
    static String Password="password";

    public static void showOperations() throws SQLException {
        System.out.println("1.\tShow DataBase\n2.\tClear DataBase\n3.\tGo Back\n");
        System.out.println("Enter a number from the above choices");
        scanner=new Scanner(System.in);
        int choice;
        try {
            choice = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("Please Enter Valid Number");
            showOperations();
            return;
        }
        switch (choice) {
            case 1 -> showDatabase();
            case 2 -> clearAllTables();
            default -> {
                Design.loggedOut();
                return;
            }
        }
        showOperations();
    }

    static void showDatabase() throws SQLException {
        connection=Connect.ConnectDB();
        String getUserDetails="select * from userdetails;";
        assert connection != null;
        preparedStatement= connection.prepareStatement(getUserDetails);
        ResultSet resultSet=preparedStatement.executeQuery();
        Design.databaseHeading();
        boolean isExist=false;
        while (resultSet.next()){
            isExist=true;
            System.out.println();
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String role=resultSet.getString("role");
            String userid=resultSet.getString("userid");
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            Design.LineBreak();
        }
        if(!isExist)
        {
            Design.databaseEmpty();
        }
        connection.close();
    }

    static void printDetails(String role) throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        preparedStatement=connection.prepareStatement(Query.getDetailsOfRole);
        preparedStatement.setString(1,role);
        ResultSet resultSet=preparedStatement.executeQuery();
        boolean isExist=false;
        while (resultSet.next())
        {
            isExist=true;
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String userid=resultSet.getString("userid");
            Design.LineBreak();
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            Design.LineBreak();
        }
        if(!isExist)
        {
            Design.noRecordsFound();
        }
        connection.close();
    }

    static boolean isExistUser(String email,String role) throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        preparedStatement= connection.prepareStatement(Query.getUserDetailsQuery);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,role);
        ResultSet resultSet=preparedStatement.executeQuery();
        boolean flag=resultSet.next();
        connection.close();
        return flag;
    }

  private static void clearAllTables() throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        Statement statement = connection.createStatement();
        statement.executeUpdate(Query.clearuserdetails);
        statement.executeUpdate(Query.clearRole);
        statement.executeUpdate(Query.clearLogin);
        statement.executeUpdate(Query.clearMedicine);
        statement.executeUpdate(Query.clearPatientMedication);
        statement.executeUpdate(Query.setAutoIncrementuserdetails);
        statement.executeUpdate(Query.setAutoIncrementlogin);
        statement.executeUpdate(Query.setAutoIncrementmedicine);
    }
}
