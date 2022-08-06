package com.company;

public class Query {
    static String adminExistQuery = "select * from userdetails where role='Admin'";
    static String getRoleQuery = "select distinct role from userdetails;";
    static String insertLoginDetails="Insert into login (email,password) values (?,?);";



//    static String clearuserdetails="delete from userdetails";
//    static String clearRole = "delete from role";
//    static String clearLogin = "delete from login";
//    static String clearMedicine = "delete from medicine";
//    static String clearPatientMedication = "delete from patientmedication";
//    static String setAutoIncrementuserdetails = "alter table userdetails AUTO_INCREMENT=1;";
//    static String setAutoIncrementlogin = "alter table login AUTO_INCREMENT=1;";
//    static String setAutoIncrementmedicine = "alter table medicine AUTO_INCREMENT=1;";
}
