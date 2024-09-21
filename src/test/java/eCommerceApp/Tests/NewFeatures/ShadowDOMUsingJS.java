package eCommerceApp.Tests.NewFeatures;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ShadowDOMUsingJS {
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
			String url = "http://watir.com/examples/shadow_dom.html";
			driver.get(url);
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement elem = (WebElement) jse.executeScript
					("return document.querySelector('div#shadow_host').shadowRoot.querySelector('span.info')");
			
			System.out.println(elem.getText());
			
			elem = (WebElement) jse.executeScript
					("return document.querySelector('div#shadow_host')"
							+ ".shadowRoot.querySelector('div#nested_shadow_host')"
							+ ".shadowRoot.querySelector('div#nested_shadow_content > div')");
			
			System.out.println(elem.getText());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			driver.close();
			driver.quit();
		}		
	}
}
