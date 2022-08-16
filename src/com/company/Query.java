package com.company;

public class Query {


    static String adminExistQuery = "select * from userdetails where role='Admin'";
    static String getRoleQuery = "select distinct role from userdetails;";
    static String getDetailsOfRole="select * from userdetails where role=?;";
    static String getUserDetailsQuery="select * from userdetails where email=? and role=?;";
    static String selectAllFromRole="select * from role";
    static String selectAllFromUserDetails="select * from userdetails";
    static String insertLoginDetails="Insert into login (email,password) values (?,?);";
    static String insertIntoUserDetails = "INSERT INTO userdetails (name,role,email) VALUES (?,?,?);";
    static String insertIntoMedicine = "Insert into medicine (Name) values (?);";
    static String insertPatientMedication="insert into patientmedication (patientID,medicineId) values (?,?);";
    static String insertRole="insert into role (roleName,priority) values (?,?);";

    static String clearuserdetails="delete from userdetails";
    static String clearRole = "delete from role";
    static String clearLogin = "delete from login";
    static String clearMedicine = "delete from medicine";
    static String clearPatientMedication = "delete from patientmedication";
    static String setAutoIncrementuserdetails = "alter table userdetails AUTO_INCREMENT=1;";
    static String setAutoIncrementlogin = "alter table login AUTO_INCREMENT=1;";
    static String setAutoIncrementmedicine = "alter table medicine AUTO_INCREMENT=1;";


    public static String getPassword(String role, String email) {
        return "select password from login inner join userdetails on login.email=userdetails.email where login.email='"+email+"' and role='"+role+"';";
    }

    public static String getPriority(String role) {
        return "select priority from role where roleName='"+role+"';";
    }

    public static String getMedID(String medName) {
        return "select id from medicine where name='"+medName+"';";
    }

    public static String checkMedicineQuery(String medName) {
        return "select * from medicine where Name='"+medName+"';";
    }

    public static String getMedicineName(String email) {
        return "select medicine.name from (patientmedication inner join medicine on patientmedication.medicineId=medicine.id inner join userdetails on patientmedication.patientId=userdetails.userid ) where email='"+email+"';";
    }

    public static String getUserId(String email) {
        return "select userid from userdetails where email='"+email+"';";
    }



}
