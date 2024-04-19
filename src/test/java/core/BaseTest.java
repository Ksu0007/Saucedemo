package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.Properties;

abstract public class BaseTest {
    public static final String OS_NAME_FOR_GIT = System.getProperty("os.name");
    private static final String ENV_BROWSER_NAME = "ENV_BROWSER_NAME";
    private static final boolean USE_CHROME_LOCALLY = false;
    protected static WebDriver driver;
    String browserName;

    @BeforeMethod
    public void setUp() {
        if (OS_NAME_FOR_GIT.equals("Linux")) {

            Properties properties = new Properties();
            properties.setProperty(ENV_BROWSER_NAME, System.getenv(ENV_BROWSER_NAME));
            browserName = properties.getProperty(ENV_BROWSER_NAME);
            System.out.println(browserName + "  ENV_BROWSER_NAME OPTION");
            startBrowser(browserName);
        } else {
            if (USE_CHROME_LOCALLY) {
                browserName = "CHROME";
                WebDriverManager.chromedriver().setup();
            } else {
                browserName = "FIREFOX";
                WebDriverManager.firefoxdriver().setup();
            }
            startBrowser(browserName);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        BasePage.setDriver(driver);
    }

    public void startBrowser(String browserName) {
        if (OS_NAME_FOR_GIT.equals("Linux")) {
            if (browserName.equals("CHROME")) {
                driver = new ChromeDriver(new ChromeOptions().addArguments(
                        "--headless", "--window-size=1920,1080"));
                System.out.println("RUN ON CHROME");
                Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
                System.out.println("Browser Name - " + capabilities.getBrowserName());
                System.out.println("Browser version - " + capabilities.getBrowserVersion());
            } else if (browserName.equals("FIREFOX")) {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless");
                driver = new FirefoxDriver(options);
                System.out.println("RUN ON FIREFOX");
                Capabilities capabilities = ((FirefoxDriver) driver).getCapabilities();
                System.out.println("Browser Name - " + capabilities.getBrowserName());
                System.out.println("Browser version - " + capabilities.getBrowserVersion());
            } else if (browserName.equals("SAFARI")) {
                WebDriverManager.getInstance(SafariDriver.class).setup();
                driver = new SafariDriver();
                System.out.println("RUN ON SAFARI");
                Capabilities capabilities = ((SafariDriver) driver).getCapabilities();
                System.out.println("Browser Name - " + capabilities.getBrowserName());
                System.out.println("Browser version - " + capabilities.getBrowserVersion());
            }
        } else {
            if (browserName.equals("CHROME")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browserName.equals("FIREFOX")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
        }
    }

    @AfterMethod
    protected void tearDown() {
        driver.quit();
    }
    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess()) takeScreenshot(driver, method.getName(), this.getClass().getName());

    }

    static File takeScreenshot (WebDriver driver, String methodName, String className) {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(String.format("screenshots/%s.%s.png", className, methodName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
