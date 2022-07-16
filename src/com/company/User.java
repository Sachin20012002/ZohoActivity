package com.company;

import java.sql.*;
import java.util.Scanner;

public class User {
    static Connection connection;
    static Statement statement;
    public User(String name,String role,String email) throws SQLException {
        connection=Connect.ConnectDB();
        statement= connection.createStatement();
        String insertUser="INSERT INTO userdetails (name,role,email) VALUES ('"+name+"','Admin','"+email+"');";
        statement.executeUpdate(insertUser);
        connection.close();
    }
    public User(String role) throws SQLException, ClassNotFoundException {
        connection=Connect.ConnectDB();
        statement=connection.createStatement();
        showOperations(role);
    }
    private void showOperations(String role) throws SQLException, ClassNotFoundException {
        Scanner scanner=new Scanner(System.in);
            System.out.println("1.\tADD USER\n2.\tVIEW DETAILS\n3.\tLOG OUT\n");
            System.out.println("Enter a number from the above choices");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addUser(role);
                case 2 -> ViewDetails(role);
                default -> {
                    System.out.println("*******<< LOGGED OUT >>*********");
                    return;
                }
            }
            showOperations(role);
        }
    private void addUser(String role) throws SQLException, ClassNotFoundException {
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt(1);
        query="select * from role";
        resultSet=statement.executeQuery(query);
        while(resultSet.next()){
            int priority=resultSet.getInt(2);
            if(loginPriority<priority)
            {
                System.out.println("*\t"+resultSet.getString(1));
            }
        }
        Scanner scanner =new Scanner(System.in);
        String roleName= scanner.next();
        System.out.println("Enter Name");
        scanner =new Scanner(System.in);
        String name= scanner.next();
        System.out.println("Enter email");
        String email= scanner.next();
        System.out.println("Enter password");
        String password= scanner.next();
        if(!DataBase.isExistEmail(email)) {

            statement = connection.createStatement();
            String insertQuery = "INSERT INTO userdetails (name,role,email) VALUES ('" + name + "','" + roleName + "','" + email + "');";
            statement.executeUpdate(insertQuery);
            insertQuery = "Insert into login (email,password) values ('" + email + "','" + password + "');";
            statement.executeUpdate(insertQuery);
            System.out.println("<<<< USER ADDED SUCCESSFULLY >>>>>");
        }
        else {
            System.out.println("User already exists");
        }
    }
    private void ViewDetails(String role) throws SQLException, ClassNotFoundException {
        int counter=1;
        Scanner scanner=new Scanner(System.in);
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt("priority");
        String getRoleDetailsQuery="select * from role;";
        resultSet=statement.executeQuery(getRoleDetailsQuery);
        while (resultSet.next())
        {   if(loginPriority<resultSet.getInt(2))
           {
            System.out.println(counter+".\t"+resultSet.getString(1));
            counter++;
           }
        }
        System.out.println("Enter the role name");
        String roleName= scanner.next();
        DataBase.printDetails(roleName);
    }
    public static int getUserId(String email) throws SQLException {
        Connection con=Connect.ConnectDB();
        Statement statement= con.createStatement();
        String getUserIdQuery="select userid from userdetails where email='"+email+"';";
        ResultSet resultSet=statement.executeQuery(getUserIdQuery);
        resultSet.next();
        return resultSet.getInt(1);
    }
}
