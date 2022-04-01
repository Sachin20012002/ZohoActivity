package com.company;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static Connection connection;
    

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject","root","password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Statement statement= connection.createStatement();
        String adminExistQuery="select * from userdetails where role=\"Admin\"";
        ResultSet r=statement.executeQuery(adminExistQuery);
        if(!r.next())
        {
            String clearRole="delete from role";
            statement.executeUpdate(clearRole);
            String clearLogin="delete from login";
            statement.executeUpdate(clearLogin);
            String clearMedicine="delete from medicine";
            statement.executeUpdate(clearMedicine);
            String clearPatientMedication="delete from patientmedication";
            statement.executeUpdate(clearPatientMedication);
            String setAutoIncrement="alter table userdetails AUTO_INCREMENT=1;";
            statement.executeUpdate(setAutoIncrement);
            setAutoIncrement="alter table login AUTO_INCREMENT=1;";
            statement.executeUpdate(setAutoIncrement);
            setAutoIncrement="alter table medicine AUTO_INCREMENT=1;";
            statement.executeUpdate(setAutoIncrement);
            greetAdmin();
        }
        initialOptions();
        connection.close();
    }

    private static void greetAdmin() throws SQLException {
        Statement statement= connection.createStatement();
        System.out.println("                    HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.println("               Welcome Admin\n--------------------------------------\nEnter Your name");
        scanner =new Scanner(System.in);
        String name= scanner.next();
        System.out.println("Enter your email");
        String email= scanner.next();
        System.out.println("Enter your password");
        String password= scanner.next();
        System.out.println("Enter the number of roles");
        int n= scanner.nextInt();
        String insertUser="INSERT INTO userdetails (name,role,email) VALUES ('"+name+"','Admin','"+email+"');";
        statement.executeUpdate(insertUser);
        String insertLoginDetails="Insert into login (email,password) values ('"+email+"','"+password+"');";
        statement.executeUpdate(insertLoginDetails);
        String insertRole="insert into role (roleName,priority) values ('Admin',1);";
        statement.executeUpdate(insertRole);
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
        scanner =new Scanner(System.in);
        int choice= scanner.nextInt();
        switch (choice) {
            case 1 -> showLoginDetails();
            case 2 -> showDatabase();
            case 3-> {
                connection.close();
                System.exit(0);
            }
        }
    }

    private static void showDatabase() throws SQLException, ClassNotFoundException {
        Statement statement= connection.createStatement();
        String getUserDetails="select * from userdetails;";
        ResultSet resultSet=statement.executeQuery(getUserDetails);
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
        Statement statement = connection.createStatement();
        String getRoleQuery = "select roleName from role;";
        ResultSet resultSet = statement.executeQuery(getRoleQuery);
        System.out.println("    AVAILABLE USERS  ");
        System.out.println("------------------------");
        while (resultSet.next()) {
            System.out.println("     *\t" + resultSet.getString("roleName"));
        }
        System.out.println();
        System.out.println("ENTER ROLE NAME");
        String role = scanner.next();
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
            int choice = scanner.nextInt();
            switch (choice) {
                case 0 -> addMedication(role);
                case 1 -> addUser(role);
                case 2 -> ViewDetails(role);
                default -> {
                    System.out.println("*******<< LOGGED OUT >>*********");
                    initialOptions();
                }
            }
    }

    private static boolean LoginValidation() throws SQLException {
        System.out.println("Enter your email");
        String email= scanner.next();
        System.out.println("Enter your password");
        String password= scanner.next();
        Statement st= connection.createStatement();
        String str="select password from login where email='"+email+"';";
        ResultSet r=st.executeQuery(str);
        if(r.next())
        {
            return r.getString("password").equals(password);
        }
        return false;
    }

    private static void addMedication(String role) throws SQLException, ClassNotFoundException {
        System.out.println("Enter Patient email");
        Statement statement = connection.createStatement();
        String email= scanner.next();
        if(isExist(email))
        {
            System.out.println("Enter Medication");
            String medName= scanner.next();
            if(!isExistMedicine(medName)) {

                String query = "Insert into medicine (Name) values ('" + medName + "');";
                statement.executeUpdate(query);
            }
            String medIdquery="select id from medicine where name='"+medName+"';";
            ResultSet resultSet=statement.executeQuery(medIdquery);
            resultSet.next();
            int medicineid=resultSet.getInt(1);
            String getUserIdQuery="select userid from userdetails where email='"+email+"';";
            resultSet=statement.executeQuery(getUserIdQuery);
            resultSet.next();
            int patientid=resultSet.getInt(1);
            String Insertquery="insert into patientmedication (patientID,medicineId) values ("+patientid+","+medicineid+");";
            statement.executeUpdate(Insertquery);
            System.out.println("  MEDICINE ADDED SUCCESSFULLY  ");
        }
        else{
            System.out.println("****<< NO PATIENT FOUND >>****");
        }
        showOperations(role);
    }

    private static boolean isExistMedicine(String s) throws SQLException {
        Statement statement = connection.createStatement();
        String checkMedicineQuery="select * from medicine where Name='"+s+"';";
        ResultSet resultSet=statement.executeQuery(checkMedicineQuery);
        return resultSet.next();

    }

    private static boolean isExist(String email) throws SQLException {
        Statement statement = connection.createStatement();
        String getUserDetailsQuery="select * from userdetails where email='"+email+"' and role='patient';";
        ResultSet resultSet=statement.executeQuery(getUserDetailsQuery);
        return resultSet.next();
    }

    private static void ViewDetails(String role) throws SQLException, ClassNotFoundException {
        int counter=1;
        boolean ispatient=true;
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
        Statement statement = connection.createStatement();
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt("priority");
        String getRoleDetailsQuery="select * from role;";
        resultSet=statement.executeQuery(getRoleDetailsQuery);
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
            int choice= scanner.nextInt();
            if(choice==1)
            {
                viewMedication(role);
            }
        }
        System.out.println("Enter the role name");
        String roleName= scanner.next();
            printDetails(roleName,role);
    }

    private static void viewMedication(String role) throws SQLException, ClassNotFoundException {
        System.out.println("Enter Your userId");
        int userid= scanner.nextInt();
        Statement statement=connection.createStatement();
        String query="select * from (select * from patientmedication inner join medicine on patientmedication.medicineId=medicine.id) as T where patientID="+userid;
        ResultSet resultSet=statement.executeQuery(query);
        System.out.println("<<<<< MEDICATIONS >>>>>>>");
        while (resultSet.next())
        {
            System.out.println("*\t"+resultSet.getString("name"));
        }
        System.out.println("-------------------------------");
        showOperations(role);
    }

    private static void printDetails(String role,String loginRole) throws SQLException, ClassNotFoundException {
        Statement statement=connection.createStatement();
        String query="select * from userdetails where role='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
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
       showOperations(loginRole);
    }

    private static void addUser(String role) throws SQLException, ClassNotFoundException {
        Statement statement=connection.createStatement();
        String query="select priority from role where roleName='"+role+"';";
        ResultSet resultSet=statement.executeQuery(query);
        resultSet.next();
        int loginPriority=resultSet.getInt(1);
        query="select * from role";
        resultSet=statement.executeQuery(query);
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
            showOperations(role);
        }
        scanner =new Scanner(System.in);
        String rolename= scanner.next();
        getUserDetails(rolename,role);
        }

    private static void getUserDetails(String role,String loginRole) throws SQLException, ClassNotFoundException {
        System.out.println("Enter Name");
        scanner =new Scanner(System.in);
        String name= scanner.next();
        System.out.println("Enter email");
        String email= scanner.next();
        System.out.println("Enter password");
        String password= scanner.next();
        if(!isExist(email)) {
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO userdetails (name,role,email) VALUES ('" + name + "','" + role + "','" + email + "');";
            statement.executeUpdate(insertQuery);
            insertQuery = "Insert into login (email,password) values ('" + email + "','" + password + "');";
            statement.executeUpdate(insertQuery);
            System.out.println("<<<< USER ADDED SUCCESSFULLY >>>>>");
        }
        else {
            System.out.println("User already exists");
        }
        showOperations(loginRole);
    }


    private static void addrole() throws SQLException {
        scanner =new Scanner(System.in);
        System.out.println("Enter the role");
        String rolename= scanner.next();
        System.out.println("Enter their priority");
        int priority= scanner.nextInt();
        Statement statement=connection.createStatement();
        String insertQuery="insert into role (roleName,priority) values ('"+rolename+"',"+priority+");";
        statement.executeUpdate(insertQuery);

    }
}
