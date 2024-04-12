package sauce;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import readProperties.ConfigProvider;

public class LoginPage extends BasePage {
    @FindBy(id = "login_button_container")
    private WebElement loginBlock;

    @FindBy(css = ".login_logo")
    private WebElement logoLogin;

    @FindBy(css = "#user-name")
    private WebElement usernameInput;

    @FindBy(css = "#password")
    private WebElement passwordInput;

    @FindBy(css = "#login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//h3")
    private WebElement errorLogin;

    public LoginPage() {
        driver.get(ConfigProvider.URL);
        PageFactory.initElements(driver, this);
    }

    public String checkLogo() {
        return logoLogin.getText();
    }

    public LoginPage invalidLogin(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        return this;
    }

    public boolean errorIsDisplayed() {
        return errorLogin.isDisplayed();
    }

    public boolean logoutRedirected() {
        return loginBlock.isDisplayed();
    }

    public String getErrorMessage() {
        return errorLogin.getText();
    }
    public InventoryPage userLogin(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        return new InventoryPage();
    }

}
