package eCommerceApp.Tests;

import org.testng.annotations.Test;

import eCommerce.baseClass.InitializeDriver;
import eCommerce.baseClass.RetryFlakyTests;

public class PriceList extends InitializeDriver{
	
	@Test(groups="priceList", enabled = true, description = "Download the price list, update the excel, "
			+ "upload the excel and verify the UI for updates", 
			retryAnalyzer = RetryFlakyTests.class)
	public void priceListUpdates() throws Exception {
		priceList.downloadAndUpdateFile("price", "apple", "fruit_name", "600");
		priceList.uploadAndVerifyPage();
	}
	
}
