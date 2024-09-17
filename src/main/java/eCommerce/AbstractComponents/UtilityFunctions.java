package eCommerce.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtilityFunctions {
	WebDriver driver;
	Wait<WebDriver> wait;
	Wait<WebDriver> fWait;

	public UtilityFunctions(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		fWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
	}

	public WebElement findAnElement(By by) {
		return driver.findElement(by);
	}

	public WebElement waitForElementToBeClickable(By by) {
		return wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public WebElement waitForElementToBeClickable(WebElement elem) {
		return wait.until(ExpectedConditions.elementToBeClickable(elem));
	}

	public WebElement waitForVisibilityOfElement(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public List<WebElement> waitForVisibilityOfAllElement(By by) {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	public Boolean waitForInvisibilityOfElement(By by) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public WebElement waitForVisibilityOfElement(String locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public void moveToElement(WebElement elem) {
		Actions actions = new Actions(driver);
		actions.moveToElement(elem);
		actions.perform();
	}

	public void fluentWaitForVisibility(String loc) {
		fWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc)));
	}

	public void fluentWaitInvisibility(By by) {
		fWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
}
