package eCommerce.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Header extends UtilityFunctions{
	
	WebDriver driver;
	
	public Header(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[contains(text(),'ORDERS')]")
	WebElement orders;
	
	@FindBy(xpath="//button[contains(text(),'Cart')]")
	WebElement cart;
	
	@FindBy(xpath="//button[contains(text(),'HOME')]")
	WebElement home;
	
	@FindBy(xpath="//button[contains(text(),'Sign Out')]")
	WebElement signout;
	
	public Orders openOrders() {
		orders.click();
		return new Orders(driver);
	}
}
