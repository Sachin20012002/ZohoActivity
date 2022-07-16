package com.company;

import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    String name;
    public Admin(String name) {
        this.name=name;
    }
    public static void addRole() throws SQLException {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the role");
        String roleName= scanner.next();
        System.out.println("Enter their priority");
        int priority= scanner.nextInt();
        new Role(roleName,priority);
    }
}
