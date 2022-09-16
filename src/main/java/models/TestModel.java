package models;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.ITestResult;
import tables.AuthorTable;

import java.sql.Timestamp;

public class TestModel {

    private static final ISettingsFile testData = new JsonSettingsFile("testData.json");

    private int id;
    private String name;
    private int statusId;
    private String methodName;
    private int projectId;
    private int sessionId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String env;
    private String browser;
    private int authorId;

    public static TestModel createFromTestNgResult(ITestResult result) {
        TestModel testModel = new TestModel();
        testModel.setName(result.getName());
        testModel.setStatusId(result.getStatus());
        testModel.setMethodName(result.getMethod().getMethodName());
        testModel.setProjectId(Integer.parseInt(testData.getValue("/projectId").toString()));
        testModel.setSessionId(Integer.parseInt(testData.getValue("/sessionId").toString()));
        testModel.setStartTime(new Timestamp(result.getStartMillis()));
        testModel.setEndTime(new Timestamp(result.getEndMillis()));
        testModel.setEnv(testData.getValue("/env").toString());
        testModel.setBrowser(AqualityServices.getConfiguration().getBrowserProfile().getBrowserName().name());
        testModel.setAuthorId(AuthorTable.getCurrentAuthorId());

        return testModel;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void updateToCurrentProject() {
        this.projectId = Integer.parseInt(testData.getValue("/projectId").toString());
    }
}
