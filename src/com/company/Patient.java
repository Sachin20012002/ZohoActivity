package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Patient {
    static Connection connection;
    static Statement statement;
    static Scanner scanner;

    Patient(String email) throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        statement=connection.createStatement();
        showOperations(email);
        connection.close();
    }
    public void showOperations(String email) throws SQLException{
        System.out.println("1.\tView Medication\n2.\tLOG OUT\n");
        System.out.println("Enter a number from the above choices");
        scanner=new Scanner(System.in);
        int choice;
        try {
            choice = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("Please Enter Valid Number");
            showOperations(email);
            return;
        }
        if (choice == 1) {
            viewMedication(email);
        } else {
            Design.loggedOut();
            return;
        }
        showOperations(email);
    }

    private void viewMedication(String email) throws SQLException {
        ResultSet resultSet=statement.executeQuery(Query.getMedicineName(email));
        Design.medications();
        while (resultSet.next())
        {
            System.out.println("*\t"+resultSet.getString("name"));
        }
        Design.LineBreak();
    }
}
