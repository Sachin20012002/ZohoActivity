package com.company;

import java.sql.*;
import java.util.Scanner;

public class DataBase {
    static Scanner scanner;

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
            case 2 -> {
                clearAllTables();
                System.out.println("The Database is cleared\n Thank You");
                System.exit(1);
            }
            default -> {
                Design.loggedOut();
                return;
            }
        }
        showOperations();
    }

    static void showDatabase() throws SQLException {
        JDBC db=JDBC.getInstance();
        ResultSet resultSet=db.executeQuery(Query.selectAllFromUserDetails);
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
        if(!isExist) {
            Design.databaseEmpty();
        }
    }

    static void printDetails(String role) throws SQLException {
        JDBC db=JDBC.getInstance();
        PreparedStatement preparedStatement= db.getPreparedStatement(Query.getDetailsOfRole);
        preparedStatement.setString(1,role);
        ResultSet resultSet= db.executeQuery(preparedStatement);
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
    }

    static boolean isExistUser(String email,String role) throws SQLException {
        JDBC db=JDBC.getInstance();
        PreparedStatement preparedStatement= db.getPreparedStatement(Query.getUserDetailsQuery);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,role);
        ResultSet resultSet= db.executeQuery(preparedStatement);
        return resultSet.next();
    }

  private static void clearAllTables() {
        JDBC db=JDBC.getInstance();
        db.executeUpdate(Query.clearuserdetails);
        db.executeUpdate(Query.clearRole);
        db.executeUpdate(Query.clearLogin);
        db.executeUpdate(Query.clearMedicine);
        db.executeUpdate(Query.clearPatientMedication);
        db.executeUpdate(Query.setAutoIncrementuserdetails);
        db.executeUpdate(Query.setAutoIncrementlogin);
        db.executeUpdate(Query.setAutoIncrementmedicine);
    }
}
