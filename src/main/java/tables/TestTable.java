package tables;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import entities.TestObject;
import org.testng.ITestResult;
import utils.DatabaseUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TestTable {
    private static final ISettingsFile testData = new JsonSettingsFile("testData.json");
    private static final Logger logger = Logger.getInstance();
    private static final String INSERT_QUERY = "INSERT INTO test " +
            "(name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static void insertTestResult(ITestResult result) {
        TestObject testObject = createTestRecord(result);
        PreparedStatement insertTestStatement = createInsertStatement(testObject);
        try {
            int insertedRows = insertTestStatement.executeUpdate();
            logger.info("Inserted %d rows", insertedRows);
        } catch (SQLException e) {
            logger.error("Error inserting into test table: " + e.getMessage());
        }
        finally {
            DatabaseUtils.closeConnection();
        }
    }

    private static TestObject createTestRecord(ITestResult result) {
        TestObject testObject = new TestObject();
        testObject.setName(result.getName());
        testObject.setStatusId(result.getStatus());
        testObject.setMethodName(result.getMethod().getMethodName());
        testObject.setProjectId(Integer.parseInt(testData.getValue("/projectId").toString()));
        testObject.setSessionId(Integer.parseInt(testData.getValue("/sessionId").toString()));
        testObject.setStartTime(new Timestamp(result.getStartMillis()));
        testObject.setEndTime(new Timestamp(result.getEndMillis()));
        testObject.setEnv(testData.getValue("/env").toString());
        testObject.setBrowser(AqualityServices.getConfiguration().getBrowserProfile().getBrowserName().name());

        return testObject;
    }

    private static PreparedStatement createInsertStatement(TestObject testObject) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseUtils.getConnection().prepareStatement(INSERT_QUERY);
            statement.setString(1, testObject.getName());
            statement.setInt(2, testObject.getStatusId());
            statement.setString(3, testObject.getMethodName());
            statement.setInt(4, testObject.getProjectId());
            statement.setInt(5, testObject.getSessionId());
            statement.setTimestamp(6, testObject.getStartTime());
            statement.setTimestamp(7, testObject.getEndTime());
            statement.setString(8, testObject.getEnv());
            statement.setString(9, testObject.getBrowser());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return statement;
    }
}
