package tables;

import aquality.selenium.core.logging.Logger;
import models.AuthorModel;
import utils.DatabaseUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorTable {

    public static final String SELECT_ID_WHERE_LOGIN_QUERY = "SELECT id FROM author WHERE login = ?";
    public static final String INSERT_AUTHOR_QUERY = "INSERT INTO author (name, login, email) VALUES (?, ?, ?)";
    public static final String COUNT_AUTHORS_WITH_LOGIN = "SELECT COUNT(id) from author where login = ?";

    private static final Logger logger = Logger.getInstance();

    public static int getCurrentAuthorId() {
        AuthorModel author = AuthorModel.getCurrentAuthor();

        if (!isAuthorInDb(author.getLogin())) {
            logger.info("Current author was not found in db, adding %s", author.getLogin());
            insertAuthor(author);
        }

        PreparedStatement statement = null;
        try {
            statement = DatabaseUtils.getConnection().prepareStatement(SELECT_ID_WHERE_LOGIN_QUERY);
            statement.setString(1, author.getLogin());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            logger.error("Error querying the db: " + e.getMessage());
        } finally {
            DatabaseUtils.closeConnection();
        }

        return -1;
    }

    private static boolean isAuthorInDb(String login) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseUtils.getConnection().prepareStatement(COUNT_AUTHORS_WITH_LOGIN);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("COUNT(ID)") == 1;
        } catch (SQLException e) {
            logger.error("Error while checking if author is in db: " + e.getMessage());
        } finally {
            DatabaseUtils.closeConnection();
        }
        return false;
    }

    private static void insertAuthor(AuthorModel authorModel) {
        PreparedStatement statement = null;

        try {
            statement = DatabaseUtils.getConnection().prepareStatement(INSERT_AUTHOR_QUERY);
            statement.setString(1, authorModel.getName());
            statement.setString(2, authorModel.getLogin());
            statement.setString(3, authorModel.getEmail());
            int insertedRows = statement.executeUpdate();
            logger.debug("Inserted %d rows", insertedRows);
        } catch (SQLException e) {
            logger.error("Error inserting author into db: " + e.getMessage());
        } finally {
            DatabaseUtils.closeConnection();
        }
    }
}
