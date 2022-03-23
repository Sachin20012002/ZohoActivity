package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<String, ArrayList<User>> members;
    static HashMap<String, Integer> roles;
    static ArrayList<String> medicines;
    static Scanner sc;
    static int id=1;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String delete="DELETE FROM userdetails";
        st.executeUpdate(delete);
        System.out.println("Welcome Admin"+
                "\nEnter Your name");
        medicines=new ArrayList<>();
        sc=new Scanner(System.in);
        String name=sc.next();
        System.out.println("Enter your email");
        String email=sc.next();
        System.out.println("Enter your password");
        String password=sc.next();
        System.out.println("Enter the number of roles");
        int n=sc.nextInt();
        roles=new HashMap<>();
        roles.put("Admin",1);
        members=new HashMap<>();
        members.put("Admin",new ArrayList<>());
        User u=new User(name,email,password,"Admin");
        String str="INSERT INTO userdetails (userid,name,role,email) VALUES ('"+id+"','"+name+"','Admin','"+email+"');";
        st.executeUpdate(str);
        id++;
        //System.exit(0);
        members.get("Admin").add(u);
        for(int i=0;i<n;i++)
        {
            addrole();
        }
        initialOptions();

    }

    private static void initialOptions() throws SQLException, ClassNotFoundException {
        System.out.println("********************");
        System.out.println();
        System.out.println("1.\tLogin Page"+"\n2.\tDatabase");
        sc=new Scanner(System.in);
        int choice=sc.nextInt();
        switch (choice) {
            case 1 -> showLoginDetails();
            case 2 -> showDatabase();
        }
    }

    private static void showDatabase() throws SQLException, ClassNotFoundException {
        boolean check=false;
        for(String s: members.keySet())
        {

            if(members.get(s).size()!=0) {
                System.out.println("**************");
                System.out.println();
                System.out.println(s);
                System.out.println("-----------------");
                check=true;
                for (User u : members.get(s)) {
                    u.showDetails();
                }
            }
        }
        if(!check)
        {
            System.out.println("DataBase is Empty");
        }
        initialOptions();
    }

    private static void showLoginDetails() throws SQLException, ClassNotFoundException {
        System.out.println("LOGIN PAGE");
        System.out.println("Login in as:\t");
        int c=1;
        for(String x:roles.keySet())
        {
            System.out.println(c+".\t"+x);
            c++;
        }
        System.out.println("ENTER ROLE NAME");
        String s=sc.next();
        if(!roles.containsKey(s))
        {
            System.out.println("Enter Correct role");
            showLoginDetails();
        }
        if(s.equals("doctor"))
        {
            System.out.println("0.\tADD MEDICATION");
        }
        System.out.println("1.\tADD USER"+"\n2.\tVIEW DETAILS"+"\n3.\tGO BACK");
        int choice=sc.nextInt();
        switch(choice)
        {case 0:
            addMedication();
            break;
            case 1:
                addUser(s);
                break;
            case 2:
                ViewDetails(s);
                break;
            default:
                initialOptions();
        }
    }

    private static void addMedication() throws SQLException, ClassNotFoundException {
        System.out.println("Enter Patient email");
        String email=sc.next();
        if(isExist(email,"patient"))
        {
            System.out.println("Enter Medication");
            String s = sc.next();
            if(!isExistmedicine(s)) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
                Statement st = con.createStatement();
                String str = "Insert into medicine (Name) values ('" + s + "');";
                st.executeUpdate(str);
            }
            medicines.add(email+ "-" + s);
            System.out.println("Medicine added successfully");
        }
        else{
            System.out.println("Invalid details");
        }
        initialOptions();
    }

    private static boolean isExistmedicine(String s) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement st = con.createStatement();
        String str="select * from medicine where Name=\""+s+"\";";
        ResultSet r=st.executeQuery(str);
        if(r.next()) {
            return true;
        }
        else {
            return false;
        }

    }

    private static boolean isExist(String email, String role) {

        for(User u:members.get("patient"))
        {
            if(u.verify(email,role))
            {
                return true;
            }
        }
        return false;
    }

    private static void ViewDetails(String s) throws SQLException, ClassNotFoundException {
        int counter=1;
        boolean ispatient=true;
        for(String x:roles.keySet())
        {   if(roles.get(s)<roles.get(x))
        {   ispatient=false;
            System.out.println(counter+".\t"+x);
            counter++;
        }
        }
        if(ispatient)
        {
            System.out.println("1.\t View Medication");
            int a=sc.nextInt();
            if(a==1)
            {
                viewMedication();
            }
        }
        String role=sc.next();
        if(members.containsKey(role))
        {
            printDetails(role);
        }
        else {
            System.out.println("Enter proper role name");
            ViewDetails(s);
        }
    }

    private static void viewMedication() throws SQLException, ClassNotFoundException {
        int count=1;
        for(String s:medicines)
        {
            System.out.println(count+".\t"+s);
            count++;
        }
        initialOptions();
    }

    private static void printDetails(String role) throws SQLException, ClassNotFoundException {
        if(members.get(role).size()==0)
        {
            System.out.println("NO RECORDS FOUND");
            initialOptions();
        }
       for(User u:members.get(role))
       {
           u.showDetails();
       }
       initialOptions();
    }

    private static void addUser(String s) throws SQLException, ClassNotFoundException {
        int counter=1;
        for(String x:roles.keySet()){
            if(roles.get(s)<roles.get(x))
            {
                System.out.println(counter+".\t"+x);
                counter++;
            }
        }
        System.out.println(counter+".\tLogin Page");
        sc=new Scanner(System.in);
        String role=sc.next();
        if(!roles.containsKey(role))
        {
            System.out.println("Invalid Access");
            addUser(s);
        }
        Boolean valid=false;
            if(roles.get(role)>roles.get(s))
            {
                valid=true;
                getUserDetails(role);
            }
        }

    private static void getUserDetails(String role) throws SQLException, ClassNotFoundException {
        System.out.println("Enter Name");
        sc=new Scanner(System.in);
        String name=sc.next();
        System.out.println("Enter email");
        String email=sc.next();
        System.out.println("Enter password");
        String password=sc.next();
        User u=new User(name,email,password,role);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String str="INSERT INTO userdetails (userid,role,name,email) VALUES ('"+id+"','"+name+"','"+role+"','"+email+"');";
        st.executeUpdate(str);
        id++;
        members.get(role).add(u);
        initialOptions();
    }


    private static void addrole() {
        sc=new Scanner(System.in);
        System.out.println("Enter the role with their priority");
        String rolename=sc.next();
        int pr=sc.nextInt();
        members.put(rolename,new ArrayList<>());
        roles.put(rolename,pr);
    }
}
