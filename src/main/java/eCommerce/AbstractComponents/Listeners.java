package eCommerce.AbstractComponents;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.UnknownHostException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listeners implements ITestListener {
	
	WebDriver driver;
	ExtentTest test;
	ExtentReports report;
	ThreadLocal<ExtentTest> thread = new ThreadLocal<ExtentTest>();
	
	public Listeners() {
		try {
			report = Reporter.getReportObject();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ITestListener.super.isEnabled();
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		test = Reporter.extentReports.createTest(result.getMethod().getMethodName());
		thread.set(test);
		try {
			Class<?> currClass = result.getTestClass().getRealClass();
			Class<?> superClass = currClass.getSuperclass();
			Field field = superClass.getDeclaredField("driver");
			driver = (WebDriver) field.get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		ExtentTest t = thread.get();
		t.log(Status.PASS, result.getMethod().getDescription());
		String filePath = "";
		try {
			filePath = Reporter.report(result.getMethod().getDescription(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.addScreenCaptureFromPath(filePath, result.getMethod().getDescription());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		ExtentTest t = thread.get();
		String filePath = "";
		t.log(Status.FAIL, result.getMethod().getDescription());
		t.fail(result.getThrowable());
		try {
			filePath = Reporter.report(result.getMethod().getDescription(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.addScreenCaptureFromPath(filePath, result.getMethod().getDescription());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);	
		ExtentTest t = thread.get();
		String filePath = "";
		t.log(Status.SKIP, result.getMethod().getDescription());
		t.skip(result.getThrowable());
		try {
			filePath = Reporter.report(result.getMethod().getDescription(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.addScreenCaptureFromPath(filePath, result.getMethod().getDescription());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		report.flush();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
