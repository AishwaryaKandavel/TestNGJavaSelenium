package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import eCommerce.baseClass.InitializeDriver;

public class Negative extends InitializeDriver {
	@Parameters({"emailID" })
	@Test(groups = {"errorHandling"})
	public void loginErrorValidation(String emailID) {
		login.loginToApp(emailID, "incorrect");
		login.loginErrorMsgValidation();
	}

	@Test(groups = {"errorHandling"})
	public void registerPageError() throws IOException {
		List<String> errorMsgValuesExpected = new ArrayList<String>();
		errorMsgValuesExpected.add("*First Name is required");
		errorMsgValuesExpected.add("*Email is required");
		errorMsgValuesExpected.add("*Phone Number is required");
		errorMsgValuesExpected.add("*Password is required");
		errorMsgValuesExpected.add("Confirm Password is required");
		
		Assert.assertTrue(login.registerErrorMsgValidation(errorMsgValuesExpected));
	}
	
	@Test(groups = {"errorHandling"})
	public void forgotPwdPageError() throws IOException {
		List<String> errorMsgValuesExpected = new ArrayList<String>();
		errorMsgValuesExpected.add("*Email is required");
		errorMsgValuesExpected.add("*Password is required");
		errorMsgValuesExpected.add("*Confirm Password is required");
		
		Assert.assertTrue(login.forgotPwdErrorMsgValidation(errorMsgValuesExpected));
	}
}
