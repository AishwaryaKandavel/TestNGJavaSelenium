package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
//import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
//import org.openqa.selenium.devtools.v127.emulation.Emulation;
import org.testng.Assert;

public class StandaloneCDPEmulationMobileTests {
	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();

		/*
		 * devTools.send(Emulation.setDeviceMetricsOverride(430, 932, 50, true,
		 * Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
		 * Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
		 * Optional.empty(), Optional.empty()));
		 */

		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("width", 430);
		arguments.put("height", 932);
		arguments.put("deviceScaleFactor", 50);
		arguments.put("mobile", true);
		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", arguments);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector(".navbar-toggler-icon")).click();
		driver.findElement(By.linkText("Library")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//h2[text()='Books Availability in Rahul Shetty Academy Library']"))
						.isDisplayed());
		driver.quit();
	}
}
