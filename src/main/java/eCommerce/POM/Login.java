package eCommerce.POM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Login extends UtilityFunctions{
	WebDriver driver;
	Wait<WebDriver> wait;
	
	public Login(WebDriver driver) {
		super(driver);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//input[@id='userEmail']")
	WebElement userEmail;
	
	@FindBy(xpath="//input[@id='userPassword']")
	WebElement userPassword;
	
	@FindBy(xpath="//input[@id='login']")
	WebElement login;
	
	By errorMsg = By.xpath("//*[normalize-space(text())='Incorrect email or password.']");
	
	public Products loginToApp(String emailID, String pwd) {
		userEmail.sendKeys(emailID);
		userPassword.sendKeys(pwd);
		login.click();
		return new Products(driver);
	}
	public void goTo(String URL) {
		driver.get(URL);
	}
	public void errorMsgValidation() {
		waitForVisibilityOfElement(errorMsg);
		Assert.assertTrue(true);
	}
}
