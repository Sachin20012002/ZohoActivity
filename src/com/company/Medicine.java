package com.company;

import java.sql.*;

public class Medicine {


    public static void addMedicine(String medName) throws SQLException {
        DConnect db=new DConnect();
        PreparedStatement preparedStatement=db.getPreparedStatement(Query.insertIntoMedicine);
        preparedStatement.setString(1,medName);
        db.executeUpdate(preparedStatement);
    }
    public static int getMedicineId(String medName) throws SQLException {
        DConnect db=new DConnect();
        ResultSet resultSet=db.executeQuery(Query.getMedID(medName));
        resultSet.next();
        return resultSet.getInt(1);
    }
    public static boolean isExistMedicine(String medName) throws SQLException {
        DConnect db=new DConnect();
        ResultSet resultSet=db.executeQuery(Query.checkMedicineQuery(medName));
        return resultSet.next();

    }
}
