package sauce;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "#react-burger-menu-btn")
    private WebElement burgerMenuButton;

    @FindBy(css = ".app_logo")
    private WebElement logo;

    @FindBy(css = ".title")
    private WebElement cartTitle;

    @FindBy(xpath = "//div[@data-test='item-quantity']")
    private WebElement itemQuantity;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemsInCart;

    @FindBy(css = "#continue-shopping")
    private WebElement continueButton;

    @FindBy(css = "#checkout")
    private WebElement checkoutButton;

    public CartPage() {
        PageFactory.initElements(driver, this);
    }

    public String getUrlCart() {return CartPage.driver.getCurrentUrl();}

    public String getTitileCart() {return cartTitle.getText();}

    public List<String> getProductsInCart() {
        List<String> productsInCart = new ArrayList<>();
        for (WebElement item : itemsInCart) {
            productsInCart.add(item.getText());
        }
        return productsInCart;
    }

    public boolean isProductInCart ( List<String> products, String neededProduct){
        for (String product: products) {
            if(product.equals(neededProduct)) {
                return true;
            }
        }
        return false;
    }
    public void removeProductFromCart(String productName) {
        WebElement product = driver
                .findElement(By.xpath("//div[@class='inventory_item_name'][contains(text(), '"
                        + productName + "')]"));
        //container from our product
        WebElement cartItemContainer = product.findElement(By
                .xpath("./ancestor::div[@class='cart_item_label']"));
        //The Remove button in the container
        WebElement removeButton = cartItemContainer.findElement(By
                .cssSelector(".btn_secondary"));
        removeButton.click();
    }

    public InventoryPage continueShopping() {
        continueButton.click();
        return new InventoryPage();
    }

    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage();
    }


}
