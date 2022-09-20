package models;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class AuthorModel {

    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");

    private int id;
    private String name;
    private String login;
    private String email;

    public AuthorModel(String name, String login, String email) {
        this.name = name;
        this.login = login;
        this.email = email;
    }

    public static AuthorModel getCurrentAuthor() {
        return new AuthorModel(
                testData.getValue("/user/name").toString(),
                testData.getValue("/user/login").toString(),
                testData.getValue("/user/email").toString()
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
