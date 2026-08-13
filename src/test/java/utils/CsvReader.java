
package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    public static List<Map<String, String>> read(String filePath) {
        try {
            Path projectRoot = Paths.get(System.getProperty("user.dir"))
                    .toAbsolutePath()
                    .normalize();

            Path requestedPath = Paths.get(filePath);
            String fileName = requestedPath.getFileName().toString().trim();

            Path testDataDirectory = requestedPath.getParent() != null
                    ? projectRoot.resolve(requestedPath.getParent()).normalize()
                    : projectRoot.resolve("testdata");

            Path csvPath = projectRoot.resolve(filePath).normalize();

            if (!Files.isRegularFile(csvPath)) {
                csvPath = findActualFile(testDataDirectory, fileName);
            }

            if (csvPath == null) {
                throw new RuntimeException("CSV file could not be located: " + filePath);
            }

            return readCsvFile(csvPath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }

    private static Path findActualFile(Path directory, String requestedFileName)
            throws IOException {

        if (!Files.isDirectory(directory)) {
            return null;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                String actualFileName = path.getFileName().toString();

                if (actualFileName.trim().equalsIgnoreCase(requestedFileName.trim())) {
                    return path;
                }
            }
        }

        return null;
    }

    private static List<Map<String, String>> readCsvFile(Path csvPath)
            throws IOException {

        List<Map<String, String>> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                csvPath,
                StandardCharsets.UTF_8)) {

            String headerLine = reader.readLine();

            if (headerLine == null || headerLine.trim().isEmpty()) {
                throw new RuntimeException("CSV file is empty: " + csvPath);
            }

            headerLine = removeBom(headerLine);

            List<String> headers = parseCsvLine(headerLine);

            for (int i = 0; i < headers.size(); i++) {
                headers.set(i, headers.get(i).trim());
            }

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> values = parseCsvLine(line);
                Map<String, String> row = new LinkedHashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    String value = i < values.size() ? values.get(i) : "";

                    if (value == null) {
                        value = "";
                    }

                    row.put(headers.get(i), value.trim());
                }

                rows.add(row);
            }
        }

        return rows;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (c == ',' && !insideQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        values.add(current.toString());

        return values;
    }

    private static String removeBom(String value) {
        if (value != null && !value.isEmpty()
                && value.charAt(0) == '\uFEFF') {
            return value.substring(1);
        }

        return value;
    }
}


