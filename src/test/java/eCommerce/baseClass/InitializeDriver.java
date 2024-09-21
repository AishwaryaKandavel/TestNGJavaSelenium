package eCommerce.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import eCommerce.AbstractComponents.ExcelHandler;
import eCommerce.AbstractComponents.JDBCConnection;
import eCommerce.AbstractComponents.JSONHandler;
import eCommerce.AbstractComponents.UtilityFunctions;
import eCommerce.POM.Login;
import eCommerce.POM.PriceList;

public class InitializeDriver {
	
	protected static Properties prop = new Properties();
	protected static JSONHandler jsonHandler;
	protected static ExcelHandler excelHandler;
	protected static JDBCConnection jdbcConnection;
	
	public WebDriver driver;
	Wait<WebDriver> wait;
	public Login login;
	public PriceList priceList;
	
	@BeforeSuite(alwaysRun = true)
	public void initializeProperties() throws IOException {
		FileInputStream fis = new FileInputStream(
				new File(System.getProperty("user.dir") + "/src/main/resources/runConfig.properties"));
		prop.load(fis);
		jsonHandler = new JSONHandler(prop);
		excelHandler = new ExcelHandler(prop);
		jdbcConnection = new JDBCConnection(prop);
	}

	public WebDriver initializeBrowser() throws IOException, URISyntaxException {
		
		FileInputStream fis = new FileInputStream(
				new File(System.getProperty("user.dir") + "/src/main/resources/runConfig.properties"));
		prop.clear();
		prop.load(fis);
		
		String cloud = System.getProperty("cloud")!=null?
				System.getProperty("cloud"):
					prop.getProperty("cloud").toLowerCase();
		
		String remote = System.getProperty("remote")!=null?
				System.getProperty("remote"):
					prop.getProperty("remote").toLowerCase();
		
		String browser = System.getProperty("browser")!=null?
				System.getProperty("browser"):
					prop.getProperty("browser").toLowerCase();
		
		String headless = System.getProperty("headless")!=null?
				System.getProperty("headless"):prop.getProperty("headless");
		
		DesiredCapabilities cap = new DesiredCapabilities();
			if(remote.equalsIgnoreCase("yes")) {
				cap.setBrowserName(browser);
			}
	
			switch (browser) {
			case "chrome":
			case "edge": {
	
				Map<String, Object> prefs = chromiumPreferences(browser);
				List<String> arguments = addArguments(headless);			
	
				if (browser.equalsIgnoreCase("chrome")) {
					ChromeOptions options = addChromeOptions(arguments, prefs);
					if(remote.equalsIgnoreCase("yes")) {
						cap.setCapability(ChromeOptions.CAPABILITY, options);
						driver = new RemoteWebDriver(new URI
								("http://"+UtilityFunctions.GetIpAddress()+":4444").toURL(), cap);
						System.out.println();
					}else {
						driver = new ChromeDriver(options);
					}
				} else {
					EdgeOptions options = addEdgeOptions(arguments, prefs);
					if(remote.equalsIgnoreCase("yes")) {
						cap.setCapability(EdgeOptions.CAPABILITY, options);
						driver = new RemoteWebDriver(new URI
								("http://"+UtilityFunctions.GetIpAddress()+":4444").toURL(), cap);
					}else
						driver = new EdgeDriver(options);
				}
				break;
			}
			case "firefox": {
	
				FirefoxProfile profile = addFirefoxProfile();
				FirefoxOptions options = addFirefoxOptions(profile, headless);
				if(remote.equalsIgnoreCase("yes")) {
					cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
					driver = new RemoteWebDriver(new URI
							("http://"+UtilityFunctions.GetIpAddress()+":4444").toURL(), cap);
				}else
					driver = new FirefoxDriver(options);
				break;
			}
	
			default:
				throw new IllegalArgumentException("Unexpected value: " + browser);
			}
			
	
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			if (browser.equalsIgnoreCase("firefox"))
				driver.manage().window().maximize();
			if(headless.equalsIgnoreCase("yes"))
				driver.manage().window().fullscreen();
			return driver;
		}
	
	
	public Map<String, Object> chromiumPreferences(String browser) {
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

		return prefs;
	}
	public List<String> addArguments(String headless) {
		List<String> arguments = new ArrayList<String>();
		arguments.add("--start-maximized"); // maximize while starting
		arguments.add("--disable-notifications"); // disable notifications
		if (headless.equalsIgnoreCase("yes"))
			arguments.add("--headless"); // headless execution
		return arguments;
	}
	public ChromeOptions addChromeOptions(List<String> arguments, Map<String, Object> prefs) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(arguments);
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" }); // disable
																								// info-bars
		options.setExperimentalOption("prefs", prefs);
		return options;
	}
	public EdgeOptions addEdgeOptions(List<String> arguments, Map<String, Object> prefs) {
		EdgeOptions options = new EdgeOptions();
		options.addArguments(arguments);
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" }); // disable
																								// info-bars
		options.setExperimentalOption("prefs", prefs);
		return options;		
	}
	
	public FirefoxProfile addFirefoxProfile() {
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
		return profile;
	}
	
	public FirefoxOptions addFirefoxOptions(FirefoxProfile profile, String headless) {
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(profile);
		if (headless.equalsIgnoreCase("yes")) {
			options.addArguments("--headless");
		}
		return options;
	}
	
	@BeforeMethod(alwaysRun = true)
	public void launchApp() throws IOException, URISyntaxException {
		driver = initializeBrowser();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		String appName = prop.getProperty("appName");
		login = new Login(driver);
		login.goTo(prop.getProperty(appName+"_url"));
		if(appName.equalsIgnoreCase("priceList"))
			priceList = new PriceList(driver, excelHandler);
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		driver.close();
	}
}
