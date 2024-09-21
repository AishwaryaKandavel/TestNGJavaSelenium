package eCommerceApp.Tests.NewFeatures;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ShadowDOMUsingSearchContext {
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		String url = "http://watir.com/examples/shadow_dom.html";
		driver.get(url);

		WebElement shadowHost = driver.findElement(By.id("shadow_host"));
		SearchContext shadowRoot = shadowHost.getShadowRoot();

		String text = shadowRoot.findElement(By.cssSelector("#shadow_content > span")).getText();
		System.out.println(text);

		WebElement nestedShadowHost = shadowRoot.findElement(By.cssSelector("#nested_shadow_host"));
		SearchContext shadowNestedRoot = nestedShadowHost.getShadowRoot();

		text = shadowNestedRoot.findElement(By.cssSelector("#nested_shadow_content > div")).getText();
		System.out.println(text);

		driver.close();
		driver.quit();
	}
}
