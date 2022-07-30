package com.company;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static Connection connection;
    static Statement statement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connection=Connect.ConnectDB();
        statement = connection.createStatement();
        String adminExistQuery = "select * from userdetails where role=\"Admin\"";
        ResultSet r = statement.executeQuery(adminExistQuery);
        if (!r.next()) {
            String clearRole = "delete from role";
            statement.executeUpdate(clearRole);
            String clearLogin = "delete from login";
            statement.executeUpdate(clearLogin);
            String clearMedicine = "delete from medicine";
            statement.executeUpdate(clearMedicine);
            String clearPatientMedication = "delete from patientmedication";
            statement.executeUpdate(clearPatientMedication);
            String setAutoIncrement = "alter table userdetails AUTO_INCREMENT=1;";
            statement.executeUpdate(setAutoIncrement);
            setAutoIncrement = "alter table login AUTO_INCREMENT=1;";
            statement.executeUpdate(setAutoIncrement);
            setAutoIncrement = "alter table medicine AUTO_INCREMENT=1;";
            statement.executeUpdate(setAutoIncrement);
            greetAdmin();
        }
        initialOptions();
        connection.close();
    }

    private static void greetAdmin() throws SQLException {
        Display.hospitalManagementSystem();
        Display.welcomeAdmin();
        scanner = new Scanner(System.in);
        System.out.println("Let us create your userLogin");
        System.out.println("Enter your Name");
        String name = scanner.next();
        System.out.println("Enter your email");
        String email = scanner.next();
        System.out.println("Enter your password");
        String password = scanner.next();
        Admin admin = new Admin(name,email,password);
        System.out.println("How many roles we can have in our hospital, Apart from Doctor and Patient");
        int n = scanner.nextInt();
        System.out.println("By default Admin will have a priority value of 1,\n" +
                "and an user can create other users who's priority value is greater than his priority value\n");
        System.out.println("Patient role will have the last priority value\n");
        System.out.println("Please enter Doctor's priority value");
        int p= scanner.nextInt();
        System.out.println("Thank You\n");
        System.out.println("Now let us create the other roles");
        for (int i = 0; i < n; i++) {
            admin.addRole();
        }
        new Role("Doctor",p);
        new Role("Patient",Integer.MAX_VALUE);

    }

    public static void initialOptions() throws SQLException, ClassNotFoundException {
        Display.hospitalManagementSystem();
        System.out.println("1.\tLogin Page\n2.\tDatabase\n3.\tEND");
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> Login.showLoginDetails();
            case 2 -> DataBase.showDatabase();
            case 3 -> {
                System.out.println("Thank You :)");
                return;
            }
        }
        initialOptions();
    }
}


