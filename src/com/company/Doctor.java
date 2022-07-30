package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Doctor{
    static Connection connection;
    static Statement statement;
    static Scanner scanner;
    Doctor() throws SQLException, ClassNotFoundException {
        connection=Connect.ConnectDB();
        statement=connection.createStatement();
        showOperations();
        connection.close();
    }
    private void showOperations() throws SQLException, ClassNotFoundException {
        System.out.println("0.\tADD MEDICATION\n1.\tADD USER\n2.\tVIEW DETAILS\n3.\tLOG OUT\n");
        System.out.println("Enter a number from the above choices");
        Scanner scanner=new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 0 -> addMedication();
            case 1 -> addUser();
            case 2 -> ViewDetails();
            default -> {
                Display.loggedOut();
                return;
            }
        }
        showOperations();
    }
    private void addMedication() throws SQLException, ClassNotFoundException {
        Connection connection=Connect.ConnectDB();
        Statement statement=connection.createStatement();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter Patient email");
        String email= scanner.next();
        if(DataBase.isExistEmail(email,"Patient"))
        {
            System.out.println("Enter Medication");
            String medName= scanner.next();
            if(!Medicine.isExistMedicine(medName)) {
                new Medicine(medName);
            }
            int medicineId=Medicine.getMedicineId(medName);
            int patientId=User.getUserId(email);
           PatientMedication.addPatientMedication(medicineId,patientId);
        }
        else{
            Display.noRecordsFound();
        }
    }
    private void addUser() throws SQLException {
        String query="select priority from role where roleName='Doctor';";
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
        if(!DataBase.isExistEmail(email,roleName)) {

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

    private void ViewDetails() throws SQLException, ClassNotFoundException {
        int counter=1;
        Scanner scanner=new Scanner(System.in);
        Connection connection=Connect.ConnectDB();
        Statement statement;
        statement=connection.createStatement();
        String query="select priority from role where roleName='Doctor';";
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

}
