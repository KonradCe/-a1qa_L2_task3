package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class ConfigDataUtils {

    private static final ISettingsFile dbData = new JsonSettingsFile("configData.json");

    public static String getDbUrl() {
        return dbData.getValue("/dbUrl").toString();
    }
}
