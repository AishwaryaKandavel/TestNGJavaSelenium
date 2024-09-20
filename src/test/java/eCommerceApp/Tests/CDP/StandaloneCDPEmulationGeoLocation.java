package eCommerceApp.Tests.CDP;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.ui.Select;

public class StandaloneCDPEmulationGeoLocation {
	public static void main(String[] args) throws InterruptedException {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=/path/to/your/custom/profile");
		ChromeDriver driver = new ChromeDriver(options);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();

		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("latitude", 46);
		arguments.put("longitude", 2);
		arguments.put("accuracy", 1);
		driver.executeCdpCommand("Emulation.setGeolocationOverride", arguments);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://google.com");
		driver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
		driver.findElements(By.cssSelector(".LC20lb")).get(0).click();
		WebElement language = driver.findElement(By.xpath("//select[@name='LanguageSelect']"));
		String languageAvaialble = language.findElements(By.xpath("//option")).get(1).getText();
		System.out.println(languageAvaialble);
		Thread.sleep(3000);
		Select lang = new Select(language);
		//below code resets the location - need to find a way to fix it
		lang.selectByVisibleText(languageAvaialble);
//		driver.executeCdpCommand("Emulation.setGeolocationOverride", arguments);
		driver.quit();
	}
}
