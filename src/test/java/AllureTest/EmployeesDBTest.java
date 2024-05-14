package AllureTest;

import Utils.DBUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDBTest extends BeforeAndAfterSetup {

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
    @Epic("EPIC-5679: Employee DB Management")
    @Issue("JIRA-1234")
    @Feature("Verify expected departments are found in the employees table")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Only Finance, Marketing, and IT departments are expected to be found in the employees table")
    public void verifyNewExpectedDepartments() {
        List<String> departments = new ArrayList<>();

        try {
            // Execute a query to test the connection
            String sql = "SELECT department FROM employees";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String department = rs.getString("department");
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> expectedDepartments = new ArrayList<>();
        expectedDepartments.add("Finance");
        expectedDepartments.add("Marketing");
        expectedDepartments.add("IT");

        // Assert that the expected departments are found in the employees table
        Assert.assertTrue(departments.containsAll(expectedDepartments), "Expected departments not found in the employees table");


    }


}
