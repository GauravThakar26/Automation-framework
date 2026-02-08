package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private static final String FILE_PATH = "src/test/resources/TestData5.xls";
    private static final String DEFAULT_SHEET = "Sheet1";

    // Reads data from the default sheet ("Sheet1")
    public static Object[][] readDefaultSheet() {
        return readExcelData(FILE_PATH, DEFAULT_SHEET);
    }

    // Reads data from the specified Excel file and sheet
    public static Object[][] readExcelData(String filePath, String sheetName) {
        List<Object[]> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;

            if (filePath.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IllegalArgumentException("Unsupported file type: " + filePath);
            }

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in Excel file.");
            }

            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCols = sheet.getRow(0).getLastCellNum();

            for (int i = 1; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Object[] rowData = new Object[totalCols];
                boolean isEmptyRow = true;

                for (int j = 0; j < totalCols; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Object cellValue = getCellValue(cell);

                    if (cellValue != null && !cellValue.toString().trim().isEmpty()) {
                        isEmptyRow = false;
                    }

                    rowData[j] = cellValue;
                }

                if (!isEmptyRow) {
                    dataList.add(rowData);
                }
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading Excel file: " + filePath);
        }

        // Convert List<Object[]> to Object[][]
        return dataList.toArray(new Object[0][0]);
    }

    // Helper method to extract cell values in a proper format
    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case BOOLEAN:
                return cell.getBooleanCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    double value = cell.getNumericCellValue();
                    if (value == Math.floor(value)) {
                        return String.valueOf((long) value);
                    } else {
                        return String.valueOf(value);
                    }
                }

            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return cell.getStringCellValue().trim();

                    case NUMERIC:
                        double value = cell.getNumericCellValue();
                        if (value == Math.floor(value)) {
                            return String.valueOf((long) value);
                        } else {
                            return String.valueOf(value);
                        }

                    case BOOLEAN:
                        return cell.getBooleanCellValue();

                    default:
                        return "";
                }

            case BLANK:
            case _NONE:
                return "";

            default:
                throw new IllegalArgumentException("Unexpected cell type: " + cell.getCellType());
        }
    }
}
