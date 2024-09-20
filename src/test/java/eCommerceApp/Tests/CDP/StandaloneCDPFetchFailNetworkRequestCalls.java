package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.fetch.Fetch;
import org.openqa.selenium.devtools.v127.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v127.network.model.ErrorReason;
import org.testng.Assert;

public class StandaloneCDPFetchFailNetworkRequestCalls {
	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		RequestPattern reqPattern =  new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty());
		devTools.send(Fetch.enable(Optional.of(Arrays.asList(reqPattern)), Optional.empty()));
		
		devTools.addListener(Fetch.requestPaused(), request -> {
			devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
		});

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.xpath("//button[normalize-space(text())='Virtual Library']")).click();
		Thread.sleep(3000);
		Assert.assertTrue(
				driver.findElements(By.xpath("//table//tr//td")).isEmpty());
		driver.quit();
	}
}
