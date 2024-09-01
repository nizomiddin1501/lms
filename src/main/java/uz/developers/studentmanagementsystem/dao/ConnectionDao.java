package uz.developers.studentmanagementsystem.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionDao {

    private static final String url = "jdbc:postgresql://localhost:5432/sms";
    private static final String dbDriver = "org.postgresql.Driver";
    private static final String userName = "postgres";
    private static final String password = "1234";

    public Connection getConnection() {
        try {
            Class.forName(dbDriver);
            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
