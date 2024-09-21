package eCommerceApp.Tests.NewFeatures.basicAuthentication;

import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicAuthenticationUsingSeleniumShortcut {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChromeDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://foo:bar@httpbin.org/basic-auth/foo/bar");
		driver.quit();
	}
}
