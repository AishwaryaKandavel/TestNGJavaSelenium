package eCommerce.testNGCucumberRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/eCommerce/CucumberTests", glue = "eCommerce.StepDefinition",
monochrome = true, plugin = {"html:cucumberReports/cucumber.html"}, tags = "@Negative")
public class TestRunner extends AbstractTestNGCucumberTests{

}
