package eCommerce.POM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Cart extends UtilityFunctions {

	WebDriver driver;

	public Cart(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='cartSection']/h3")
	private List<WebElement> productsAdded;

	@FindBy(xpath = "//button[text()='Checkout']")
	private WebElement checkOutElem;

	public Payment verifyCart(String products) {
		String[] product = products.split(";");
		List<String> productsTobeAdded = Arrays.asList(product);
		List<String> prods = productsAdded.stream().map(WebElement::getText).collect(Collectors.toList());

		if (prods.equals(productsTobeAdded))
			Assert.assertTrue(true);
		else
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
