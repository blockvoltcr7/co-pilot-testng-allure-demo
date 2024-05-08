package AllureTest;

import Utils.DBUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestPostGresSQLConnection {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @BeforeClass
    public void setUp() throws SQLException {
        // Establish the connection using the DBUtils class
        conn = DBUtils.getConnection();
        System.out.println("Connected to the PostgreSQL server successfully.");
    }

    @Test
    public void testDatabaseConnection() {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        // Close resources using the DBUtils class
        DBUtils.closeResources(rs, stmt, conn);
    }
}