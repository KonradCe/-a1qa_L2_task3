import base.BaseTestCaseTwo;
import models.TestModel;
import org.testng.annotations.Test;
import tables.TestTable;

import java.sql.Timestamp;
import java.util.Random;

public class ProcessingDataTest extends BaseTestCaseTwo {

    @Test(dataProvider = "randomTests")
    public void simulateTest(TestModel testModel) {
        logger.info("Simulating executing test %s", testModel.getName());
        testModel.setStatusId(new Random().nextInt(3) + 1);
        testModel.setStartTime(new Timestamp(System.currentTimeMillis()));
        try {
            Thread.sleep(new Random().nextInt(400) + 100);
        } catch (InterruptedException e) {
            logger.debug("sleep interrupted");
        }
        testModel.setEndTime(new Timestamp(System.currentTimeMillis()));

        logger.info("Updating previously created records with new results from test simulation");
        TestTable.updateTest(testModel);
    }
}
