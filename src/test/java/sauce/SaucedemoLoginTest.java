package sauce;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import readProperties.ConfigProvider;

public class SaucedemoLoginTest extends BaseTest {
    @Test
    public void testLogoOnLoginPage(){
        LoginPage loginPage = new LoginPage();
        String logo = "Swag Labs";
        Assert.assertEquals(loginPage.checkLogo(), logo);
    }

    @Test
    public void testStandardUserLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.userLogin(ConfigProvider.STANDARD_LOGIN, ConfigProvider.STANDARD_PASSWORD);
        InventoryPage inventoryPage = new InventoryPage();
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products");
        Assert.assertTrue(inventoryPage.getUrlInventoryPage().contains("inventory"));
    }
    @Test
    public void testInvalidUsernameLogin() {
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin("standart_user", ConfigProvider.STANDARD_PASSWORD);
        Assert.assertTrue(loginPage.errorIsDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
    }

    @Test
    public void testInvalidPassword() {
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin(ConfigProvider.STANDARD_LOGIN, "123");
        Assert.assertTrue(loginPage.errorIsDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
    }

    @Test
    public void testEmptyUsername() {
        String expectedErrorMessage = "Epic sadface: Username is required";
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin("", ConfigProvider.STANDARD_PASSWORD);
        Assert.assertTrue(loginPage.errorIsDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
    }

    @Test
    public void testEmptyPassword() {
        String expectedErrorMessage = "Epic sadface: Password is required";
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin(ConfigProvider.STANDARD_LOGIN, "");
        Assert.assertTrue(loginPage.errorIsDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
    }


}
