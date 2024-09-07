package eCommerceApp.Tests;

import java.io.IOException;
import java.util.ArrayList;
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
	public void E2E(String emailID, String password, String products, String country) throws IOException {
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
		return new Object[][] 
				{{"henrycavillpookie@gmail.com", "RahulShetty@1295", "ZARA COAT 3;IPHONE 13 PRO", "India"},
			{"hughjackmanpookie@gmail.com","RahulShetty@1235","ADIDAS ORIGINAL;ZARA COAT 3","Ireland"}};
	}
}
