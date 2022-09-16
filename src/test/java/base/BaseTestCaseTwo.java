package base;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.TestModel;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import tables.AuthorTable;
import tables.TestTable;

import java.util.ArrayList;
import java.util.Iterator;

public class BaseTestCaseTwo {

    private static final ISettingsFile testData = new JsonSettingsFile("testData.json");
    public Logger logger = null;
    private ArrayList<Object[]> testModels = null;
    private ArrayList<Integer> idsOfInsertedTests = new ArrayList<>();

    @DataProvider(name = "randomTests")
    public Iterator<Object[]> getTestCases() {
        testModels = TestTable.getRandomTests();

        Iterator<Object[]> testModelIterator = testModels.iterator();
        while (testModelIterator.hasNext()) {
            Object[] ts = testModelIterator.next();
            TestModel test = (TestModel) ts[0];
            test.setProjectId(Integer.parseInt(testData.getValue("/projectId").toString()));
            test.setAuthorId(AuthorTable.getCurrentAuthorId());
            int generatedId = TestTable.insertTest(test);
            test.setId(generatedId);
            idsOfInsertedTests.add(generatedId);
        }

        return testModels.iterator();
    }

    @BeforeMethod
    public void setUp() {
        if (logger == null) {
            logger = Logger.getInstance();
        }
        logger.info("Test set up");
    }

    @AfterMethod()
    public void afterTest(ITestResult result) {
        logger.info("Test tear down");
        for (int testId : idsOfInsertedTests) {
            TestTable.deleteTest(testId);
        }
    }
}
