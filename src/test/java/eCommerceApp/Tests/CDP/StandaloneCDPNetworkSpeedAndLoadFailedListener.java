package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.network.Network;
import org.openqa.selenium.devtools.v127.network.model.ConnectionType;
import org.testng.Assert;

public class StandaloneCDPNetworkSpeedAndLoadFailedListener {
	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.send(Network.emulateNetworkConditions(true, 3000, 20000, 10000, 
				Optional.of(ConnectionType.CELLULAR2G), Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.addListener(Network.loadingFailed(), loadingfailed ->{
			System.out.println(loadingfailed.getErrorText());
			System.out.println(loadingfailed.getTimestamp());
		});

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.xpath("//button[normalize-space(text())='Virtual Library']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//h2[text()='Books Availability in Rahul Shetty Academy Library']"))
						.isDisplayed());
		driver.quit();
	}
}
