package sauce;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BackpackPage extends BasePage {

    @FindBy(css = ".inventory_details_img")
    private WebElement backpackImage;

    @FindBy(css = ".inventory_details_name large_size")
    private WebElement backpackNameElement;

    @FindBy(css = ".inventory_details_price")
    private WebElement backpackPriceElement;

    @FindBy(css = ".inventory_details_desc large_size")
    private WebElement description;

    @FindBy(id = "#add-to-cart")
    private WebElement addToCartButton;

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

    public Double getBackpackProce() {
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

    public InventoryPage returnBack() {
        backButton.click();
        return new InventoryPage();
    }
}
