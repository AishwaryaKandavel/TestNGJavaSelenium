package eCommerceApp.Tests;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import eCommerce.AbstractComponents.Reporter;
import eCommerce.POM.*;
import eCommerce.baseClass.InitializeDriver;

public class StandAloneTest extends InitializeDriver{
	WebDriver driver;
	Wait<WebDriver> wait;
	Reporter report;

	@BeforeSuite
	public void initializeDriver() throws IOException {
		driver = initializeBrowser();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		report = new Reporter(driver);
	}

	@AfterSuite
	public void closeDriver() {
		driver.quit();
	}

	@Parameters({ "URL", "emailID", "password", "productList", "country" })
	@Test
	public void E2E(String url, String emailID, String password, String products, String country) {
		Login login = new Login(driver);
		login.goTo(url);
		Products product = login.loginToApp(emailID, password);
		Cart cart = product.addProductsToCart(products);
		Payment payment = cart.verifyCart(products);
		EndPage endPage = payment.checkout(country);
		endPage.captureOrderDetails(products);
	}
}
