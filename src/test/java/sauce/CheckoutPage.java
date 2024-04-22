package sauce;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    @FindBy(css = ".title")
    private WebElement title;

    @FindBy(css = "#first-name")
    private WebElement firstNameField;

    @FindBy(css = "#last-name")
    private WebElement lastNAmeField;

    @FindBy(css = "#postal-code")
    private WebElement zip;

    @FindBy(css = "#continue")
    private WebElement continueButton;

    @FindBy(css = "#cancel")
    private WebElement cancelButton;

    @FindBy(css = "h3")
    private WebElement errorMessage;


    public CheckoutPage() {
        PageFactory.initElements(driver, this);
    }

    public String getUrlCheckoutPage() { return CheckoutPage.driver.getCurrentUrl();}

    public String getTitleCheckoutPage() {return title.getText();}

    public CheckoutPage invalidCheckoutDataInput(String firstName, String lastName, String zipCode) {
        firstNameField.sendKeys(firstName);
        lastNAmeField.sendKeys(lastName);
        zip.sendKeys(zipCode);
        continueButton.click();
        return this;
    }

    public String getErrorMessage() {return errorMessage.getText();}

    public CheckoutOverviewPage fillInFields(String firstName, String lastName, String zipCode) {
        firstNameField.sendKeys(firstName);
        lastNAmeField.sendKeys(lastName);
        zip.sendKeys(zipCode);
        continueButton.click();
        return new CheckoutOverviewPage();
    }

    public CartPage cancelCheckout() {
        cancelButton.click();
        return new CartPage();
    }

}
