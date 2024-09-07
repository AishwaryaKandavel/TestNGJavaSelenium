package eCommerce.POM;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Login extends UtilityFunctions{
	WebDriver driver;
	
	public Login(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//input[@id='userEmail']")
	WebElement userEmail;
	
	@FindBy(xpath="//input[@id='userPassword']")
	WebElement userPassword;
	
	@FindBy(xpath="//input[@id='login']")
	WebElement login;
	
	@FindBy(xpath="//a[text()='Register here']")
	WebElement registerHere;
	
	@FindBy(css=".invalid-feedback")
	List<WebElement> errorMsgs;
	
	@FindBy(css=".forgot-password-link")
	WebElement forgotPwd;
	
	@FindBy(css="button[type='submit']")
	WebElement submitBtn;
	
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

	public void loginErrorMsgValidation() {
		waitForVisibilityOfElement(errorMsg);
		Assert.assertTrue(true);
	}
	
	public boolean registerErrorMsgValidation(List<String> errorMsgValuesExpected) {
		registerHere.click();
		login.click();
		
		List<String> errorMsgValuesActual = errorMsgs.stream().map(e -> e.getText()).collect(Collectors.toList());
		return errorMsgValuesActual.equals(errorMsgValuesExpected)?true:false;
	}
	public boolean forgotPwdErrorMsgValidation(List<String> errorMsgValuesExpected) {
		forgotPwd.click();
		submitBtn.click();
		
		List<String> errorMsgValuesActual = errorMsgs.stream().map(e -> e.getText()).collect(Collectors.toList());
		return errorMsgValuesActual.equals(errorMsgValuesExpected)?true:false;
	}	
}
