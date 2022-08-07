package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    static Connection connection=null;
    public static Connection ConnectDB(){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DataBase.DataBaseURL, DataBase.Username, DataBase.Password);
            return connection;
        }catch(SQLException e){
            System.out.println("Cannot connect to dataBase");
            return null;
        }
    }
}
