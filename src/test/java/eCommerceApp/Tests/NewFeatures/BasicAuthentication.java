package eCommerceApp.Tests.NewFeatures;

import java.net.URI;
import java.time.Duration;
import java.util.function.Predicate;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicAuthentication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChromeDriver driver = new ChromeDriver();
		
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");
		((HasAuthentication)driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://httpbin.org/basic-auth/foo/bar");
		driver.quit();
	}
}
