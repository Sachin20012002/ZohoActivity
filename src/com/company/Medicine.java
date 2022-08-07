package com.company;

import java.sql.*;

public class Medicine {
    static Connection connection;
    static Statement statement;
    static PreparedStatement preparedStatement;

    public static void addMedicine(String medName) throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        preparedStatement = connection.prepareStatement(Query.insertIntoMedicine);
        preparedStatement.setString(1,medName);
        preparedStatement.executeUpdate();
    }
    public static int getMedicineId(String medName) throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(Query.getMedID(medName));
        resultSet.next();
        return resultSet.getInt(1);
    }
    public static boolean isExistMedicine(String medName) throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(Query.checkMedicineQuery(medName));
        return resultSet.next();

    }
}
