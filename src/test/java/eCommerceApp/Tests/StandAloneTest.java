package eCommerceApp.Tests;

import java.io.IOException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import eCommerce.POM.*;
import eCommerce.baseClass.InitializeDriver;

public class StandAloneTest extends InitializeDriver{

	@Parameters({ "URL", "emailID", "password", "productList", "country" })
	@Test
	public void E2E(String url, String emailID, String password, String products, String country) throws IOException {
		login.goTo(url);
		Products product = login.loginToApp(emailID, password);
		Cart cart = product.addProductsToCart(products);
		Payment payment = cart.verifyCart(products);
		EndPage endPage = payment.checkout(country);
		endPage.captureOrderDetails(products);
	}
	@Parameters({ "URL", "emailID"})
	@Test
	public void ErrorValidationLogin(String url, String emailID) {
		login.goTo(url);
		login.loginToApp(emailID, "incorrect");
		login.errorMsgValidation();
	}
}
