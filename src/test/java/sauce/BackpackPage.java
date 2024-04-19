package sauce;

import core.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BackpackPage extends BasePage {

    @FindBy(css = ".inventory_details_img")
    private WebElement backpackImage;

    @FindBy(xpath = "//div[@data-test='inventory-item-name']")
    private WebElement backpackNameElement;

    @FindBy(css = ".inventory_details_price")
    private WebElement backpackPriceElement;

    @FindBy(css = "//div[@data-test='inventory-item-desc']")
    private WebElement description;

    @FindBy(css = "#add-to-cart")
    private WebElement addToCartButton;

    @FindBy(css = "#remove")
    private WebElement removeFromCartButton;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartButton;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement goodsInCart;

    @FindBy(id = "back-to-products")
    private WebElement backButton;

    public BackpackPage() {
        PageFactory.initElements(driver, this);
    }

    public String getBackpackImage() {
        String imageUrl = backpackImage.getAttribute("src");
        return imageUrl;
    }

    public String getBackpackName() {
        String productName = backpackNameElement.getText();
        return productName;
    }

    public Double getBackpackPrice() {
        String productPriceText = backpackPriceElement.getText();
        String cleanedProductPriceText = productPriceText.replace("$", "").trim();
        Double backpackPrice = Double.parseDouble(cleanedProductPriceText);
        return backpackPrice;
    }

    public String getBackpackDescription() {
        String productDescription = description.getText();
        return productDescription;
    }

    public void addToCart() {
        addToCartButton.click();
    }

    public void removeFromCart(){removeFromCartButton.click();}

    public String getNumberOfGoodsInCart() {
        String goodsQuantity = goodsInCart.getText();
        return goodsQuantity;
    }

    public boolean isGoodsInCartDisplayed() {
        try {
            return goodsInCart.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public InventoryPage returnBack() {
        backButton.click();
        return new InventoryPage();
    }
}
