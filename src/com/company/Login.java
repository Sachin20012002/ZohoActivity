package com.company;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;

public class Login {
    PreparedStatement statement;

    Login(String email, String password) throws SQLException {
        Connection connection=Connect.ConnectDB();
        statement= connection.prepareStatement(Query.insertLoginDetails);
        statement.setString(1,email);
        statement.setString(2,password);
        statement.executeUpdate();
    }

    static void showLoginDetails() throws SQLException, ClassNotFoundException {
        Connection connection=Connect.ConnectDB();
        Statement statement;
        Scanner scanner=new Scanner(System.in);
        Display.loginPage();
        System.out.println();
        System.out.println("Login in as:\t");
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Query.getRoleQuery);
        Display.availableUsers();
        HashSet<String> roleSet=new HashSet<>();
        while (resultSet.next()) {
            String currRole=resultSet.getString("role");
            roleSet.add(currRole);
            System.out.println("     *\t" + currRole);
        }
        System.out.println();
        System.out.println("ENTER ROLE NAME");
        String role = scanner.next();
        if(!roleSet.contains(role)){
            System.out.println("User not Available");
            showLoginDetails();
        }
        System.out.println("Enter your email");
        String email= scanner.next();
        if (!LoginValidation(role,email)) {
            Display.invalidLogin();
            showLoginDetails();
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
        Connection connection=Connect.ConnectDB();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your password");
        String password= scanner.next();
        Statement statement= connection.createStatement();
        String str="select password from login inner join userdetails on login.email=userdetails.email where login.email='"+email+"' and role='"+role+"';";
        ResultSet r=statement.executeQuery(str);
        if(r.next())
        {
            return r.getString("password").equals(password);
        }
        return false;
    }
}
