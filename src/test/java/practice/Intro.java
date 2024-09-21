package practice;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Intro {

	public static void main(String[] args) {
		//Ignore in new version Selenium 4.6.0 - SeleniumManager downloads the driver for us. 
		//If below line is added, SeleniumManageer is not invoked
		//System.setProperty("webdriver.chrome.driver", "C:\\Automation\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
		driver.get(url);
		String CurrentUrl = driver.getCurrentUrl();
		if(url.equalsIgnoreCase(CurrentUrl))
			System.out.println(CurrentUrl);
		else
			System.out.println(CurrentUrl);
		
		String title = driver.getTitle();
		if(title.equalsIgnoreCase("GreenKart - veg and fruits kart"))
			System.out.println(title);
		else
			System.out.println(title);
		driver.close();
		driver.quit();
	}

}
