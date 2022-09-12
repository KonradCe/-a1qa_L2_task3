package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.sql.*;

public class DatabaseUtils {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(dbDataUtils.getDbUrl(),
                        dbCredentialUtils.getUser(),
                        dbCredentialUtils.getPassword());
            } catch (SQLException | ClassNotFoundException exception) {
                LoggerUtils.error("Error while creating connection to db: " + exception.getMessage());
            }
        }
        return connection;
    }


    public void foo() {
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from test limit 10");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // the purpose of this inner class is to improve readability only
    private static class dbCredentialUtils {
        private static final ISettingsFile credentials = new JsonSettingsFile("dbCredentials.json");

        private static String getUser() {
            return credentials.getValue("/user").toString();
        }

        private static String getPassword() {
            return credentials.getValue("/password").toString();
        }
    }

    private static class dbDataUtils {
        private static final ISettingsFile dbData = new JsonSettingsFile("dbData.json");
        private static String getDbUrl() {
            return dbData.getValue("/db_url").toString();
        }
    }
}
