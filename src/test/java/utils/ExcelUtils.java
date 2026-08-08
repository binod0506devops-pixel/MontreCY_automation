package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelUtils {


    private static final String EXCEL_PATH =
            "testdata/TestData.xlsx";


    /**
     * Reads Excel file and returns all test data.
     *
     * Format:
     *
     * Browser | URL | Username | Password | Resume
     *
     */
    public static List<HashMap<String, String>> getTestData() {


        List<HashMap<String, String>> testData =
                new ArrayList<>();


        try (
                FileInputStream fis =
                        new FileInputStream(EXCEL_PATH);

                Workbook workbook =
                        WorkbookFactory.create(fis)
        ) {


            Sheet sheet =
                    workbook.getSheet("Sheet1");


            if(sheet == null) {

                throw new RuntimeException(
                        "Sheet1 not found in Excel file"
                );

            }


            // Header row
            Row headerRow =
                    sheet.getRow(0);


            int totalColumns =
                    headerRow.getLastCellNum();



            // Reading rows
            for(int i = 1;
                i <= sheet.getLastRowNum();
                i++) {


                Row currentRow =
                        sheet.getRow(i);



                // Skip empty rows
                if(currentRow == null) {
                    continue;
                }



                HashMap<String,String> rowData =
                        new HashMap<>();



                boolean emptyRow = true;



                for(int j = 0;
                    j < totalColumns;
                    j++) {


                    String columnName =
                            headerRow
                                    .getCell(j)
                                    .getStringCellValue()
                                    .trim();



                    Cell cell =
                            currentRow.getCell(j);



                    String cellValue = "";



                    if(cell != null) {

                        cellValue =
                                getCellValue(cell);

                    }



                    if(!cellValue.isBlank()) {

                        emptyRow = false;

                    }


                    rowData.put(
                            columnName,
                            cellValue
                    );

                }



                // Add only valid rows
                if(!emptyRow) {

                    testData.add(rowData);

                }

            }



            System.out.println(
                    "Total Test Data Loaded : "
                            + testData.size()
            );


        }
        catch(Exception e) {


            throw new RuntimeException(
                    "Failed to read Excel file",
                    e
            );

        }



        return testData;

    }




    /**
     * Converts Excel cell value into String
     */
    private static String getCellValue(Cell cell) {


        DataFormatter formatter =
                new DataFormatter();


        return formatter
                .formatCellValue(cell)
                .trim();

    }


}