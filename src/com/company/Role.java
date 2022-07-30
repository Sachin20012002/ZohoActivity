package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Role {

    public Role(String roleName, int priority) throws SQLException {
        Connection con=Connect.ConnectDB();
        Statement statement;
        statement= con.createStatement();
        String insertRole="insert into role (roleName,priority) values ('"+roleName+"',"+priority+");";
        statement.executeUpdate(insertRole);
        con.close();
    }
}
