package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    Connection con=null;

    public static Connection ConnectDB(){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zohoProject", "root", "password");
            return connection;
        }catch(SQLException e){
            System.out.println("Cannot connect to dataBase");
            return null;
        }
    }
}
