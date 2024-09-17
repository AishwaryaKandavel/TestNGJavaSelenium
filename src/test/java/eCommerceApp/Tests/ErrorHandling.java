package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import eCommerce.baseClass.InitializeDriver;
import eCommerce.baseClass.RetryFlakyTests;

public class ErrorHandling extends InitializeDriver {
	@Parameters({ "emailID" })
	@Test(groups = {"errorHandling" }, description = "Error validation for the login page", 
			testName = "Login Error Validation", dataProvider = "getData", retryAnalyzer = RetryFlakyTests.class)
	public void loginErrorValidation(HashMap<Object, Object> data) {
		String emailID = (String) data.get("emailID");
		String password = (String) data.get("password");
		login.loginToApp(emailID, password);
		login.loginErrorMsgValidation();
	}

	@Test(groups = {"errorHandling" }, description = "Error validation for the register page", 
			testName = "Registration Error Validation", retryAnalyzer = RetryFlakyTests.class)
	public void registerPageError() throws IOException {
		List<String> errorMsgValuesExpected = new ArrayList<String>();
		errorMsgValuesExpected.add("*First Name is required");
		errorMsgValuesExpected.add("*Email is required");
		errorMsgValuesExpected.add("*Phone Number is required");
		errorMsgValuesExpected.add("*Password is required");
		errorMsgValuesExpected.add("Confirm Password is required");

		Assert.assertTrue(login.registerErrorMsgValidation(errorMsgValuesExpected));
	}

	@Test(groups = { "errorHandling" }, description = "Error validation for the forgot password page",
			testName = "Forgot Password Error Validation", retryAnalyzer = RetryFlakyTests.class)
	public void forgotPwdPageError() throws IOException {
		List<String> errorMsgValuesExpected = new ArrayList<String>();
		errorMsgValuesExpected.add("*Email is required");
		errorMsgValuesExpected.add("*Password is required");
		errorMsgValuesExpected.add("*Confirm Password is required");

		Assert.assertTrue(login.forgotPwdErrorMsgValidation(errorMsgValuesExpected));
	}
	@DataProvider
	public Object[][] getData() throws StreamReadException, DatabindException, IOException {
		List<HashMap<Object,Object>> data= jsonHandler.getJSONDataToMap("credentialsData");
		return IntStream.range(0, data.size()).mapToObj(o->new Object[] {data.get(o)}).toArray(Object[][]::new);
	}
}
