package eCommerce.AbstractComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExcelHandler {
	
	Properties prop;
	public ExcelHandler(Properties prop) {
		this.prop = prop;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<HashMap<Object, Object>> getExcelDataToMap(String filePath)
			throws StreamReadException, DatabindException, IOException, InvalidFormatException {

		File file = new File(System.getProperty("user.dir") + prop.getProperty(filePath));
		XSSFWorkbook wf = new XSSFWorkbook(file);
		int sheetCount = wf.getNumberOfSheets();
		
//		String content = Files.readString(path);
//		ObjectMapper om = new ObjectMapper();
//		List<HashMap<Object, Object>> result = om.readValue(content,
//				new TypeReference<List<HashMap<Object, Object>>>() {
//				});
		return null;
	}
}
