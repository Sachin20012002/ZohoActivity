package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientMedication {
    public static void addPatientMedication(int medicineId, int patientId) throws SQLException {
        Connection connection=Connect.ConnectDB();
        assert connection!=null;
        PreparedStatement preparedStatement=connection.prepareStatement(Query.insertPatientMedication);
        preparedStatement.setInt(1,patientId);
        preparedStatement.setInt(2,medicineId);
        preparedStatement.executeUpdate();
        System.out.println("  MEDICINE ADDED SUCCESSFULLY  ");
    }
}
