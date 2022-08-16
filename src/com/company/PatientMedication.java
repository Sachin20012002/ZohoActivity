package com.company;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PatientMedication {
    public static void addPatientMedication(int medicineId, int patientId) throws SQLException {
        DConnect db=new DConnect();
        PreparedStatement preparedStatement=db.getPreparedStatement(Query.insertPatientMedication);
        preparedStatement.setInt(1,patientId);
        preparedStatement.setInt(2,medicineId);
        db.executeUpdate(preparedStatement);
        System.out.println("  MEDICINE ADDED SUCCESSFULLY  ");
    }
}
