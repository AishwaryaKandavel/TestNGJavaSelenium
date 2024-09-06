package eCommerce.POM;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Cart extends UtilityFunctions {

	WebDriver driver;
	Wait<WebDriver> wait;

	public Cart(WebDriver driver) {
		super(driver);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='cartSection']/h3")
	List<WebElement> productsAdded;

	@FindBy(xpath = "//button[text()='Checkout']")
	WebElement checkOutElem;

	public Payment verifyCart(String products) {
		String[] product = products.split(";");
		List<String> productsTobeAdded = Arrays.asList(product);
		List<String> prods = productsAdded.stream().map(WebElement::getText).collect(Collectors.toList());

		if (prods.size() == productsTobeAdded.size() && prods.size() > 0) {
			for (String prod : prods)
				if (!productsTobeAdded.contains(prod)) {
					Assert.assertTrue(false);
					break;
				}
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
		try {
			moveToElement(checkOutElem);
		} catch (MoveTargetOutOfBoundsException e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", checkOutElem);
			moveToElement(checkOutElem);
		}
		checkOutElem.click();
		return new Payment(driver);
	}
}
