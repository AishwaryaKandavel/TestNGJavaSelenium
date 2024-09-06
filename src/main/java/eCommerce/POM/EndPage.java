package eCommerce.POM;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class EndPage extends UtilityFunctions{
	
	WebDriver driver;
	Wait<WebDriver> wait;
	
	public EndPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//label[contains(text(),'|')]")
	List<WebElement> ordersElem;
	
	@FindBy(xpath="//button[text()='Click To Download Order Details in CSV']")
	WebElement downloadCSV;
	
	@FindBy(xpath="//button[text()='Click To Download Order Details in Excel']")
	WebElement downloadExcel;
	
	public void captureOrderDetails(String products) {
		String[] productsArr = products.split(";");
		if(ordersElem.size()==productsArr.length)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
		waitForElementToBeClickable(downloadCSV);
		downloadCSV.click();
		waitForElementToBeClickable(downloadExcel);
		downloadExcel.click();
	}
}
