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

    Patient(String email) throws SQLException, ClassNotFoundException {
        connection=Connect.ConnectDB();
        statement=connection.createStatement();
        showOperations(email);
        connection.close();
    }
    public void showOperations(String email) throws SQLException, ClassNotFoundException {
        System.out.println("1.\tView Medication\n2.\tLOG OUT\n");
        System.out.println("Enter a number from the above choices");
        scanner=new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> viewMedication(email);
            default -> {
                Display.loggedOut();
                return;
            }
        }
        showOperations(email);
    }

    private void viewMedication(String email) throws SQLException, ClassNotFoundException {
        String query="select medicine.name from (patientmedication inner join medicine on patientmedication.medicineId=medicine.id inner join userdetails on patientmedication.patientId=userdetails.userid ) where email='"+email+"';";
        ResultSet resultSet=statement.executeQuery(query);
        Display.medications();
        while (resultSet.next())
        {
            System.out.println("*\t"+resultSet.getString("name"));
        }
        Display.LineBreak();
    }
}
