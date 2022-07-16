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
        System.out.println("                    HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("               Welcome Admin\n--------------------------------------\nEnter Your name");
        scanner = new Scanner(System.in);
        String name = scanner.next();
        System.out.println("Enter your email");
        String email = scanner.next();
        System.out.println("Enter your password");
        String password = scanner.next();
        Admin admin = new Admin(name);
        new User(name, "Admin", email);
        new Login(email, password);
        new Role("Admin", 1);
        System.out.println("Enter the number of roles");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            admin.addRole();
        }
    }

    public static void initialOptions() throws SQLException, ClassNotFoundException {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("                    HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("1.\tLogin Page\n2.\tDatabase\n3.\tEND");
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> Login.showLoginDetails();
            case 2 -> DataBase.showDatabase();
            case 3 -> {
                connection.close();
                System.exit(0);
            }
        }
        initialOptions();
    }
}


