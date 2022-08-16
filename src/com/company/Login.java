package com.company;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;

public class Login {
    static Scanner scanner;

    static void addLogin(String email, String password) throws SQLException {
        JDBC db=JDBC.getInstance();
        PreparedStatement preparedStatement=db.getPreparedStatement(Query.insertLoginDetails);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        db.executeUpdate(preparedStatement);
    }

    static void showLoginDetails() throws SQLException {
        JDBC db=JDBC.getInstance();
        scanner=new Scanner(System.in);
        Design.loginPage();
        System.out.println();
        System.out.println("Login in as:\t");
        ResultSet resultSet = db.executeQuery(Query.getRoleQuery);
        Design.availableUsers();
        HashSet<String> roleSet=new HashSet<>();
        while (resultSet.next()) {
            String currRole=resultSet.getString("role");
            roleSet.add(currRole);
            System.out.println("     *\t" + currRole);
        }
        System.out.println();
        System.out.println("ENTER ROLE NAME");
        String role = scanner.nextLine();
        if(!roleSet.contains(role)){
            System.out.println("User not Available");
            showLoginDetails();
            return;
        }
        System.out.println("Enter your email");
        String email= scanner.nextLine();
        if (!LoginValidation(role,email)) {
            Design.invalidLogin();
            showLoginDetails();
            return;
        }
        if(role.equals("Patient")){
            new Patient(email);
        } else if (role.equals("Doctor")) {
            new Doctor();
        }
        else {
            new User(role);
        }
    }

    private static boolean LoginValidation(String role,String email) throws SQLException {
        JDBC db=JDBC.getInstance();
        scanner=new Scanner(System.in);
        System.out.println("Enter your password");
        String password= scanner.nextLine();
        ResultSet r=db.executeQuery(Query.getPassword(role,email));
        if(r.next())
        {
            return r.getString("password").equals(password);
        }
        return false;
    }
}
