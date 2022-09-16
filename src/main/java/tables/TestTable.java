package tables;

import aquality.selenium.core.logging.Logger;
import models.TestModel;
import utils.DatabaseUtils;
import utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestTable {

    public static final String SELECT_RANDOM_TESTS_QUERY = "SELECT * FROM test where id LIKE ? LIMIT 10";
    // TODO: column names should be stored somewhere else (json? enum?) and only referenced here
    private static final String INSERT_QUERY = "INSERT INTO test " +
            "(name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser, author_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final Logger logger = Logger.getInstance();

    public static void insertTest(TestModel testModel) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseUtils.getConnection().prepareStatement(INSERT_QUERY);
            statement.setString(1, testModel.getName());
            statement.setInt(2, testModel.getStatusId());
            statement.setString(3, testModel.getMethodName());
            statement.setInt(4, testModel.getProjectId());
            statement.setInt(5, testModel.getSessionId());
            statement.setTimestamp(6, testModel.getStartTime());
            statement.setTimestamp(7, testModel.getEndTime());
            statement.setString(8, testModel.getEnv());
            statement.setString(9, testModel.getBrowser());
            statement.setInt(10, testModel.getAuthorId());

            int insertedRows = statement.executeUpdate();
            System.out.println(insertedRows);
            logger.info("Inserted %d rows", insertedRows);
        } catch (SQLException e) {
            logger.error("Error inserting into test table: " + e.getMessage());
        } finally {
            DatabaseUtils.closeConnection();
        }
    }

    public static ArrayList<Object[]> getRandomTests() {
        ArrayList<Object[]> result = new ArrayList<>();
        PreparedStatement statement = null;
        String randomIdDigits = StringUtils.getRandomDoubleDigitString();
        try {
            statement = DatabaseUtils.getConnection().prepareStatement(SELECT_RANDOM_TESTS_QUERY);
            statement.setString(1, randomIdDigits);
            System.out.println(statement);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TestModel testModel = new TestModel();
                // TODO: column names should be stored somewhere else (json? enum?) and only referenced here
                testModel.setId(rs.getInt("id"));
                testModel.setName(rs.getString("name"));
                testModel.setStatusId(rs.getInt("status_id"));
                testModel.setMethodName(rs.getString("method_name"));
                testModel.setProjectId(rs.getInt("project_id"));
                testModel.setSessionId(rs.getInt("session_id"));
                testModel.setStartTime(StringUtils.convertStringToTimestamp(rs.getString("start_time")));
                testModel.setEndTime(StringUtils.convertStringToTimestamp(rs.getString("end_time")));
                testModel.setEnv(rs.getString("env"));
                testModel.setBrowser(rs.getString("browser"));
                testModel.setAuthorId(rs.getInt("author_id"));
                result.add(new Object[]{testModel});
            }
        } catch (SQLException e) {
            logger.error("Error selecting random test, with id containing " + randomIdDigits + ": " + e.getMessage());
        } finally {
            DatabaseUtils.closeConnection();
        }
        return result;
    }
}
