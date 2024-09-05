package eCommerce.POM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Products extends UtilityFunctions{
	WebDriver driver;
	Wait<WebDriver> wait;
	
	public Products(WebDriver driver) {
		super(driver);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}
	
	By loadingScreen = By.xpath("/div[@id='toast-container']");
	By continueShopping = By.xpath("//button[text()='Continue Shopping']");
	
	@FindBy(xpath="//button[contains(text(),'Cart')]")
	WebElement cartElem;
	
	public By setProductValueToLocator(String product) {
		String productToAddLoc = "//b[normalize-space(text())='" + product
		+ "']/parent::*/following-sibling::button[normalize-space(text())='Add To Cart']";
		By productToAdd = By.xpath(productToAddLoc);
		return productToAdd;
	}
	
	public boolean addProductsToCart(String products) {
		
		waitForInvisibilityOfElement(loadingScreen);
		String[] product = products.split(";");
		for (String prod : product) {
			WebElement productElem = waitForElementToBeClickable(setProductValueToLocator(prod));
			productElem.click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/div[@id='toast-container']")));
//			waitForVisibilityOfElement("/div[@id='toast-container']");
			fluentWaitIgnoringTimeoutForInvisibility(loadingScreen);
		}
		cartElem.click();
		return true;
	}
}
