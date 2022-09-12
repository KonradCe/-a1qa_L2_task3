package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;

public class LoggerUtils {

    private static final ILocalizedLogger localizedLogger = AqualityServices.getLocalizedLogger();

    public static void info(String message) {
        localizedLogger.info(message);
    }

    public static void error(String message) {
        localizedLogger.error(message);
    }

}