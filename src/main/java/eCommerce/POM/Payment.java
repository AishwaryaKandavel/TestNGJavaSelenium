package eCommerce.POM;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Payment extends UtilityFunctions {
	WebDriver driver;

	public Payment(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@placeholder='Select Country']")
	private WebElement countryElem;

	@FindBy(xpath = "//a[normalize-space(text())='Place Order']")
	private WebElement placeOrder;

	By confirmationMsgBy = By.xpath("//*[normalize-space(text())='Thankyou for the order.']");

	public By setCountryValueToLocator(String country) {
		String countryLoc = "//span[normalize-space(text())='" + country + "']";
		By countryBy = By.xpath(countryLoc);
		return countryBy;
	}

	public EndPage checkout(String country) {
		waitForElementToBeClickable(countryElem).sendKeys(country);
		waitForElementToBeClickable(setCountryValueToLocator(country)).click();
		placeOrder.click();

		List<WebElement> confirmationMsg = waitForVisibilityOfAllElement(confirmationMsgBy);
		Assert.assertTrue(confirmationMsg.size() > 0);
		return new EndPage(driver);
	}
}
