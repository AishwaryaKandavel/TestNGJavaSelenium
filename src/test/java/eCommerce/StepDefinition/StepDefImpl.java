package eCommerce.StepDefinition;

import java.io.IOException;
import java.util.List;

import eCommerce.POM.Cart;
import eCommerce.POM.EndPage;
import eCommerce.POM.Login;
import eCommerce.POM.Payment;
import eCommerce.POM.Products;
import eCommerce.baseClass.InitializeDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefImpl{
	
	Login login;
	Products product;
	Cart cart;
	Payment payment;
	EndPage endPage;
	InitializeDriver id;
	
	@Given("I launch the application")
	public void i_launch_the_application() throws IOException {
		id = new InitializeDriver();
		id.launchApp();
		this.login = id.login;
	}
	@Given("^I login to the app with (.+) and (.+)$")
	public void i_login_to_the_app_with(String username, String password) {
		product = login.loginToApp(username, password);
	}
	
	@When("^I add the product (.+)$")
	public void i_add_the_product(String products) {
		cart = product.addProductsToCart(products);
//		EndPage endPage = payment.checkout(country);
	}
	@And("^I checkout product (.+) and submit the order for country \"(.+)\"$")
	public void i_checkout_product_and_submit_the_order_to_get(String products, String country) {
		payment = cart.verifyCart(products);
		endPage = payment.checkout(country);
	}
	@Then("^I capture orderIds for (.+)$")
	public void i_capture_orderIds(String products) {
		List<String> orderIds = endPage.captureOrderDetails(products);
		System.out.println(orderIds);
	}
	@Then("I validate the error message in the login page")
	public void i_validate_the_error_message_in_the_login_page(){
		login.loginErrorMsgValidation();
	}
	@And("I close the application")
	public void i_close_the_application() {
		id.closeBrowser();
	}
}
