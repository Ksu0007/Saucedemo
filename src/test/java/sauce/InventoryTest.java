package sauce;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import readProperties.ConfigProvider;

import java.util.List;

public class InventoryTest extends BaseTest {

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.userLogin(ConfigProvider.STANDARD_LOGIN, ConfigProvider.STANDARD_PASSWORD);
    }

    @Test
    public void testBurgerMenuIsOpen() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.openBurgerMenu();
        Assert.assertTrue(inventoryPage.burgerMenuIsDisplayed());
    }

    @Test
    public void testBurgerMenuIsClosed() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.closeBurgerMenu();
        Assert.assertTrue(inventoryPage.burgerMenuIsClosed());
    }

    @Test
    public void testBurgerMenuHasAllLinks() {
        List<String> expectedLinks = List.of("All Items", "About", "Logout", "Reset App State");
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.openBurgerMenu();
        List<String> actualLinks = inventoryPage.getBurgerMenuLinks();
        Assert.assertEquals(actualLinks.size(), expectedLinks.size());
        for (String expectedLink : expectedLinks) {
            Assert.assertTrue(actualLinks.contains(expectedLink));
        }
    }

    @Test
    public void testAboutLink() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.openBurgerMenu();
        inventoryPage.clickAbout();

        String expectedUrl = "https://saucelabs.com/";
        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedUrl);
     }

     @Test
    public void testLogoutLink() {
         InventoryPage inventoryPage = new InventoryPage();
         inventoryPage.openBurgerMenu();
         inventoryPage.clickLogout();
         LoginPage loginPage = new LoginPage();
         Assert.assertTrue(loginPage.logoutRedirected());
    }

    @Test
    public void testSortingZtoA() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.chooseDropdown(1);
        List<String> sortedProductNames = inventoryPage.alphabetSorting();
        for (int i = 1; i < sortedProductNames.size(); i++) {
            Assert.assertTrue(sortedProductNames.get(i - 1).compareTo(sortedProductNames.get(i)) >= 0,
                    "Product names are not sorted in reverse alphabetical order");
        }
    }

    @Test
    public void testPricesSortingLowToHigh() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.chooseDropdown(2);
        List<Double> sortedPrices = inventoryPage.getPricesDouble();

        for (int i = 1; i < sortedPrices.size(); i++) {
            Assert.assertTrue(sortedPrices.get(i - 1) <= sortedPrices.get(i), "Prices are not sorted correctly");
        }
    }

    @Test
    public void testPricesSortedHighToLow() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.chooseDropdown(3);
        List<Double> sortedPrices = inventoryPage.getPricesDouble();

        for (int i = 1; i < sortedPrices.size(); i++) {
            Assert.assertTrue(sortedPrices.get(i - 1) >= sortedPrices.get(i));
        }
    }

    @Test
    public void testSortingAtoZ() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.chooseDropdown(0);
        List<String> sortedProductNames = inventoryPage.alphabetSorting();

        for (int i = 1; i < sortedProductNames.size(); i++) {
            Assert.assertTrue(sortedProductNames.get(i - 1).compareTo(sortedProductNames.get(i)) <= 0);
        }
    }


}
