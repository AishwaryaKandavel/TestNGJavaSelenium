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

	public String report(String testCaseName) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destPath = System.getProperty("user.dir") 
				+ "/target/screenshot/"+testCaseName+System.currentTimeMillis()+".png";
		FileUtils.copyFile(srcFile, new File(destPath));
		return destPath;
	}
}
