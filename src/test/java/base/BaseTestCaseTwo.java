package base;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.TestModel;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import tables.AuthorTable;
import tables.TestTable;

import java.util.ArrayList;
import java.util.Iterator;

public class BaseTestCaseTwo {

    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    private final ArrayList<Integer> idsOfInsertedTests = new ArrayList<>();
    public Logger logger = null;
    private ArrayList<Object[]> testModels = null;

    @BeforeSuite
    public void setUp() {
        if (logger == null) {
            logger = Logger.getInstance();
        }
        logger.info("Test set up");
    }

    @DataProvider(name = "randomTests")
    public Iterator<Object[]> getTestCases() {
        testModels = TestTable.getSemiRandomTests();

        logger.info("Changing columns (project_id, author) of 'random' test from db and inserting them into db as new records");
        for (Object[] ts : testModels) {
            TestModel test = (TestModel) ts[0];
            test.setProjectId(Integer.parseInt(testData.getValue("/projectId").toString()));
            test.setAuthorId(AuthorTable.getCurrentAuthorId());
            int generatedId = TestTable.insertTest(test);
            test.setId(generatedId);
            idsOfInsertedTests.add(generatedId);
        }
        return testModels.iterator();
    }

    @AfterMethod()
    public void afterTest(ITestResult result) {
        logger.info("Test tear down");
        logger.info("Deleting previously inserted test records");
        for (int testId : idsOfInsertedTests) {
            TestTable.deleteTest(testId);
        }
    }
}
