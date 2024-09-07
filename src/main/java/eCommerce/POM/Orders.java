package eCommerce.POM;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import eCommerce.AbstractComponents.UtilityFunctions;

public class Orders extends UtilityFunctions{
	
	WebDriver driver;
	
	public Orders(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//tr[@class='ng-star-inserted']//th")
	List<WebElement> orderIDsElem;
	
	public boolean verifyOrders(List<String> orderIDs) {
		boolean flag = false;
		List<String> ordersIDsActual = orderIDsElem.stream().map(o -> o.getText().trim()).collect(Collectors.toList());
		for (String orderId : orderIDs) {
			if (!ordersIDsActual.contains(orderId)) {
			    flag = false;
			    break;
			}
			flag = true;
		}
		return flag;
	}
}
