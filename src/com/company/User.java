package com.company;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;

public class User {
    static Connection connection;
    static Statement statement;
    public User(String name,String role,String email) throws SQLException {
        connection=Connect.ConnectDB();
        PreparedStatement statement= connection.prepareStatement("Insert into userdetails (name,role,email) values (?,?,?)");
        statement.setString(1,name);
        statement.setString(2,role);
        statement.setString(3,email);
        statement.executeUpdate();
        connection.close();
    }
    public User(String role) throws SQLException, ClassNotFoundException {
        connection=Connect.ConnectDB();
        statement=connection.createStatement();
        showOperations(role);
        connection.close();
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
                    Display.loggedOut();
                    return;
                }
            }
            showOperations(role);
        }
    private void addUser(String role) throws SQLException {
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt(1);
        query="select * from role";
        resultSet=statement.executeQuery(query);
        Display.availableRoles();
        HashSet<String> roleSet=new HashSet<>();
        while(resultSet.next()){
            int priority=resultSet.getInt(2);
            if(loginPriority<priority)
            {   String currRole=resultSet.getString(1);
                roleSet.add(currRole);
                System.out.println("*\t"+currRole);
            }
        }
        Scanner scanner =new Scanner(System.in);
        System.out.println("ENTER THE ROLE NAME");
        String roleName= scanner.next();
        if(!roleSet.contains(roleName)){
            Display.notAvailable();
            addUser(role);
        }
        System.out.println("Enter Name");
        scanner =new Scanner(System.in);
        String name= scanner.next();
        System.out.println("Enter email");
        String email= scanner.next();
        System.out.println("Enter password");
        String password= scanner.next();
        if(!DataBase.isExistUser(email,role)) {

            statement = connection.createStatement();
            String insertQuery = "INSERT INTO userdetails (name,role,email) VALUES ('" + name + "','" + roleName + "','" + email + "');";
            statement.executeUpdate(insertQuery);
            insertQuery = "Insert into login (email,password) values ('" + email + "','" + password + "');";
            statement.executeUpdate(insertQuery);
            Display.userAdded();
        }
        else {
            Display.userExists();
        }
    }
    private void ViewDetails(String role) throws SQLException{
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
