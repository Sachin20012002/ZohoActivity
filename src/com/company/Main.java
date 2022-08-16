package com.company;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner;

    public static void main(String[] args) throws SQLException{
        DConnect db=new DConnect();
        ResultSet r = db.executeQuery(Query.adminExistQuery);
        if (!r.next()) {
            greetAdmin();
        }
        mainPage();
    }

    private static void greetAdmin() throws SQLException {
        Design.hospitalManagementSystem();
        Design.welcomeAdmin();
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
        System.out.println("""
                By default Admin will have a priority value of 1,
                and an user can create other users who's priority value is greater than his priority value
                """);
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

    public static void mainPage() throws SQLException {
        Design.hospitalManagementSystem();
        System.out.println("1.\tLogin Page\n2.\tDatabase\n3.\tEND");
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> Login.showLoginDetails();
            case 2 -> DataBase.showOperations();
            case 3 -> {
                System.out.println("Thank You :)");
                return;
            }
        }
        mainPage();
    }
}