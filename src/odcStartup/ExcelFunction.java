package odcStartup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


//Class to handle the loading of data from excel. If 
public class ExcelFunction {

	private XSSFWorkbook eWorkbook;
	private XSSFSheet eSheet;


	public void loadExcelFile(String filePath) throws Exception {
		//open file stream
	
		try {
			FileInputStream excelFile = new FileInputStream(filePath);	
			eWorkbook = new XSSFWorkbook(excelFile);
			
				
		} catch (FileNotFoundException e) {
		
			
		throw(e);
			
			}
	}
	
	public void loadExcelSheet(String sheetName) {
		
		try {
			eSheet = eWorkbook.getSheet(sheetName);
			
		}
		catch (Exception e) {
			System.out.println("fail to load data");
			throw(e);
			
		}
	}
	
	//return data in a list
	public List<String> getTestData(String SheetName){
	
		String cellData = "";
		
		try {
			loadExcelSheet(SheetName);
			
			int size = eSheet.getLastRowNum()+1;
			
			List<String> testData = new ArrayList<String>();
			for (int i = 0; i<size; i++)
			{
			
				cellData =String.valueOf(eSheet.getRow(i).getCell(0).getStringCellValue());
				cellData= cellData.trim();
				testData.add(cellData);
				
			}
			return testData;
		}
		catch(Exception e){
			e.printStackTrace();
			throw (e);
		}
		
	}
	
	public static XSSFCell[] findCells(String tableName) {
		DataFormatter formatter = new DataFormatter();
		String pos = "begin";
		XSSFCell[] cells = new XSSFCell[2];
		return null;
		
	}
}