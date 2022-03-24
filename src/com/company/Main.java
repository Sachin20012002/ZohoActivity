package com.company;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String adminExistQuery="select * from userdetails where role=\"Admin\"";
        ResultSet r=st.executeQuery(adminExistQuery);
        if(!r.next())
        {   String clearUserDetails="delete from userdetails";
            st.executeUpdate(clearUserDetails);
            String clearRole="delete from role";
            st.executeUpdate(clearRole);
            String clearLogin="delete from login";
            st.executeUpdate(clearLogin);
            String clearMedicine="delete from medicine";
            st.executeUpdate(clearMedicine);
            String setAutoIncrement="alter table userdetails AUTO_INCREMENT=1;";
            st.executeUpdate(setAutoIncrement);
            setAutoIncrement="alter table login AUTO_INCREMENT=1;";
            st.executeUpdate(setAutoIncrement);
            setAutoIncrement="alter table medicine AUTO_INCREMENT=1;";
            st.executeUpdate(setAutoIncrement);
            greetAdmin();
        }
        initialOptions();
    }

    private static void greetAdmin() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        System.out.println("                    HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("               Welcome Admin\n--------------------------------------\nEnter Your name");
        sc=new Scanner(System.in);
        String name=sc.next();
        System.out.println("Enter your email");
        String email=sc.next();
        System.out.println("Enter your password");
        String password=sc.next();
        System.out.println("Enter the number of roles");
        int n=sc.nextInt();
        String insertUser="INSERT INTO userdetails (name,role,email) VALUES ('"+name+"','Admin','"+email+"');";
        st.executeUpdate(insertUser);
        String insertLoginDetails="Insert into login (email,password) values ('"+email+"','"+password+"');";
        st.executeUpdate(insertLoginDetails);
        String insertRole="insert into role (roleName,priority) values ('Admin',1);";
        st.executeUpdate(insertRole);
        for(int i=0;i<n;i++)
        {
            addrole();
        }
    }

    private static void initialOptions() throws SQLException, ClassNotFoundException {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("                    HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("1.\tLogin Page\n2.\tDatabase\n3.\tEND");
        sc=new Scanner(System.in);
        int choice=sc.nextInt();
        switch (choice) {
            case 1 -> showLoginDetails();
            case 2 -> showDatabase();
            case 3-> System.exit(0);
        }
    }

    private static void showDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String getUserDetails="select * from userdetails;";
        ResultSet resultSet=st.executeQuery(getUserDetails);
        System.out.println("              USER DATABASE");
        System.out.println("---------------------------------------------");
        boolean isExist=false;
        while (resultSet.next()){
            isExist=true;
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String role=resultSet.getString("role");
            String userid=resultSet.getString("userid");
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            System.out.println("-----------------------------------------------------");
        }
        if(!isExist)
        {
            System.out.println("--------------------");
            System.out.println("DataBase is Empty");
            System.out.println("--------------------");
        }

        initialOptions();
    }

    private static void showLoginDetails() throws SQLException, ClassNotFoundException {
        System.out.println("                LOGIN PAGE");
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println("Login in as:\t");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement st = con.createStatement();
        String getRoleQuery = "select roleName from role;";
        ResultSet resultSet = st.executeQuery(getRoleQuery);
        System.out.println("    AVAILABLE USERS  ");
        System.out.println("------------------------");
        while (resultSet.next()) {
            System.out.println("     *\t" + resultSet.getString("roleName"));
        }
        System.out.println();
        System.out.println("ENTER ROLE NAME");
        String role = sc.next();
        if (!LoginValidation()) {
            System.out.println("\n******<< Invalid login Credentials >>*******\n");
            showLoginDetails();
        }
        showOperations(role);
    }

    private static void showOperations(String role) throws SQLException, ClassNotFoundException {
        if(role.equals("doctor"))
        {
            System.out.println("0.\tADD MEDICATION");
        }
        System.out.println("1.\tADD USER\n2.\tVIEW DETAILS\n3.\tLOG OUT\n");
        System.out.println("Enter a number from the above choices");
            int choice = sc.nextInt();
            switch (choice) {
                case 0 -> addMedication();
                case 1 -> addUser(role);
                case 2 -> ViewDetails(role);
                default -> {
                    System.out.println("*******<< LOGGED OUT >>*********");
                    initialOptions();
                }
            }
    }

    private static boolean LoginValidation() throws ClassNotFoundException, SQLException {
        System.out.println("Enter your email");
        String email=sc.next();
        System.out.println("Enter your password");
        String password=sc.next();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String str="select password from login where email='"+email+"';";
        ResultSet r=st.executeQuery(str);
        if(r.next())
        {
            return r.getString("password").equals(password);
        }
        return false;
    }

    private static void addMedication() throws SQLException, ClassNotFoundException {
        System.out.println("Enter Patient email");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement st = con.createStatement();
        String email=sc.next();
        if(isExist(email))
        {
            System.out.println("Enter Medication");
            String medName= sc.next();
            if(!isExistMedicine(medName)) {

                String str = "Insert into medicine (Name) values ('" + medName + "');";
                st.executeUpdate(str);
            }
            String medIdquery="select id from medicine where name='"+medName+"';";
            ResultSet resultSet=st.executeQuery(medIdquery);
            resultSet.next();
            int medicineid=resultSet.getInt(1);
            String getUserIdQuery="select userid from userdetails where email='"+email+"';";
            resultSet=st.executeQuery(getUserIdQuery);
            resultSet.next();
            int patientid=resultSet.getInt(1);
            String Insertquery="insert into patientmedication (patientID,medicineId) values ("+patientid+","+medicineid+");";
            st.executeUpdate(Insertquery);
            System.out.println("  MEDICINE ADDED SUCCESSFULLY  ");
        }
        else{
            System.out.println("****<< NO PATIENT FOUND >>****");
        }
        initialOptions();
    }

    private static boolean isExistMedicine(String s) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement st = con.createStatement();
        String checkMedicineQuery="select * from medicine where Name='"+s+"';";
        ResultSet resultSet=st.executeQuery(checkMedicineQuery);
        return resultSet.next();

    }

    private static boolean isExist(String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement st = con.createStatement();
        String getUserDetailsQuery="select * from userdetails where email='"+email+"';";
        ResultSet resultSet=st.executeQuery(getUserDetailsQuery);
        return resultSet.next();
    }

    private static void ViewDetails(String role) throws SQLException, ClassNotFoundException {
        int counter=1;
        boolean ispatient=true;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement st = con.createStatement();
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=st.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt("priority");
        String getRoleDetailsQuery="select * from role;";
        resultSet=st.executeQuery(getRoleDetailsQuery);
        while (resultSet.next())
        {   if(loginPriority<resultSet.getInt(2))
        {   ispatient=false;

            System.out.println(counter+".\t"+resultSet.getString(1));
            counter++;
        }
        }
        if(ispatient)
        {
            System.out.println("1.\t View Medication");
            int choice=sc.nextInt();
            if(choice==1)
            {
                viewMedication();
            }
        }
        System.out.println("Enter the role name");
        String roleName=sc.next();
            printDetails(roleName);
    }

    private static void viewMedication() throws SQLException, ClassNotFoundException {
        System.out.println("Enter Your userId");
        int userid=sc.nextInt();
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String query="select * from (select * from patientmedication inner join medicine on patientmedication.medicineId=medicine.id) as T where patientID="+userid;
        ResultSet resultSet=st.executeQuery(query);
        while (resultSet.next())
        {
            System.out.println(resultSet.getString("name"));
        }
        initialOptions();
    }

    private static void printDetails(String role) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String query="select * from userdetails where role='"+role+"';";
        ResultSet resultSet=st.executeQuery(query);
        boolean isExist=false;
        while (resultSet.next())
        {
            isExist=true;
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String userid=resultSet.getString("userid");
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            System.out.println("-----------------------------------------------------");
        }
        if(!isExist)
        {
            System.out.println("NO RECORDS FOUND");
        }
       initialOptions();
    }

    private static void addUser(String role) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=st.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt(1);
        query="select * from role";
        resultSet=st.executeQuery(query);
        boolean isPatient=true;
        while(resultSet.next()){
            int priority=resultSet.getInt(2);
            if(loginPriority<priority)
            {   isPatient=false;
                System.out.println("*\t"+resultSet.getString(1));
            }
        }
        if(isPatient)
        {
            System.out.println("You are not allowed to Add users");
            initialOptions();
        }
        sc=new Scanner(System.in);
        String rolename=sc.next();
        getUserDetails(rolename);
        }

    private static void getUserDetails(String role) throws SQLException, ClassNotFoundException {
        System.out.println("Enter Name");
        sc=new Scanner(System.in);
        String name=sc.next();
        System.out.println("Enter email");
        String email=sc.next();
        System.out.println("Enter password");
        String password=sc.next();
        if(!isExist(email)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
            Statement st = con.createStatement();
            String insertQuery = "INSERT INTO userdetails (name,role,email) VALUES ('" + name + "','" + role + "','" + email + "');";
            st.executeUpdate(insertQuery);
            insertQuery = "Insert into login (email,password) values ('" + email + "','" + password + "');";
            st.executeUpdate(insertQuery);
        }
        else {
            System.out.println("User already exists");
        }
        initialOptions();
    }


    private static void addrole() throws ClassNotFoundException, SQLException {
        sc=new Scanner(System.in);
        System.out.println("Enter the role");
        String rolename=sc.next();
        System.out.println("Enter their priority");
        int priority=sc.nextInt();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        Statement st=con.createStatement();
        String insertQuery="insert into role (roleName,priority) values ('"+rolename+"',"+priority+");";
        st.executeUpdate(insertQuery);

    }
}
