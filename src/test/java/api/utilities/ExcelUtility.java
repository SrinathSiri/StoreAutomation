package api.utilities;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtility {
    @Test
    public String[][] getExcelData(String excelpath, String sheetpath) throws IOException {

        File file = new File(excelpath);
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sheet = wb.getSheet(sheetpath);

        String[][] data = new String[sheet.getPhysicalNumberOfRows() - 1][sheet.getRow(0).getPhysicalNumberOfCells()];

        for (int rowindex = sheet.getFirstRowNum() + 1; rowindex <= sheet.getLastRowNum(); rowindex++) {
            for (int colindex = sheet.getRow(rowindex).getFirstCellNum(); colindex < sheet.getRow(rowindex).getLastCellNum(); colindex++) {
                if (ObjectUtils.isNotEmpty(sheet.getRow(rowindex).getCell(colindex))) {
                    data[rowindex - 1][colindex] = sheet.getRow(rowindex).getCell(colindex).toString();
                } else {
                    data[rowindex - 1][colindex] = null;
                }
            }
        }
        wb.close();
        return data;
    }

    @DataProvider(name="getdata")
    public String[][] getData() throws IOException {
        return getExcelData(".\\testdata\\ra_ddt_testdata.xlsx","Sheet1");
    }
}