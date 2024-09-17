package eCommerce.POM;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import eCommerce.AbstractComponents.ExcelHandler;
import eCommerce.AbstractComponents.TableHandler;
import eCommerce.AbstractComponents.UtilityFunctions;

public class PriceList extends UtilityFunctions{
	WebDriver driver;
	String filePath = "";
	List<HashMap<Object, Object>> tableInExcel;
	List<HashMap<Object, Object>> tableInUi;
	ExcelHandler excelHandler;
	
	public PriceList(WebDriver driver, ExcelHandler excelHandler) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.excelHandler = excelHandler;
	}
	@FindBy(xpath="//div[@role='columnheader']/div")
	private List<WebElement> columnHeaderRowCells;
	
	@FindBy(xpath="//div[@role='row' and not(contains(@class,'HeadRow'))]")
	private List<WebElement> AllRows;
	
	@FindBy(id="downloadButton")
	private WebElement download;
	
	@FindBy(id="fileinput")
	private WebElement chooseFile;
	
	private By successMsg = By.xpath("//div[text()='Updated Excel Data Successfully.']");
	
	private By cells = By.xpath("//div[text()]");
	
	public void downloadAndUpdateFile(String columnToUpdate, String key, String keyColumn, String updatedValue) 
			throws Exception {
		download.click();
		Thread.sleep(3000);
		filePath = excelHandler.updateValueInExcel(columnToUpdate, key, keyColumn, updatedValue);
		tableInExcel = excelHandler.getExcelDataToMap(filePath, "0");
	}
	
	public void uploadAndVerifyPage() throws InvalidFormatException, IOException {
		chooseFile.sendKeys(filePath);
		Assert.assertTrue(waitForVisibilityOfElement(successMsg).isDisplayed());
		tableInUi = TableHandler.geTableSDataToMap(columnHeaderRowCells, AllRows, cells);
		boolean flag = false;
		if(tableInUi.size()==tableInExcel.size()) {
			for (int i = 0; i<tableInUi.size(); i++) {
				HashMap<Object, Object> excel = tableInExcel.get(i);
				HashMap<Object, Object> ui = tableInUi.get(i);
				
				if(!excel.entrySet().stream().allMatch(e->e.getValue().toString().equals(ui.get(e.getKey()).toString()))) {
					System.out.println(tableInExcel.get(i)+"\n"+tableInUi.get(i));
					Assert.assertTrue(false);
				}
				flag = true;
			}
		}else 
			Assert.assertTrue(false);
		if(flag)
			Assert.assertTrue(flag);
	}
}
