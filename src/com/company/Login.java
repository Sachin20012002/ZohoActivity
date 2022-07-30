package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;


public class Login {

    Login(String email, String password) throws SQLException {
        Connection con=Connect.ConnectDB();
        Statement statement;
        statement= con.createStatement();
        String insertLoginDetails="Insert into login (email,password) values ('"+email+"','"+password+"');";
        statement.executeUpdate(insertLoginDetails);
    }

    static void showLoginDetails() throws SQLException, ClassNotFoundException {
        Connection connection=Connect.ConnectDB();
        Statement statement;
        Scanner scanner=new Scanner(System.in);
        Display.loginPage();
        System.out.println();
        System.out.println("Login in as:\t");
        statement = connection.createStatement();
        String getRoleQuery = "select distinct role from userdetails;";
        ResultSet resultSet = statement.executeQuery(getRoleQuery);
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
