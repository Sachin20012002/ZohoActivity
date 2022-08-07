package com.company;

import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    public Admin(String name,String email,String password) throws SQLException {
        new User(name, "Admin", email);
        Login.addLogin(email, password);
        new Role("Admin", 1);
    }
     void addRole() throws SQLException {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the role");
        String roleName= scanner.nextLine();
        System.out.println("Enter their priority");
        int priority= scanner.nextInt();
        new Role(roleName,priority);
    }
}
