package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.network.Network;

import com.google.common.collect.ImmutableList;

public class StandaloneCDPBlockNetworkRequests {
	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		ImmutableList<String> urls = ImmutableList.of("*.jpg", "*.css", ".png");
		devTools.send(Network.setBlockedURLs(urls));

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		driver.quit();
	}
}
