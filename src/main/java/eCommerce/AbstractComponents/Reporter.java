package eCommerce.AbstractComponents;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Reporter {

	WebDriver driver;

	public Reporter(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}

	public void report() throws IOException {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("/SeleniumJavaFrameworkECommerceApp/target"));
	}
}
