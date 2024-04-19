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

    @Test
    public void testItemName() {
        BackpackPage backpackPage = new InventoryPage().openBackpackPage();
        Assert.assertEquals(backpackPage.getBackpackName(), "Sauce Labs Backpack");
    }

    @Test
    public void testItemPrice() {
        BackpackPage backpackPage = new InventoryPage().openBackpackPage();
        Assert.assertEquals(backpackPage.getBackpackPrice(), 29.99);
    }

    @Test
    public void testAddToCart() {
        BackpackPage backpackPage = new InventoryPage().openBackpackPage();
        backpackPage.addToCart();
        Assert.assertEquals(backpackPage.getNumberOfGoodsInCart(), "1");
    }

    @Test
    public void testRemoveFromCert(){
        BackpackPage backpackPage = new InventoryPage().openBackpackPage();
        backpackPage.addToCart();
        backpackPage.removeFromCart();
        Assert.assertFalse(backpackPage.isGoodsInCartDisplayed());

    }
}
