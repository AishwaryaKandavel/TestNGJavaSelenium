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
	private WebElement orders;
	
	@FindBy(xpath="//button[contains(text(),'Cart')]")
	private WebElement cart;
	
	@FindBy(xpath="//button[contains(text(),'HOME')]")
	private WebElement home;
	
	@FindBy(xpath="//button[contains(text(),'Sign Out')]")
	private WebElement signout;
	
	public Orders openOrders() {
		orders.click();
		return new Orders(driver);
	}
}
