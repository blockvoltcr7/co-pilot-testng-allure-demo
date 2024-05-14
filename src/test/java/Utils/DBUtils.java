package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/template1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "superuser";

    // Load the PostgreSQL JDBC driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get a new database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Close the connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Close the PreparedStatement
    public static void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Close the ResultSet
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Close all resources together
    public static void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(conn);
    }
}
