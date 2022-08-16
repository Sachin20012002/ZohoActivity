package com.company;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Role {

    public Role(String roleName, int priority) throws SQLException {
        JDBC db=JDBC.getInstance();
        PreparedStatement preparedStatement=db.getPreparedStatement(Query.insertRole);
        preparedStatement.setString(1,roleName);
        preparedStatement.setInt(2,priority);
        db.executeUpdate(preparedStatement);
    }
}
