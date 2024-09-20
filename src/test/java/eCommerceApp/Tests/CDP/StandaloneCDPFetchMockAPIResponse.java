package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.fetch.Fetch;
import org.openqa.selenium.devtools.v127.network.model.Request;
import org.testng.Assert;

public class StandaloneCDPFetchMockAPIResponse {
	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		
		devTools.addListener(Fetch.requestPaused(), request -> {
			Request req = request.getRequest();
			if(req.getUrl().contains("=shetty")) {
				String mockedURL = req.getUrl().replaceAll("=shetty", "=BadGuy");
				System.out.println("New URL: "+mockedURL);
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedURL), 
						Optional.of(req.getMethod()), Optional.empty(), Optional.empty(), Optional.empty()));
			}else {
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(req.getUrl()), 
						Optional.of(req.getMethod()), Optional.empty(), Optional.empty(), Optional.empty()));
			}
		});

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.xpath("//button[normalize-space(text())='Virtual Library']")).click();
		Thread.sleep(3000);
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[text()='Oops only 1 Book available']"))
						.isDisplayed());
		driver.quit();
	}
}
