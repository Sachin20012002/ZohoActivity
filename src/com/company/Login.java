package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Login {
    public static void showLoginDetails() throws SQLException, ClassNotFoundException {
        Connection connection=Connect.ConnectDB();
        Statement statement;
        Scanner scanner=new Scanner(System.in);
        System.out.println("                LOGIN PAGE");
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println("Login in as:\t");
        statement = connection.createStatement();
        String getRoleQuery = "select roleName from role;";
        ResultSet resultSet = statement.executeQuery(getRoleQuery);
        System.out.println("    AVAILABLE USERS  ");
        System.out.println("------------------------");
        while (resultSet.next()) {
            System.out.println("     *\t" + resultSet.getString("roleName"));
        }
        System.out.println();
        System.out.println("ENTER ROLE NAME");
        String role = scanner.next();
        if (!Login.LoginValidation()) {
            System.out.println("\n******<< Invalid login Credentials >>*******\n");
            showLoginDetails();
        }
        if(role.equals("Patient")){
            new Patient();
        } else if (role.equals("Doctor")) {
            new Doctor();
        }
        else {
            new User(role);
        }
    }
    public Login(String email, String password) throws SQLException {
        Connection con=Connect.ConnectDB();
        Statement statement;
        statement= con.createStatement();
        String insertLoginDetails="Insert into login (email,password) values ('"+email+"','"+password+"');";
        statement.executeUpdate(insertLoginDetails);
    }
    public static boolean LoginValidation() throws SQLException {
        Connection connection=Connect.ConnectDB();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your email");
        String email= scanner.next();
        System.out.println("Enter your password");
        String password= scanner.next();
        Statement statement= connection.createStatement();
        String str="select password from login where email='"+email+"';";
        ResultSet r=statement.executeQuery(str);
        if(r.next())
        {
            return r.getString("password").equals(password);
        }
        return false;
    }
}
