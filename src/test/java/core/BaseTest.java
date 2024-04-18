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
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        if (OS_NAME_FOR_GIT.equals("Linux")) {
            Properties properties = new Properties();
            properties.setProperty(ENV_BROWSER_NAME, System.getenv(ENV_BROWSER_NAME));
            /**Тут мы береём переменную - ENV_BROWSER_NAME: (например - "FIREFOX") из файла CIforItFriendly.yml
             * и передаём её в метод startBrowser, который применит драйвера для хрома или фаерфокса
             * */
            //TODO для вывода и перепроверки, потом убрать )))
            String options = properties.getProperty(ENV_BROWSER_NAME);
            System.out.println(options + "  ENV_BROWSER_NAME OPTION");
            startBrowser(options);
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        BasePage.setDriver(driver);
    }

    public void startBrowser(String Browser) {
        if (Browser.equals("CHROME")) {
            driver = new ChromeDriver(new ChromeOptions().addArguments(
                    "--headless", "--window-size=1920,1080"));
            System.out.println("RUN ON CHROME");
            Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
            System.out.println("Browser Name - " + capabilities.getBrowserName());
            System.out.println("Browser version - " + capabilities.getBrowserVersion());
        } else {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
            System.out.println("RUN ON FIREFOX");
            Capabilities capabilities = ((FirefoxDriver) driver).getCapabilities();
            System.out.println("Browser Name - " + capabilities.getBrowserName());
            System.out.println("Browser version - " + capabilities.getBrowserVersion());
        }
    }
    @AfterMethod
    public void tearDown() {

        if (ENV_BROWSER_NAME.equals("CHROME")) {
            driver.close();
            driver.quit();
        } else {
            driver.close();
        }
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
