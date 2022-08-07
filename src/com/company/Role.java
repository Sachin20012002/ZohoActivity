package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Role {

    public Role(String roleName, int priority) throws SQLException {
        Connection connection=Connect.ConnectDB();
        assert connection != null;
        PreparedStatement preparedStatement=connection.prepareStatement(Query.insertRole);
        preparedStatement.setString(1,roleName);
        preparedStatement.setInt(2,priority);
        preparedStatement.executeUpdate();
        connection.close();
    }
}
