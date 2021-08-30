package db;

/**
 * https://dev.mysql.com/doc/connector-j/8.0/en/
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/sergio?" + "user=root&password=12345");

            System.out.println("sucess connection");

            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return conn;
    }

    public static void main(String[] args) {
        MySQL.getConnection();
    }

}
