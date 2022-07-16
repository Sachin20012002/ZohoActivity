package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Medicine {
    public Medicine(String medName) throws SQLException {
        Connection connection=Connect.ConnectDB();
        Statement statement = connection.createStatement();
        String query = "Insert into medicine (Name) values ('" + medName + "');";
        statement.executeUpdate(query);
    }
    public static int getMedicineId(String medName) throws SQLException {
        Connection connection=Connect.ConnectDB();
        Statement statement = connection.createStatement();
        String medIdquery="select id from medicine where name='"+medName+"';";
        ResultSet resultSet=statement.executeQuery(medIdquery);
        resultSet.next();
        return resultSet.getInt(1);
    }
    public static boolean isExistMedicine(String s) throws SQLException {
        Connection connection=Connect.ConnectDB();
        Statement statement = connection.createStatement();
        String checkMedicineQuery="select * from medicine where Name='"+s+"';";
        ResultSet resultSet=statement.executeQuery(checkMedicineQuery);
        return resultSet.next();

    }
}
