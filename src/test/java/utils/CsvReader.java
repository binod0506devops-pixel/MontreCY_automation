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

    /**
     * Reads CSV file from the project testdata directory.
     *
     * Example:
     * CsvReader.read("testdata/contentmoduledata.csv");
     */
    public static List<Map<String, String>> read(String filePath) {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("Reading CSV file: " + filePath);
        System.out.println("============================================================");

        try {

            /*
             * 1. Get project root
             */
            Path projectRoot = Paths.get(
                    System.getProperty("user.dir")
            ).toAbsolutePath().normalize();

            System.out.println("Project root : " + projectRoot);

            /*
             * 2. Get requested file name
             */
            Path requestedPath = Paths.get(filePath);

            String fileName = requestedPath
                    .getFileName()
                    .toString()
                    .trim();

            /*
             * 3. Locate testdata directory
             */
            Path testDataDirectory;

            if (requestedPath.getParent() != null) {

                testDataDirectory = projectRoot.resolve(
                        requestedPath.getParent()
                ).normalize();

            } else {

                testDataDirectory = projectRoot.resolve("testdata");
            }

            System.out.println("Testdata directory : " + testDataDirectory);
            System.out.println(
                    "Directory exists   : " +
                            Files.isDirectory(testDataDirectory)
            );

            /*
             * 4. First try the normal path
             */
            Path csvPath = projectRoot
                    .resolve(filePath)
                    .normalize();

            System.out.println("Normal CSV path    : " + csvPath);
            System.out.println("Exists             : " + Files.exists(csvPath));
            System.out.println("Regular file       : " + Files.isRegularFile(csvPath));

            /*
             * 5. If normal path doesn't work,
             *    search the directory and use the EXACT Path
             *    returned by Java's filesystem.
             */
            if (!Files.isRegularFile(csvPath)) {

                System.out.println();
                System.out.println("Normal path was not usable.");
                System.out.println("Searching directory for actual CSV file...");

                csvPath = findActualFile(
                        testDataDirectory,
                        fileName
                );
            }

            /*
             * 6. Final validation
             */
            if (csvPath == null) {

                throw new RuntimeException(
                        "\nCSV file could not be located.\n" +
                                "Requested file : " + filePath + "\n" +
                                "Project root   : " + projectRoot + "\n" +
                                "Testdata folder: " + testDataDirectory
                );
            }

            System.out.println();
            System.out.println("Actual CSV path  : " + csvPath);
            System.out.println("Exists           : " + Files.exists(csvPath));
            System.out.println("Regular file     : " + Files.isRegularFile(csvPath));

            /*
             * 7. Read CSV
             */
            return readCsvFile(csvPath);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to read CSV file: " + filePath,
                    e
            );
        }
    }


    /**
     * Finds the actual file from the directory.
     *
     * This is important for your Windows environment because
     * Java's direct reconstructed Path is currently reporting
     * exists=false even though directory listing can find the file.
     */
    private static Path findActualFile(
            Path directory,
            String requestedFileName) throws IOException {

        if (!Files.isDirectory(directory)) {

            System.out.println(
                    "Directory does not exist: " + directory
            );

            return null;
        }

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(directory)) {

            for (Path path : stream) {

                String actualFileName =
                        path.getFileName().toString();

                System.out.println(
                        "Found filesystem entry: [" +
                                actualFileName +
                                "]"
                );

                /*
                 * Compare after trimming and ignoring case.
                 */
                if (actualFileName.trim()
                        .equalsIgnoreCase(
                                requestedFileName.trim()
                        )) {

                    System.out.println(
                            "MATCH FOUND: " + path
                    );

                    return path;
                }
            }
        }

        return null;
    }


    /**
     * Reads the actual CSV file.
     */
    private static List<Map<String, String>> readCsvFile(
            Path csvPath) throws IOException {

        List<Map<String, String>> rows =
                new ArrayList<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(
                             csvPath,
                             StandardCharsets.UTF_8
                     )) {

            /*
             * Read header
             */
            String headerLine = reader.readLine();

            if (headerLine == null ||
                    headerLine.trim().isEmpty()) {

                throw new RuntimeException(
                        "CSV file is empty: " + csvPath
                );
            }

            /*
             * Remove UTF-8 BOM if present.
             */
            headerLine = removeBom(headerLine);

            List<String> headers =
                    parseCsvLine(headerLine);

            /*
             * Clean headers
             */
            for (int i = 0; i < headers.size(); i++) {

                headers.set(
                        i,
                        headers.get(i)
                                .trim()
                );
            }

            System.out.println();
            System.out.println("CSV HEADERS:");

            for (String header : headers) {
                System.out.println("[" + header + "]");
            }

            /*
             * Read rows
             */
            String line;

            while ((line = reader.readLine()) != null) {

                /*
                 * Ignore completely empty lines.
                 */
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> values =
                        parseCsvLine(line);

                Map<String, String> row =
                        new LinkedHashMap<>();

                for (int i = 0; i < headers.size(); i++) {

                    String value = "";

                    if (i < values.size()) {
                        value = values.get(i);
                    }

                    if (value == null) {
                        value = "";
                    }

                    value = value.trim();

                    row.put(
                            headers.get(i),
                            value
                    );
                }

                rows.add(row);
            }
        }

        System.out.println();
        System.out.println(
                "# CSV ROWS READ : " + rows.size()
        );

        if (!rows.isEmpty()) {

            System.out.println();
            System.out.println("FIRST CSV ROW:");

            for (Map.Entry<String, String> entry :
                    rows.get(0).entrySet()) {

                System.out.println(
                        entry.getKey() +
                                " = [" +
                                entry.getValue() +
                                "]"
                );
            }
        }

        System.out.println(
                "============================================================"
        );
        System.out.println();

        return rows;
    }


    /**
     * Basic CSV parser.
     *
     * Supports:
     * - comma separated values
     * - quoted values
     * - commas inside quoted values
     * - escaped quotes ("")
     */
    private static List<String> parseCsvLine(
            String line) {

        List<String> values =
                new ArrayList<>();

        StringBuilder current =
                new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char c = line.charAt(i);

            if (c == '"') {

                /*
                 * Escaped quote:
                 * ""
                 */
                if (insideQuotes &&
                        i + 1 < line.length() &&
                        line.charAt(i + 1) == '"') {

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

        /*
         * Add final value.
         */
        values.add(current.toString());

        return values;
    }


    /**
     * Removes UTF-8 BOM.
     */
    private static String removeBom(String value) {

        if (value != null &&
                !value.isEmpty() &&
                value.charAt(0) == '\uFEFF') {

            return value.substring(1);
        }

        return value;
    }
}