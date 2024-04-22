package sauce;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"header_container\"]/div[2]/span")
    private WebElement title;

    @FindBy(css = ".inventory_item_price")
    public List<WebElement> prices;

    @FindBy(css = ".summary_subtotal_label")
    public WebElement subtotalSum;

    @FindBy(css = ".summary_tax_label")
    public WebElement taxSum;

    @FindBy(css = ".summary_total_label")
    public WebElement totalSum;

    @FindBy(css = "#checkout")
    public WebElement checkoutButton;

    @FindBy(css = "#cancel")
    public WebElement cancelCheckout;

    @FindBy(css = "#finish")
    private WebElement finishButton;


    public CheckoutOverviewPage() {
        PageFactory.initElements(driver, this);
    }

    public String getUrlCheckoutOverviewPage() {return CheckoutOverviewPage.driver.getCurrentUrl();}

    public String getTitleCheckoutOverviewPage() {return title.getText();}


    public InventoryPage continueShoppingFromCheckout() {
        cancelCheckout.click();
        return new InventoryPage();
    }

    public double pricesSum() {
        double sum = 0;
        for (WebElement price : prices) {
            String priceText = price.getText();
            String cleanedPriceText = priceText.replace("$", "").trim();
            double productPrice = Double.parseDouble(cleanedPriceText);
            sum += productPrice;
        }
        return sum;
    }

    public double getSubTotalSum() {
        String subtotalText = subtotalSum.getText();
        String cleanedSubtotal = subtotalText.replaceAll("[^0-9.]", "");
        double subTotalSum = Double.parseDouble(cleanedSubtotal);
        return subTotalSum;
    }

    public double getTaxSum() {
        String taxText = taxSum.getText();
        String cleanedTax = taxText.replaceAll("[^0-9.]", "");
        double taxSumNumber = Double.parseDouble(cleanedTax);
        return taxSumNumber;
    }

    public double getTotalSum() {
        String cleanedTotalSum = totalSum.getText().replaceAll("[^0-9.]", "");
        double totalSumNumber = Double.parseDouble(cleanedTotalSum);
        return totalSumNumber;
    }

    public CompleteCheckoutPage completeCheckout () {
        finishButton.click();
        return new CompleteCheckoutPage();
    }
}
