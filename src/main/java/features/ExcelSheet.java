package features;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import libraries.ReportLibrary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelSheet {

	public static String locOfInputExcel ;

    public int totalrows(String testDataFileName, String sheetname) throws IOException {
        locOfInputExcel = ReportLibrary.getPath()+"\\testdata\\"+testDataFileName+".xlsx";
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        
       /* FileOutputStream fos=new FileOutputStream(locOfInputExcel);
        w.write(fos);
        fis.close();*/

        return s.getLastRowNum();
    }

    /*public static XSSFRow getRow(String sheetname, int rownum) throws IOException {
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
        return r;
    }
    */
    public static String getUniqueLastNameAndFirstName(String sheetname) throws IOException {
    	  FileInputStream fis = new FileInputStream(locOfInputExcel);
          XSSFWorkbook w = new XSSFWorkbook(fis);
          XSSFSheet s = w.getSheet(sheetname);
         // XSSFRow r = s.getRow(1); 
           
          int noOfRows = 5000;
          
        DataFormatter formatter = new DataFormatter();
        int randomNumForPickLastName = (int )(Math.random() * noOfRows + 1);
        int randomNumForPicKFirstName = (int )(Math.random() * noOfRows + 1);
        
        XSSFRow rForLastName = s.getRow(randomNumForPickLastName);  
        XSSFRow rForFirstName = s.getRow(randomNumForPicKFirstName);
        
        String cellVal= formatter.formatCellValue((rForLastName.getCell(2))).trim()+"_"+formatter.formatCellValue((rForFirstName.getCell(1))).trim()+
        		"_"+formatter.formatCellValue((rForFirstName.getCell(3))).trim();
        
        return cellVal;
    }

    public static void writeExcel(String testDataFileName,String sheetname, int rownum, int colnum,String accno,boolean headerRow) throws EncryptedDocumentException, InvalidFormatException, IOException {
    	locOfInputExcel = ReportLibrary.getPath()+"\\testdata\\"+testDataFileName+".xlsx";
    	FileInputStream fis=new FileInputStream(locOfInputExcel);
    	XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
        Cell cell=r.createCell(colnum);
        cell.setCellType(cell.CELL_TYPE_STRING);
        cell.setCellValue(accno);
        XSSFCellStyle headerStyle = w.createCellStyle();
        Font headerFont = w.createFont();
        //headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        if (headerRow) {
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        } else {
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }
        headerStyle.setFont(headerFont);
        cell.setCellStyle(headerStyle);
        FileOutputStream fos=new FileOutputStream(locOfInputExcel);
        w.write(fos);
        fos.close();

    }
  //**************code for Write Excel sheet by column Name******************************************************
    public static void writeTestCaseData(String testDataFileName, String sheetName, int currentRowNumber,String AccNumber,String ColumnName) throws EncryptedDocumentException, InvalidFormatException, IOException {

        DataFormatter formatter = new DataFormatter();
        XSSFRow rowWithColumnNames = null;
        try {
            rowWithColumnNames = ExcelSheet.getRow(sheetName, 0,testDataFileName);
           // System.out.println(rowWithColumnNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
       

        for (int p = 0; p < rowWithColumnNames.getLastCellNum(); p++) {
            //Ignore the columns without any column name in test case excel file
        	//System.out.println(rowWithColumnNames.getCell(p));
        	String ColumnValue=formatter.formatCellValue(rowWithColumnNames.getCell(p));
            if (ColumnValue.equalsIgnoreCase(ColumnName)) {
            	ExcelSheet.writeExcel(testDataFileName, sheetName, currentRowNumber, p,AccNumber, true);
            }
    
        }
    }   

    public static XSSFRow getRow(String sheetname, int rownum,String  testDataFileName) throws IOException {
    	locOfInputExcel = ReportLibrary.getPath()+"\\testdata\\"+testDataFileName+".xlsx";
        FileInputStream fis = new FileInputStream(locOfInputExcel);
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        XSSFRow r = s.getRow(rownum);
        w.close();
        fis.close();
        return r;
    }

    public static void writeAccntNoForeachTestdata(String testDataFileName, String sheetName, int noOFRowsinScenarioFile,String AccNumber,String ColumnName,String TestcaseID) throws EncryptedDocumentException, InvalidFormatException, IOException {
    	int RowNumber=0;
    	//int currentRowNo=1;
        DataFormatter formatter = new DataFormatter();
        XSSFRow rowWithColumnNames = null,rowWithTestCaseID = null;
        try {	
            rowWithColumnNames = ExcelSheet.getRow(sheetName, 0,testDataFileName);
            
            		for (int i = 0; i <=noOFRowsinScenarioFile; i++) {
            			rowWithTestCaseID=ExcelSheet.getRow(sheetName, i,testDataFileName);
                        //Ignore the columns without any column name in test case excel file
                    	//System.out.println(rowWithColumnNames.getCell(p));
                    	String RowValue=formatter.formatCellValue(rowWithTestCaseID.getCell(0));
                        if (RowValue.equalsIgnoreCase(TestcaseID)) {
                        	RowNumber=i;
                        	break;
                        }
                       // currentRowNo++;
            		}
           // System.out.println(rowWithColumnNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
       

        for (int p = 0; p < rowWithColumnNames.getLastCellNum(); p++) {
            //Ignore the columns without any column name in test case excel file
        	//System.out.println(rowWithColumnNames.getCell(p));
        	String ColumnValue=formatter.formatCellValue(rowWithColumnNames.getCell(p));
            if (ColumnValue.equalsIgnoreCase(ColumnName)) {
            	ExcelSheet.writeExcel(testDataFileName, sheetName, RowNumber, p,AccNumber, true);
          
            	break;
            	
            }
    
        }
    }  

}

