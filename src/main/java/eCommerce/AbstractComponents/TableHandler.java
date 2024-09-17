package eCommerce.AbstractComponents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TableHandler {
	
	WebDriver driver;
	
	public TableHandler(WebDriver driver) {
		this.driver = driver;
	}

	public static List<HashMap<Object, Object>> geTableSDataToMap(List<WebElement> headerCells, 
			List<WebElement> rows, By cells)
			throws IOException, InvalidFormatException {
		
		List<HashMap<Object, Object>> result = new ArrayList<HashMap<Object,Object>>();
		
		for(int j=0; j<rows.size();j++) {
			HashMap<Object, Object> keyValCols = new HashMap<Object, Object>();
			String[] rowsCells = rows.get(j).getText().split("\n");
			for (int i=0; i<headerCells.size();i++) {
				String temp = headerCells.get(i).getText().toLowerCase().replaceAll("s no", "sno");
				Object headerCell = temp.replaceAll(" ", "_");
				Object rowCell = rowsCells[i];
				keyValCols.put(headerCell, rowCell);
			}
			result.add(keyValCols);
		}
		return result;
	}
}
