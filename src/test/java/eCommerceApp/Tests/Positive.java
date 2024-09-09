package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import eCommerce.POM.*;
import eCommerce.baseClass.InitializeDriver;
import eCommerce.baseClass.RetryFlakyTests;

public class Positive extends InitializeDriver {
	List<String> orderIDs = new ArrayList<String>();
	
	@Parameters({"emailID", "password", "productList", "country" })
	@Test(enabled = true, description = "E2E flow for placing an order", testName = "Place Order", 
	retryAnalyzer = RetryFlakyTests.class)
	public void E2E(String emailID, String password, String products, String country) throws IOException {
		Products product = login.loginToApp(emailID, password);
		Cart cart = product.addProductsToCart(products);
		Payment payment = cart.verifyCart(products);
		EndPage endPage = payment.checkout(country);
		orderIDs = endPage.captureOrderDetails(products);
	}
	
	@Parameters({"emailID", "password", "productList"})
	@Test(enabled = true, dependsOnMethods = "E2E", description = "Verification of the placed order", 
	testName = "Verify Order", retryAnalyzer = RetryFlakyTests.class)
	public void verifyOrders(String emailID, String password, String products) throws IOException {
		Products product = login.loginToApp(emailID, password);
		Orders orders = product.openOrders();
		Assert.assertTrue(orders.verifyOrders(orderIDs));
	}
}
