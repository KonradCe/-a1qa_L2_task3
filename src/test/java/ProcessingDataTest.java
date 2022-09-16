import base.BaseTestCaseTwo;
import models.TestModel;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Random;

public class ProcessingDataTest extends BaseTestCaseTwo {

    @Test(dataProvider = "randomTests")
    public void simulateTest(TestModel testModel){
        testModel.setStatusId(new Random().nextInt(3) + 1);
        testModel.setStartTime(new Timestamp(System.currentTimeMillis()));
        try {
            Thread.sleep(new Random().nextInt(400) + 100);
        } catch (InterruptedException e) {
            logger.debug("sleep interrupted");
        }
        Timestamp stopTime = new Timestamp(System.currentTimeMillis());

    }
}
