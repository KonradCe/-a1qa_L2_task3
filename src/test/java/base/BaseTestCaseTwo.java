package base;

import aquality.selenium.core.logging.Logger;
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
            test.updateToCurrentProject();
            test.setAuthorId(AuthorTable.getCurrentAuthorId());

            TestTable.insertTest(test);
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
    }
}
