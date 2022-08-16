package com.company;

import java.sql.*;
import java.util.Scanner;

public class Doctor extends User{

    Doctor() throws SQLException {
        super();
        showOperations();
    }
    private void showOperations() throws SQLException {
        System.out.println("0.\tADD MEDICATION\n1.\tADD USER\n2.\tVIEW DETAILS\n3.\tLOG OUT\n");
        System.out.println("Enter a number from the above choices");
        scanner=new Scanner(System.in);
        int choice;
        try {
            choice = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("Please Enter Valid Number");
            showOperations();
            return;
        }
        switch (choice) {
            case 0 -> addMedication();
            case 1 -> addUser("Doctor");
            case 2 -> ViewDetails("Doctor");
            default -> {
                Design.loggedOut();
                return;
            }
        }
        showOperations();
    }
    private void addMedication() throws SQLException {
        scanner=new Scanner(System.in);
        System.out.println("Enter Patient email");
        String email= scanner.nextLine();
        if(DataBase.isExistUser(email,"Patient"))
        {
            System.out.println("Enter Medication");
            String medName= scanner.nextLine();
            if(!Medicine.isExistMedicine(medName)) {
                Medicine.addMedicine(medName);
            }
            int medicineId=Medicine.getMedicineId(medName);
            int patientId=User.getUserId(email);
           PatientMedication.addPatientMedication(medicineId,patientId);
        }
        else{
            Design.noRecordsFound();
        }
    }
}
