package eCommerce.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import eCommerce.AbstractComponents.JSONHandler;
import eCommerce.POM.Login;
import io.cucumber.java.BeforeAll;

public class InitializeDriver {
	
	protected static Properties prop = new Properties();
	protected static JSONHandler jsonHandler;
	
	public WebDriver driver;
	Wait<WebDriver> wait;
	public Login login;
	
	
	@BeforeSuite(alwaysRun = true)
	@BeforeAll
	public void initializeProperties() throws IOException {
		FileInputStream fis = new FileInputStream(
				new File(System.getProperty("user.dir") + "/src/main/resources/runConfig.properties"));
		prop.load(fis);
		jsonHandler = new JSONHandler(prop);
	}

	public WebDriver initializeBrowser() throws IOException {
		
		FileInputStream fis = new FileInputStream(
				new File(System.getProperty("user.dir") + "/src/main/resources/runConfig.properties"));
		prop.clear();
		prop.load(fis);
		
		String browser = System.getProperty("browser")!=null?
				System.getProperty("browser"):
					prop.getProperty("browser").toLowerCase();
		
		String headless = System.getProperty("headless")!=null?
				System.getProperty("headless"):prop.getProperty("headless");

		switch (browser) {

		case "chrome":
		case "edge": {

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", prop.getProperty("download_path"));
			prefs.put("download.prompt_for_download", false); // Set this to false to disable download prompts
			if (browser.equalsIgnoreCase("chrome"))
				prefs.put("profile.default_content_settings.popups", 0); // Disable pop-ups
			else
				prefs.put("profile.default_content_settings.popups", 2); // Disable pop-ups
			prefs.put("safebrowsing.enabled", prop.getProperty("safebrowsing")); // Enable safe browsing

			prefs.put("profile.password_manager_enabled", false); // Disable password manager
			prefs.put("credentials_enable_service", false); // Disable credentials service

			List<String> arguments = new ArrayList<String>();
			arguments.add("--start-maximized"); // maximize while starting
			arguments.add("--disable-notifications"); // disable notifications
			if (headless.equalsIgnoreCase("yes"))
				arguments.add("--headless"); // headless execution

			if (browser.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments(arguments);
				options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" }); // disable
																										// info-bars
				options.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(options);
			} else {
				EdgeOptions options = new EdgeOptions();
				options.addArguments(arguments);
				options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" }); // disable
																										// info-bars
				options.setExperimentalOption("prefs", prefs);
				driver = new EdgeDriver(options);
			}
			break;
		}

		case "firefox": {

			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2); // Use custom download directory
			profile.setPreference("browser.download.dir", prop.getProperty("download_path")); // Set download directory
			profile.setPreference("browser.download.manager.showWhenStarting", false); // Disable download prompts
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
					"application/pdf,application/x-pdf"); // Add MIME types if needed
			profile.setPreference("browser.safebrowsing.enabled", true); // Enable safe browsing
			profile.setPreference("dom.disable_open_during_load", true); // Disable pop-ups
			profile.setPreference("signon.rememberSignons", false); // Disable password manager
			profile.setPreference("signon.autofillForms", false); // Disable auto-fill

			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(profile);
			if (headless.equalsIgnoreCase("yes")) {
				options.addArguments("--headless");
			}

			driver = new FirefoxDriver(options);
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + browser);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
		if (browser.equalsIgnoreCase("firefox"))
			driver.manage().window().maximize();
		if(headless.equalsIgnoreCase("yes"))
			driver.manage().window().fullscreen();
		return driver;
	}
	
	@BeforeMethod(alwaysRun = true)
	public void launchApp() throws IOException {
		driver = initializeBrowser();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		login = new Login(driver);
		login.goTo(prop.getProperty("app_url"));
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		driver.close();
	}
}
