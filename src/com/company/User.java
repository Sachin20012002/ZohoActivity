package com.company;

public class User {
    String name;
    String email;
    String password;
    String role;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    boolean verify(String email,String role)
    {
        return (email.equals(this.email) && role.equals(this.role));
    }
    public void showDetails() {
        System.out.println("Name:\t"+name+"\nEmail:\t"+email+"\nPassword:\t"+password);
    }
}
