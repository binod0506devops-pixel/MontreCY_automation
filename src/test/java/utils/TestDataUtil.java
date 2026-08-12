package utils;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public final class TestDataUtil {

    private static final String CONTENT_DATA_FILE =
            "testdata/contentmoduledata.csv";

    private TestDataUtil() {
        // Utility class
    }

    public static List<Map<String, String>> loadContentData() {

        List<Map<String, String>> data =
                CsvReader.read(CONTENT_DATA_FILE);

        if (data == null || data.isEmpty()) {
            throw new IllegalStateException(
                    "No content data found in " + CONTENT_DATA_FILE
            );
        }

        return data;
    }

    public static String getRequiredValue(
            Map<String, String> data,
            String fieldName) {

        String value = data.get(fieldName);

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    fieldName + " is missing in CSV test data."
            );
        }

        return value.trim();
    }

    public static String getResourcePath(String fileName) {

        return Paths.get(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                fileName
        ).toString();
    }
}