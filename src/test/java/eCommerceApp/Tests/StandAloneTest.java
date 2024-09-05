package eCommerceApp.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import eCommerce.AbstractComponents.Reporter;
import eCommerce.POM.*;

public class StandAloneTest {
	WebDriver driver;
	Wait<WebDriver> wait;
	Reporter report;

	@Parameters({ "URL" })
	@BeforeSuite
	public void initializeDriver(String URL) {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		report = new Reporter(driver);
	}

	@AfterSuite
	public void closeDriver() {
		driver.quit();
	}

	@Parameters({ "URL", "emailID", "password" })
	@Test
	public void login(String url, String emailID, String password) {
		Login login = new Login(driver);
		login.goTo(url);
		Assert.assertTrue(login.loginToApp(emailID, password));
	}
	@Parameters({ "productList" })
	@Test(dependsOnMethods = "login")
	public void addToCartAndVerify(String products) {
		Products product = new Products(driver);
		Assert.assertTrue(product.addProductsToCart(products));
	}
	@Parameters({ "productList" })
	@Test(dependsOnMethods = "addToCartAndVerify")
	public void verifyCart(String products) {	
		Cart cart = new Cart(driver);
		Assert.assertTrue(cart.verifyCart(products));
	}
	@Parameters({ "country" })
	@Test(dependsOnMethods = "verifyCart")
	public void checkout(String country) throws InterruptedException {
		Payment payment = new Payment(driver);
		payment.checkout(country);
	}
	@Parameters({ "productList" })
	@Test(dependsOnMethods = "checkout")
	public void captureOrderDetails(String products) {
		List<WebElement> ordersElem = findElements("//label[contains(text(),'|')]");
		String[] productsArr = products.split(";");
		if(ordersElem.size()==productsArr.length)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	public WebElement findAnElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	public List<WebElement> findElements(String locator) {
		return driver.findElements(By.xpath(locator));
	}
}
