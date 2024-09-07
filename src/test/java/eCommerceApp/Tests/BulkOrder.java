package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
//import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import eCommerce.POM.*;
import eCommerce.baseClass.InitializeDriver;

public class BulkOrder extends InitializeDriver {
	List<String> orderIDs = new ArrayList<String>();

//	@Parameters({"emailID", "password", "productList", "country" })
	@Test(enabled = true, dataProvider = "getData", groups = "bulkOrder")
	public void E2E(HashMap<Object, Object> data) throws IOException {
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
	public Object[][] getData() {
		HashMap<Object, Object> data1 = new HashMap<Object, Object>();
		data1.put("emailID", "henrycavillpookie@gmail.com");
		data1.put("password", "RahulShetty@1295");
		data1.put("products", "ZARA COAT 3;IPHONE 13 PRO");
		data1.put("country", "India");

		HashMap<Object, Object> data2 = new HashMap<Object, Object>();
		data2.put("emailID", "hughjackmanpookie@gmail.com");
		data2.put("password", "RahulShetty@1235");
		data2.put("products", "ADIDAS ORIGINAL;ZARA COAT 3");
		data2.put("country", "Ireland");

		return new Object[][] { { data1 }, { data2 } };
	}
}
