package kernel.files;

import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by kadir.basol on 4/22/2016.
 */
public class ExcelUtils {

    public static final void excelIter( ObservableList list ) {
        for (Object o : list) {

        }

        File output = new File( "workbook.xls" );
        Workbook wb = new HSSFWorkbook();
        //Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");
        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(0);

        // Create a cell and put a date value in it.  The first cell is not styled
        // as a date.
        Cell cell = row.createCell(0);
        cell.setCellValue(new Date());

        // we style the second cell as a date (and time).  It is important to
        // create a new cell style from the workbook otherwise you can end up
        // modifying the built in style and effecting not only this cell but other cells.
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        //you can also set date as java.util.Calendar
        cell = row.createCell(2);
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        // Write the output to a file
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(output);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Desktop.getDesktop().open(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static final void deneme(String filex) {
        try {
            FileInputStream file = new FileInputStream(filex);

            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
//                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                            final int columnIndex = cell.getColumnIndex();
                            if (columnIndex == 1) {
                                final String stringCellValue = cell.getStringCellValue();
                                int indexX = 0;
                                if ((indexX = stringCellValue.indexOf("SENI TAKIP EDIYOR")) != -1) {
                                    final String substring = stringCellValue.substring(0, indexX);
                                    cell.setCellValue(substring);
                                } else if ((indexX = stringCellValue.indexOf("SENI TAKIP EDIYOR")) != -1) {
                                    final String substring = stringCellValue.substring(0, indexX);
                                    cell.setCellValue(substring);
                                }
                            }
                            break;
                    }
                }
            }
            file.close();
            FileOutputStream out = new FileOutputStream(filex);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
