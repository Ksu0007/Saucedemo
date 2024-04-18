package sauce;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    @FindBy(xpath = "//span[@class='title']")
    private WebElement titleInventoryPage;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuOpenButton;

    @FindBy(css = ".bm-cross-button")
    private WebElement burgerMenuCloseButton;

    @FindBy(css = ".bm-menu-wrap")
    private WebElement burgerMenu;

    @FindBy(css = "a[class='bm-item menu-item']")
    private List<WebElement> burgerMenuLinks;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(xpath = "//*[@id='header_container']/div[2]/div/span/select")
    private WebElement filterDropdown;

    @FindBy(css = ".inventory_list")
    private List<WebElement> products;

    @FindBy(css = ".inventory_item_name ")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(xpath = "//button[@class='btn btn_primary btn_small btn_inventory ']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//img[@alt='Sauce Labs Backpack']")
    private WebElement backpackLink;


    public InventoryPage() {
        PageFactory.initElements(driver, this);
    }

    public String getTitleInventoryPage() {
        return titleInventoryPage.getText();
    }

    public String getUrlInventoryPage() {
        return InventoryPage.driver.getCurrentUrl();
    }

    public void openBurgerMenu() {
        burgerMenuOpenButton.click();
    }

    public boolean burgerMenuIsDisplayed() {
        String ariaHiddenValue = burgerMenu.getAttribute("aria-hidden");
        return ariaHiddenValue.equals("false");
    }

    public boolean burgerMenuIsClosed() {
        String ariaHiddenValue = burgerMenu.getAttribute("aria-hidden");
        return ariaHiddenValue.equals("true");
    }

    public void closeBurgerMenu() {burgerMenuCloseButton.click();}
    public void clickCloseButtonWithWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuCloseButton)).click();
    }

    public List<String> getBurgerMenuLinks() {
        getWait10().until(ExpectedConditions.visibilityOf(burgerMenu));

        List<String> menuLinksText = new ArrayList<>();
        for (WebElement link : burgerMenuLinks) {
            menuLinksText.add(link.getText());
        }
        return menuLinksText;
    }

    public void clickAbout() {
        aboutLink.click();
    }

    public LoginPage clickLogout() {
        logoutLink.click();
        return new LoginPage();
    }

    public void chooseDropdown(int index) {
        filterDropdown.click();
        Select s = new Select(filterDropdown);
        s.selectByIndex(index);
    }

    public List<String> alphabetSorting() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getPricesDouble() {
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : productPrices) {
            String priceText = priceElement.getText();
            String cleanedPriceText = priceText.replace("$", "").trim();
            double price = Double.parseDouble(cleanedPriceText);
            prices.add(price);
        }
        return prices;
    }

    public BackpackPage openBackpackPage(){
        backpackLink.click();
        return new BackpackPage();
    }
}
