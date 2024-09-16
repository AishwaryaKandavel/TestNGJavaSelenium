package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import eCommerce.POM.*;
import eCommerce.baseClass.InitializeDriver;
import eCommerce.baseClass.RetryFlakyTests;

public class BulkOrderUsingExcel extends InitializeDriver {
	List<String> orderIDs = new ArrayList<String>();

	@Test(enabled = true, dataProvider = "getDataFromExcel", groups = "bulkOrderUsingExcel", 
			description = "Complete E2E bulk orders "
			+ "flow and verification of the same", retryAnalyzer = RetryFlakyTests.class)
	public void E2E_BulkOrder(HashMap<Object, Object> data) throws IOException {
		String emailID = (String) data.get("emailID");
		String password = (String) data.get("password");
		String products = (String) data.get("products");
		String country = (String) data.get("country");
		double tcno = (double) data.get("tcno");
		System.out.println("Test Case "+tcno);

		Products product = login.loginToApp(emailID, password);
		Cart cart = product.addProductsToCart(products);
		Payment payment = cart.verifyCart(products);
		EndPage endPage = payment.checkout(country);
		orderIDs = endPage.captureOrderDetails(products);
		Orders orders = product.openOrders();
		Assert.assertTrue(orders.verifyOrders(orderIDs));
	}
	
	@DataProvider
	public Object[][] getDataFromExcel() throws IOException, InvalidFormatException {
		
		List<HashMap<Object, Object>> data = excelHandler.getExcelDataToMap("bulkOrder");
		return IntStream.range(0, data.size()).mapToObj(i->new Object[] {data.get(i)}).toArray(Object[][]::new);
	}	
	
}
