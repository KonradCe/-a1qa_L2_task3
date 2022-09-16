package base;

import aquality.selenium.core.logging.Logger;
import models.TestModel;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import tables.TestTable;

public class BaseTestCaseOne {

    public Logger logger = null;

    @BeforeMethod
    public void setUp() {
        if (logger == null) {
            logger = Logger.getInstance();
        }
        logger.info("Test set up");
    }

    @AfterMethod
    public void afterTest(ITestResult result) {
        logger.info("Test tear down - inserting test results into test table");
        TestTable.insertTest(TestModel.createFromTestNgResult(result));
    }
}
