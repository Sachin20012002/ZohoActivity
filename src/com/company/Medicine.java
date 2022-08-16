package com.company;

import java.sql.*;

public class Medicine {


    public static void addMedicine(String medName) throws SQLException {
        JDBC db=JDBC.getInstance();
        PreparedStatement preparedStatement=db.getPreparedStatement(Query.insertIntoMedicine);
        preparedStatement.setString(1,medName);
        db.executeUpdate(preparedStatement);
    }
    public static int getMedicineId(String medName) throws SQLException {
        JDBC db=JDBC.getInstance();
        ResultSet resultSet=db.executeQuery(Query.getMedID(medName));
        resultSet.next();
        return resultSet.getInt(1);
    }
    public static boolean isExistMedicine(String medName) throws SQLException {
        JDBC db=JDBC.getInstance();
        ResultSet resultSet=db.executeQuery(Query.checkMedicineQuery(medName));
        return resultSet.next();

    }
}
