package sauce;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;


import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {
    @FindBy(xpath = "//span[@class='title']")
    private WebElement titleInventoryPage;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuOpenButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement burgerMenuCloseButton;

    @FindBy(css = ".bm-menu-wrap")
    private WebElement burgerMenu;

    @FindBy(css = "a[class='bm-item menu-item']")
    private List<WebElement> burgerMenuLinks;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(css = "product_sort_container")
    private WebElement filterDropdown;

    @FindBy(css = ".inventory_list")
    private List<WebElement> products;

    @FindBy(css = ".inventory_item_name ")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(xpath = "//button[@class='btn btn_primary btn_small btn_inventory ']")
    private List<WebElement> addToCartButtons;


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

    public void closeBurgerMenu() {
        burgerMenuCloseButton.click();
    }

    public List<String> getBurgerMenuLinks() {
        List<String> menuLinksText = new ArrayList<>();
        for(WebElement link : burgerMenuLinks) {
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
}
