package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.network.Network;
import org.openqa.selenium.devtools.v127.network.model.Request;
import org.openqa.selenium.devtools.v127.network.model.Response;
import org.testng.Assert;

public class StandaloneCDPNetworkResponseAndStatusCodes {
	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.addListener(Network.requestWillBeSent(), request -> {
			Request req = request.getRequest();
			System.out.println("Request: "+req.getUrl());
		});
		
		devTools.addListener(Network.responseReceived(), response -> {
			Response res = response.getResponse();
			if(res.getStatus().toString().startsWith("4"))
				System.out.println("Response: "+res.getUrl()+": "+res.getStatus());
		});

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.xpath("//button[normalize-space(text())='Virtual Library']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//h2[text()='Books Availability in Rahul Shetty Academy Library']"))
						.isDisplayed());
		driver.quit();
	}
}
