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
    Patient() throws SQLException, ClassNotFoundException {
        connection=Connect.ConnectDB();
        statement=connection.createStatement();
        showOperations();
        connection.close();
    }
    public void showOperations() throws SQLException, ClassNotFoundException {
        System.out.println("1.\tView Medication\n2.\tLOG OUT\n");
        System.out.println("Enter a number from the above choices");
        Scanner scanner=new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> viewMedication();
            default -> {
                System.out.println("*******<< LOGGED OUT >>*********");
                Main.initialOptions();
            }
        }
    }

    private void viewMedication() throws SQLException, ClassNotFoundException {
        System.out.println("Enter Your userId");
        int userid= scanner.nextInt();
        String query="select * from (select * from patientmedication inner join medicine on patientmedication.medicineId=medicine.id) as T where patientID="+userid;
        ResultSet resultSet=statement.executeQuery(query);
        System.out.println("<<<<< MEDICATIONS >>>>>>>");
        while (resultSet.next())
        {
            System.out.println("*\t"+resultSet.getString("name"));
        }
        System.out.println("-------------------------------");
        showOperations();
    }
}
