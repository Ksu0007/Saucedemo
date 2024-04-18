package tests;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import readProperties.ConfigProvider;
import sauce.BackpackPage;
import sauce.InventoryPage;
import sauce.LoginPage;

public class BackpackTest extends BaseTest {
    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.userLogin(ConfigProvider.STANDARD_LOGIN, ConfigProvider.STANDARD_PASSWORD);
    }

    @Test
    public void testReturnBack() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.openBackpackPage();
        BackpackPage backpackPage = new BackpackPage();
        backpackPage.returnBack();
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products");

    }
}
