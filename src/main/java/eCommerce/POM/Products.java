package eCommerce.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Products extends UtilityFunctions {
	WebDriver driver;

	public Products(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private By loadingScreen = By.cssSelector(".ng-animating");

	@FindBy(xpath = "//button[contains(text(),'Cart')]")
	private WebElement cartElem;

	public By setProductValueToLocator(String product) {
		String productToAddLoc = "//b[normalize-space(text())='" + product
				+ "']/parent::*/following-sibling::button[normalize-space(text())='Add To Cart']";
		By productToAdd = By.xpath(productToAddLoc);
		return productToAdd;
	}

	public Cart addProductsToCart(String products) {

		waitForInvisibilityOfElement(loadingScreen);
		String[] product = products.split(";");
		for (String prod : product) {
			WebElement productElem = waitForElementToBeClickable(setProductValueToLocator(prod));
			productElem.click();
			waitForVisibilityOfElement(loadingScreen);
			waitForInvisibilityOfElement(loadingScreen);
		}
		cartElem.click();
		Assert.assertTrue(true);
		return new Cart(driver);
	}

	public Orders openOrders() {
		Header header = new Header(driver);
		return header.openOrders();
	}
}
