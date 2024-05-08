package AllureTest;

import java.sql.*;

public class TestPostGresSQLConnection {

    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/co-pilot-demo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "superuser";

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");

            // Execute a query to test the connection
            String sql = "SELECT * FROM employees";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                System.out.printf("ID: %d, Name: %s %s, Email: %s%n", employeeId, firstName, lastName, email);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
