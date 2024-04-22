package tests;

import core.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import readProperties.ConfigProvider;
import sauce.*;

public class CheckoutTest extends BaseTest {
    @BeforeMethod
    public void loginAndAddProductsToCart() {
        LoginPage loginPage = new LoginPage();
        loginPage.userLogin(ConfigProvider.STANDARD_LOGIN, ConfigProvider.STANDARD_PASSWORD);
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.addBikeToCart();
        inventoryPage.addJacketToCart();
    }

    @Test
    public void testCartIsOpen() {
        CartPage cartPage = new InventoryPage().openCartPAge();
        Assert.assertEquals(cartPage.getUrlCart(), "https://www.saucedemo.com/cart.html");
    }

    @Test
    public void testTitleIsDisplayed() {
        CartPage cartPage = new InventoryPage().openCartPAge();
        Assert.assertEquals(cartPage.getTitileCart(), "Your Cart");
    }

    @Test
    public void testAddedProductsIsInCart(){
        CartPage cartPage = new InventoryPage().openCartPAge();
        Assert.assertTrue(cartPage.isProductInCart(cartPage.getProductsInCart(), "Sauce Labs Bike Light"));
        Assert.assertTrue(cartPage.isProductInCart(cartPage.getProductsInCart(), "Sauce Labs Fleece Jacket"));
    }

    @Test
    public void testRemovingProduct() {
        CartPage cartPage = new InventoryPage().openCartPAge();
        cartPage.removeProductFromCart("Sauce Labs Bike Light");
        Assert.assertFalse(cartPage.isProductInCart(cartPage.getProductsInCart(), "Sauce Labs Bike Light"));
    }

    @Test
    public void testContinueShoppingButton() {
        CartPage cartPage = new InventoryPage().openCartPAge();
        cartPage.continueShopping();
        InventoryPage inventoryPage = new InventoryPage();
        Assert.assertEquals(inventoryPage.getUrlInventoryPage(), "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void testCheckoutRedirect() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        Assert.assertEquals(checkoutPage.getUrlCheckoutPage(),
                "https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test
    public void testCheckoutPageTitle() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        Assert.assertEquals(checkoutPage.getTitleCheckoutPage(), "Checkout: Your Information");
    }

    @Test
    public void testEmptyFirstName() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        checkoutPage.invalidCheckoutDataInput("", ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP );
        Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: First Name is required");
    }

    @Test
    public void testEmptyLastName() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        checkoutPage.invalidCheckoutDataInput(ConfigProvider.USER_FIRST_NAME, "", ConfigProvider.USER_ZIP );
        Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: Last Name is required");
    }

    @Test
    public void testEmptyZip() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        checkoutPage.invalidCheckoutDataInput(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, "");
        Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: Postal Code is required");
    }

    @Test
    public void testCancelCheckout() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        checkoutPage.cancelCheckout();
        CartPage cartPage = new CartPage();
        Assert.assertEquals(cartPage.getUrlCart(), "https://www.saucedemo.com/cart.html");
    }

    @Test
    public void testValidCheckoutInfo() {
        CheckoutPage checkoutPage = new InventoryPage().openCartPAge().goToCheckout();
        checkoutPage.fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage();
        Assert.assertEquals(checkoutOverviewPage.getUrlCheckoutOverviewPage(),
                "https://www.saucedemo.com/checkout-step-two.html");
    }

    @Test
    public void testTitleCheckoutOverviewPage() {
        CheckoutOverviewPage checkoutOverviewPage = new InventoryPage().openCartPAge().goToCheckout()
        .fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        Assert.assertEquals(checkoutOverviewPage.getTitleCheckoutOverviewPage(), "Checkout: Overview");
    }

    @Test
    public void checkItemTotalSum() {
        CheckoutOverviewPage checkoutOverviewPage = new InventoryPage().openCartPAge().goToCheckout()
                .fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        Assert.assertEquals(checkoutOverviewPage.getSubTotalSum(), checkoutOverviewPage.pricesSum());
    }

    @Test
    public void checkTaxCorrectness() {
        CheckoutOverviewPage checkoutOverviewPage = new InventoryPage().openCartPAge().goToCheckout()
                .fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        double taxFromPriceSum = Math.round(checkoutOverviewPage.pricesSum() * 0.08 * 100.0) / 100.0;
        Assert.assertEquals(taxFromPriceSum, checkoutOverviewPage.getTaxSum());
    }

    @Test
    public void checkTotalSum() {
        CheckoutOverviewPage checkoutOverviewPage = new InventoryPage().openCartPAge().goToCheckout()
                .fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        double calculatedTotalSum = Math.round(checkoutOverviewPage.pricesSum() * 1.08 * 100) / 100.0;
        Assert.assertEquals(checkoutOverviewPage.getTotalSum(), calculatedTotalSum);
    }

    @Test
    public void testContinueShopping() {
        CheckoutOverviewPage checkoutOverviewPage = new InventoryPage().openCartPAge().goToCheckout()
                .fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        checkoutOverviewPage.continueShoppingFromCheckout();
        InventoryPage inventoryPage = new InventoryPage();
        Assert.assertEquals(inventoryPage.getUrlInventoryPage(), "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void testCheckout() {
        CheckoutOverviewPage checkoutOverviewPage = new InventoryPage().openCartPAge().goToCheckout()
                .fillInFields(ConfigProvider.USER_FIRST_NAME, ConfigProvider.USER_LAST_NAME, ConfigProvider.USER_ZIP);
        checkoutOverviewPage.completeCheckout();
        CompleteCheckoutPage completeCheckoutPage = new CompleteCheckoutPage();
        Assert.assertEquals(completeCheckoutPage.getThankYouMessageText(), "Thank you for your order!");
    }


}
