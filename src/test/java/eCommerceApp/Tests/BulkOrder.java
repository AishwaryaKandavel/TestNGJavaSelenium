package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import eCommerce.POM.*;
import eCommerce.baseClass.InitializeDriver;
import eCommerce.baseClass.RetryFlakyTests;

public class BulkOrder extends InitializeDriver {
	List<String> orderIDs = new ArrayList<String>();

	@Test(enabled = true, dataProvider = "getData", groups = "bulkOrder", description = "Complete E2E bulk orders "
			+ "flow and verification of the same", testName = "E2E Order - Bulk", retryAnalyzer = RetryFlakyTests.class)
	public void E2E_BulkOrder(HashMap<Object, Object> data) throws IOException {
		String emailID = (String) data.get("emailID");
		String password = (String) data.get("password");
		String products = (String) data.get("products");
		String country = (String) data.get("country");

		Products product = login.loginToApp(emailID, password);
		Cart cart = product.addProductsToCart(products);
		Payment payment = cart.verifyCart(products);
		EndPage endPage = payment.checkout(country);
		orderIDs = endPage.captureOrderDetails(products);
		Orders orders = product.openOrders();
		Assert.assertTrue(orders.verifyOrders(orderIDs));
	}

	@DataProvider
	public Object[][] getData() throws StreamReadException, DatabindException, IOException {
		
		List<HashMap<Object, Object>> data = jsonHandler.getJSONDataToMap("bulkOrderData");
		return IntStream.range(0, data.size()).mapToObj(i->new Object[] {data.get(i)}).toArray(Object[][]::new);
	}
}
