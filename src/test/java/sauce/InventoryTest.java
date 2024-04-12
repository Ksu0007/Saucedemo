package sauce;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import readProperties.ConfigProvider;

import java.util.List;
import java.util.stream.Collectors;

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
        List<java.lang.String> actualLinks = inventoryPage.getBurgerMenuLinks();

                .map(element -> element.getText())  // Получение текста из WebElement
                .collect(Collectors.toList());

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


}
