package org.keywordsframework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.keywordsframework.configuration.Constans;
import org.keywordsframework.testScripts.TestSuiteByExcel;

public class ExcelUtil {
	private static XSSFWorkbook ExcelWorkbook;
	private static XSSFSheet ExcelSheet;
	private static XSSFRow Row;
	private static XSSFCell Cell;
	
	
	//��ȡExcel�ļ�·��
	public static void setExcelFile(String path) throws Exception {
		FileInputStream file;
		try {
			file = new FileInputStream(path);
			ExcelWorkbook = new XSSFWorkbook(file);
			file.close();		
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel·����ȡʧ��");
			e.printStackTrace();
		}
	}
	
	//��ȡExcel�ļ�·��
	public static void setExcelFile(String path,String sheetName) throws Exception {
		FileInputStream file;
		try {
			file = new FileInputStream(path);
			ExcelWorkbook = new XSSFWorkbook(file);
			ExcelSheet = ExcelWorkbook.getSheet(sheetName);
			file.close();		
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel·����ȡʧ��");
			e.printStackTrace();
		}
	}
	
	//��ȡָ��Sheet�еĵ�Ԫ������ֻ֧��.xlsx
	public static String getCellData(String sheetName, int rowN, int cellN) {
		
		ExcelSheet = ExcelWorkbook.getSheet(sheetName);
		
		try {
			Cell = ExcelSheet.getRow(rowN).getCell(cellN);
			String cellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell.getStringCellValue()
					: Cell.getNumericCellValue() + "";
			return cellData;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
			return "";
		}
	}
	
	//
	public static int getLastRowNum() {
		return ExcelSheet.getLastRowNum();
	}
	
	//֪����ȡSheet�е�����������
	public static int getRowCount(String SheetName) {
		ExcelSheet = ExcelWorkbook.getSheet(SheetName);
		int number = ExcelSheet.getLastRowNum();
		return number;
	}
	
	//��Excel��ָ��Sheet�У���ȡ��һ�ΰ���ָ����������������ֵ��к�
	public static int getFirstRowContainsTestCaseID(String sheetName,String testCaseName,int colNum) {
		int i;
		try {
			ExcelSheet = ExcelWorkbook.getSheet(sheetName);
			int rowCont = ExcelUtil.getRowCount(sheetName);
			for (i=0;i<rowCont;i++){
				if (ExcelUtil.getCellData(sheetName, i, colNum).equalsIgnoreCase(testCaseName)) {
					break;
				}	
			}
			//System.out.println(i);
			return i;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			return 0;
		}		
	}
	
	//��ȡָ��Sheet��ĳ��������������ĸ���
	public static int getTestCaseLastStepRow(String sheetName,String testCaseID, int testCaseStartRowNumber) {
		try {
			ExcelSheet = ExcelWorkbook.getSheet(sheetName);
			for (int i=testCaseStartRowNumber;i<=ExcelUtil.getRowCount(sheetName)-1;i++) {
				if(!testCaseID.equals(ExcelUtil.getCellData(sheetName, i, Constans.Col_TestCaseID))) {
					int number = i;
					System.out.println(number);
					return number;		
				}
			}
			int number = ExcelSheet.getLastRowNum()+1;
			return number;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
			return 0;
		}
	}
	
	//��Excel�ļ�ָ����Sheetҳд������
	public static void setCellData(String sheetName,int rowNum, int cellNum, String result) {
		try {
			ExcelSheet = ExcelWorkbook.getSheet(sheetName);
			XSSFCellStyle cellStyle = ExcelWorkbook.createCellStyle();;
			cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //�±߿�
			cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//��߿�
			cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//�ϱ߿�
			cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//�ұ߿�
			Row = ExcelSheet.getRow(rowNum);
			Cell = Row.getCell(cellNum, Row.RETURN_BLANK_AS_NULL);
			
			if(Cell == null) {
				Cell = Row.createCell(cellNum);
				Cell.setCellStyle(cellStyle);
				Cell.setCellValue(result);
			} else {
				Cell.setCellStyle(cellStyle);
				Cell.setCellValue(result);
			}
			FileOutputStream fileOut = new FileOutputStream(Constans.Path_ExcelFile);
			ExcelWorkbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	
	//��Excel�ļ��л�ȡ�������ݵľ�̬����
	public static Object[][] getTestData(String excelFilePath, String sheetName) throws IOException {
		File file = new File(excelFilePath);
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook Workbook = null;
		
		//��ȡ�ļ���չ�����ж�.xlsx����.xls
		String fileExtensionName = excelFilePath.substring(excelFilePath.indexOf("."));
		
		if (fileExtensionName.equals(".xlsx")) {
			Workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			Workbook = new HSSFWorkbook(inputStream);
		}
		
		Sheet Sheet = Workbook.getSheet(sheetName);
		
		//����Excel�ļ����ж�����
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();
		
		List<Object[]> records = new ArrayList<Object[]>();
		
		for (int i=0;i<rowCount;i++) {
			Row row = Sheet.getRow(i);
			String fields[] = new String[row.getLastCellNum()];
			for (int j=0;j<row.getLastCellNum();j++) {
				fields[j] = (String)(row.getCell(j).getCellType() == XSSFCell.CELL_TYPE_STRING ?
						row.getCell(j).getStringCellValue() : 
							"" + row.getCell(j).getNumericCellValue());
			}
			records.add(fields);
		}
		
		Object[][] results = new Object[records.size()][];
		for(int i=0;i<records.size();i++) {
			results[i] = records.get(i);
		}
		
		return results;
			
	}
	
}
