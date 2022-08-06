package com.company;

import java.sql.*;

public class DataBase {
    static Connection connection;
    static PreparedStatement statement;
    static void showDatabase() throws SQLException {
        connection=Connect.ConnectDB();
        String getUserDetails="select * from userdetails;";
        statement= connection.prepareStatement(getUserDetails);
        ResultSet resultSet=statement.executeQuery();
        Display.databaseHeading();
        boolean isExist=false;
        while (resultSet.next()){
            isExist=true;
            System.out.println();
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String role=resultSet.getString("role");
            String userid=resultSet.getString("userid");
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            Display.LineBreak();
        }
        if(!isExist)
        {
            Display.databaseEmpty();
        }
        connection.close();
    }

    static boolean isExistEmail(String email,String role) throws SQLException {
        connection=Connect.ConnectDB();
        String getUserDetailsQuery="select * from userdetails where email=? and role=?;";
        statement = connection.prepareStatement(getUserDetailsQuery);
        statement.setString(1,email);
        statement.setString(2,role);
        ResultSet resultSet=statement.executeQuery();
        boolean flag=resultSet.next();
        connection.close();
        return flag;
    }

    static void printDetails(String role) throws SQLException {
        connection=Connect.ConnectDB();
        String query="select * from userdetails where role=?;";
        statement=connection.prepareStatement(query);
        statement.setString(1,role);
        ResultSet resultSet=statement.executeQuery();
        boolean isExist=false;
        while (resultSet.next())
        {
            isExist=true;
            String name=resultSet.getString("name");
            String email=resultSet.getString("email");
            String userid=resultSet.getString("userid");
            Display.LineBreak();
            System.out.println("UserId:\t"+userid+"\nName:\t"+name+"\nEmail:\t"+email+"\nRole:\t"+role);
            Display.LineBreak();
        }
        if(!isExist)
        {
            Display.noRecordsFound();
        }
        connection.close();
    }

    static boolean isExistUser(String email,String role) throws SQLException {
        connection=Connect.ConnectDB();
        String getUserDetailsQuery="select * from userdetails where email=? and role=?;";
        statement = connection.prepareStatement(getUserDetailsQuery);
        statement.setString(1,email);
        statement.setString(2,role);
        ResultSet resultSet=statement.executeQuery();
        boolean flag=resultSet.next();
        connection.close();
        return flag;
    }

 /*  use this part of code only to clear your database when needed
  private static void clearAllTables() throws SQLException {
        connection=Connect.ConnectDB();
        assert connection != null;
        Statement statement = connection.createStatement();
        statement.executeUpdate(Query.clearuserdetails);
        statement.executeUpdate(Query.clearRole);
        statement.executeUpdate(Query.clearLogin);
        statement.executeUpdate(Query.clearMedicine);
        statement.executeUpdate(Query.clearPatientMedication);
        statement.executeUpdate(Query.setAutoIncrementuserdetails);
        statement.executeUpdate(Query.setAutoIncrementlogin);
        statement.executeUpdate(Query.setAutoIncrementmedicine);
    }

    public static void main(String[] args) throws SQLException {
        clearAllTables();
    }
 */
}
