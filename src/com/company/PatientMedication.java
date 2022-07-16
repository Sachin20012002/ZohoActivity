package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientMedication {
    public static void addPatientMedication(int medicineId, int patientId) throws SQLException {
        Connection connection=Connect.ConnectDB();
        Statement statement=connection.createStatement();
        String Insertquery="insert into patientmedication (patientID,medicineId) values ("+patientId+","+medicineId+");";
        statement.executeUpdate(Insertquery);
        System.out.println("  MEDICINE ADDED SUCCESSFULLY  ");
    }
}
