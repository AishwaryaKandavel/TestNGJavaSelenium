package eCommerce.AbstractComponents;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reporter {

	static ExtentReports extentReports;

	public static String report(String testCaseName, WebDriver driver) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destPath = System.getProperty("user.dir") 
				+ "/target/screenshot/"+testCaseName+System.currentTimeMillis()+".png";
		FileUtils.copyFile(srcFile, new File(destPath));
		return destPath;
	}
	
	public static ExtentReports getReportObject() throws UnknownHostException {
		
		String path = System.getProperty("user.dir")+"/extentReports/report_"+System.currentTimeMillis()+".html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Report");
		
		extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester", InetAddress.getLocalHost().getHostName());
		return extentReports;
	}
}
