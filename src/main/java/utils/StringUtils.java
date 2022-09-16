package utils;

import java.sql.Timestamp;
import java.util.Random;

public class StringUtils {

    public static String getRandomDoubleDigitString() {
        Random random = new Random();
        return "%" + String.valueOf(random.nextInt(10)).repeat(2) + "%";
    }

    public static Timestamp convertStringToTimestamp(String ts) {
        if (ts != null) {
            return Timestamp.valueOf(ts);
        }
        else {
            return null;
        }
    }
}
