package com.company;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;

public class User {

    static Scanner scanner;
    public User(String name,String role,String email) throws SQLException {
        DConnect db=new DConnect();
        PreparedStatement preparedStatement= db.getPreparedStatement(Query.insertIntoUserDetails);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,role);
        preparedStatement.setString(3,email);
        db.executeUpdate(preparedStatement);
    }
    public User(String role) throws SQLException{
        showOperations(role);
    }

    public User() {
    }


    private void showOperations(String role) throws SQLException {
        scanner=new Scanner(System.in);
            System.out.println("1.\tADD USER\n2.\tVIEW DETAILS\n3.\tLOG OUT\n");
            System.out.println("Enter a number from the above choices");
            int choice;
            try {
                choice = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("Please Enter Valid Number");
                showOperations(role);
                return;
            }
            switch (choice) {
                case 1 -> addUser(role);
                case 2 -> ViewDetails(role);
                default -> {
                    Design.loggedOut();
                    return;
                }
            }
            showOperations(role);
        }

        void addUser(String role) throws SQLException {
        DConnect db=new DConnect();
        ResultSet resultSet=db.executeQuery(Query.getPriority(role));
        resultSet.next();
        int loginPriority=resultSet.getInt(1);
        resultSet=db.executeQuery(Query.selectAllFromRole);
        Design.availableRoles();
        HashSet<String> roleSet=new HashSet<>();
        while(resultSet.next()){
            int priority=resultSet.getInt(2);
            if(loginPriority<priority)
            {   String currRole=resultSet.getString(1);
                roleSet.add(currRole);
                System.out.println("*\t"+currRole);
            }
        }
        scanner =new Scanner(System.in);
        System.out.println("ENTER THE ROLE NAME");
        String roleName= scanner.nextLine();
        if(!roleSet.contains(roleName)){
            Design.notAvailable();
            addUser(role);
            return;
        }
        System.out.println("Enter Name");
        scanner =new Scanner(System.in);
        String name= scanner.nextLine();
        System.out.println("Enter email");
        String email= scanner.nextLine();
        System.out.println("Enter password");
        String password= scanner.nextLine();
        if(!DataBase.isExistUser(email,roleName)) {

            PreparedStatement preparedStatement = db.getPreparedStatement(Query.insertIntoUserDetails);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,roleName);
            preparedStatement.setString(3,email);
            db.executeUpdate(preparedStatement);
            preparedStatement=db.getPreparedStatement(Query.insertLoginDetails);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            db.executeUpdate(preparedStatement);
            Design.userAdded();
        }
        else {
            Design.userExists();
        }
    }

     void ViewDetails(String role) throws SQLException{
        DConnect db=new DConnect();
        int counter=1;
        Scanner scanner=new Scanner(System.in);
        ResultSet resultSet=db.executeQuery(Query.getPriority(role));
        resultSet.next();
        int loginPriority=resultSet.getInt("priority");
        resultSet=db.executeQuery(Query.selectAllFromRole);
        while (resultSet.next())
        {   if(loginPriority<resultSet.getInt(2))
           {
            System.out.println(counter+".\t"+resultSet.getString(1));
            counter++;
           }
        }
        System.out.println("Enter the role name");
        String roleName= scanner.nextLine();
        DataBase.printDetails(roleName);
    }
    public static int getUserId(String email) throws SQLException {
        DConnect db=new DConnect();
        ResultSet resultSet=db.executeQuery(Query.getUserId(email));
        resultSet.next();
        return resultSet.getInt(1);
    }
}
