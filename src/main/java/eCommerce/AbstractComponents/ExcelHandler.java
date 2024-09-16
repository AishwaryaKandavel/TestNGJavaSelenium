package eCommerce.AbstractComponents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler {
	
	Properties prop;
	public ExcelHandler(Properties prop) {
		this.prop = prop;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<HashMap<Object, Object>> getExcelDataToMap(String sheetName)
			throws IOException, InvalidFormatException {
		
		List<HashMap<Object, Object>> result = new ArrayList<HashMap<Object,Object>>();
		File file = new File(System.getProperty("user.dir") + prop.getProperty("excelData"));
		XSSFWorkbook wf = new XSSFWorkbook(file);
		XSSFSheet sheet = wf.getSheet(sheetName);
		
		Iterator<Row> rows = sheet.iterator();
		Row headerRow = rows.next();
		while(rows.hasNext()) {
			HashMap<Object, Object> keyValCols = new HashMap<Object, Object>();
			Row row = rows.next();
			for (int i=0; i<row.getPhysicalNumberOfCells();i++) {
				Object headerCell = headerRow.getCell(i).getStringCellValue();
				Object rowCell;
				try{
					rowCell = row.getCell(i).getStringCellValue();
				}catch(Exception e) {
					rowCell = row.getCell(i).getNumericCellValue();
				}
				keyValCols.put(headerCell, rowCell);
			}
			result.add(keyValCols);
		}
		wf.close();
		return result;
	}
}
